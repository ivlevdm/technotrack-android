package ru.technotrack.divlev.messenger.network;


import android.os.AsyncTask;
import android.util.Log;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.nio.charset.Charset;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

import ru.technotrack.divlev.messenger.config.ApplicationConfig;
import ru.technotrack.divlev.messenger.util.JSONUtil;

public class NetworkImpl implements Network {
    private static final NetworkImpl network = new NetworkImpl();
    private static String TAG = NetworkImpl.class.getSimpleName().toUpperCase();

    private NetworkListener listener;
    private Socket socket;
    private volatile boolean stop;
    private Queue<String> messageQueue;
    private Thread receiver;
    private Thread sender;
    private AsyncTask<Void, Void, Void> openSocketTask;

    private class MessageReceiver implements Runnable {
        private final InputStream inStream;

        public MessageReceiver() throws IOException{
            this.inStream = new BufferedInputStream(socket.getInputStream());
        }

        @Override
        public void run() {
            try {
                boolean cleanup = false;
                byte[] data = new byte[32768];
                ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

                do {
                    if (cleanup) {
                        outputStream.reset();
                        cleanup = false;
                    }
                    // Запись в data производится от 0
                    int readBytes = inStream.read(data);
                    if (readBytes != -1) {
                        outputStream.write(data, 0, readBytes);
                        outputStream.flush();
                        String result = outputStream.toString("utf-8");
                        Log.i(TAG, "Result:" + result);
                        if (result.endsWith("}") && JSONUtil.isJSONValid(result)) {
                            Log.i(TAG, "Received message:" + result);
                            listener.onReceiveMessage(result);
                            cleanup = true;
                        }
                    } else {
                        network.listener.onNetworkError(NetworkErrorType.SOCKET_IS_CLOSED);
                        break;
                    }
                } while (!stop);
            } catch (IOException e) {
                Log.e(TAG, e.getMessage());
                e.printStackTrace();
            }
        }
    }

    private class MessageSender implements Runnable {
        private final OutputStream outStream;

        public MessageSender() throws IOException {
            outStream = socket.getOutputStream();
        }

        @Override
        public void run() {
            while (!stop) {
                if (messageQueue.isEmpty()) {
                    try {
                        synchronized (network) {
                            network.wait();
                        }
                    } catch (InterruptedException e) {
                        Log.e(TAG, e.getMessage());
                    }
                } else {
                    while (!messageQueue.isEmpty()) {
                        String msg = messageQueue.poll();
                        byte[] data = msg.getBytes(Charset.forName("UTF-8"));

                        try {
                            outStream.write(data);
                            outStream.flush();
                            Log.i(TAG, "Sent: " + msg);
                        } catch (IOException e) {
                            Log.e(TAG, e.getMessage());
                            network.listener.onNetworkError(NetworkErrorType.SOCKET_IS_CLOSED);
                        }
                    }
                }
            }
        }
    }

    private NetworkImpl() {}

    public static NetworkImpl instance() {
        return network;
    }

    @Override
    public void setListener(NetworkListener listener) {
        this.listener = listener;
    }

    @Override
    public void start() {
        messageQueue = new ConcurrentLinkedQueue<>();
        stop = false;

        openSocketTask = new ConnectServerTask();
        openSocketTask.execute();
    }

    class ConnectServerTask extends AsyncTask<Void, Void, Void> {
        @Override
        protected Void doInBackground(Void... params) {
            try {
                Log.i(TAG, "Start to open a socket.");
                network.socket = new Socket(ApplicationConfig.SERVER_ADDRESS,
                        ApplicationConfig.SERVER_PORT);

                receiver = new Thread(new MessageReceiver());
                sender = new Thread(new MessageSender());

                receiver.start();
                sender.start();
            } catch (IOException e) {
                Log.e(TAG, e.getMessage());
                network.listener.onNetworkError(NetworkErrorType.SOCKET_OPENING_FAILED);
            }

            return null;
        }

    }

    @Override
    public void send(String msg) {
        messageQueue.add(msg);
        synchronized (network) {
            network.notify();
        }
    }

    @Override
    public void finish() {
        try {
            Log.i(TAG, "Finishing...");
            stop = true;
            synchronized (network) {
                network.notify();
            }
            sender.join();
            receiver.join();
            messageQueue = null;
            socket.close();
            Log.i(TAG, "Finished.");
        } catch (InterruptedException e) {
            Log.e(TAG, e.getMessage());
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
