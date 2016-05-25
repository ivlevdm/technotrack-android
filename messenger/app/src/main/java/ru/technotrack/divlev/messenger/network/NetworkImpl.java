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
import java.util.concurrent.SynchronousQueue;

import ru.technotrack.divlev.messenger.config.ApplicationConfig;
import ru.technotrack.divlev.messenger.util.JSONUtil;

public class NetworkImpl implements Network {
    private static final NetworkImpl network = new NetworkImpl();
    private static String TAG = NetworkImpl.class.getSimpleName().toUpperCase();

    private NetworkListener listener;
    private Socket socket;
    private boolean stop;
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
                int offset = 0;
                ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

                do {
                    if (cleanup) {
                        outputStream.reset();
                        offset = 0;
                        cleanup = false;
                    }
                    // Запись в data производится от 0
                    int readBytes = inStream.read(data);
                    if (readBytes != -1) {
                        outputStream.write(data, offset, readBytes);
                        offset += readBytes;
                        outputStream.flush();
                        String result = outputStream.toString("utf-8");
                        if (result.endsWith("}") && JSONUtil.isJSONValid(result)) {
                            Log.i(TAG, "Received message:" + result);
                            listener.onReceiveMessage(result);
                            cleanup = true;
                        }
                    } else {
                        Log.d(TAG,"Couldn't read anything.");
                    }
                } while (!stop);
            } catch (IOException e) {
                Log.e(TAG, e.getMessage());
            }
        }
    }

    private class MessageSender implements Runnable {
        private final OutputStream outStream;

        public MessageSender() throws IOException {
            outStream = new BufferedOutputStream(socket.getOutputStream());
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
                        } catch (IOException e) {
                            Log.e(TAG, e.getMessage());
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
        messageQueue = new SynchronousQueue<>();
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
            stop = true;
            sender.join();
            receiver.join();
            messageQueue = null;
        } catch (InterruptedException e) {
            Log.e(TAG, e.getMessage());
        }

    }
}
