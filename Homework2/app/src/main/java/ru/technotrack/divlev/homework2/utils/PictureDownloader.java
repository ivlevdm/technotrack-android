package ru.technotrack.divlev.homework2.utils;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Message;
import android.util.Log;
import android.util.Pair;

import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class PictureDownloader<Token> extends HandlerThread {
    private final static String TAG = PictureDownloader.class.getSimpleName();

    private static final int MESSAGE_DOWNLOAD = 0;

    private Handler handler;
    private Map<Token, Pair<Listener<Token>, String>> requestMap =
            Collections.synchronizedMap(new HashMap<Token, Pair<Listener<Token>, String>>());

    private Handler responseHandler;

    private DataStorage<String, Bitmap> dataStorage = null;

    public interface Listener<Token>{
        void onPictureDownloaded(Token token, Bitmap picture);
    }

    public PictureDownloader(Handler responseHandler) {
        super(TAG);
        this.responseHandler = responseHandler;
    }

    public void queuePicture(Token token, Listener<Token> listener, String url) {
        requestMap.put(token, new Pair<>(listener, url));
        handler.obtainMessage(MESSAGE_DOWNLOAD, token)
                .sendToTarget();
    }

    @SuppressLint("HandlerLeak")
    @Override
    protected void onLooperPrepared() {
        handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                if (msg.what == MESSAGE_DOWNLOAD) {
                    @SuppressWarnings("unchecked")
                    Token token = (Token) msg.obj;
                    Log.i(TAG, "PictureDownloader request for url: " + requestMap.get(token).second);
                    handleRequest(token);
                }
            }
        };
    }

    private void handleRequest(final Token token){

        final String url = requestMap.get(token).second;

        Bitmap bitMap = null;
        try {
            if (url == null)
                return;

            byte[] bitmapBytes = HttpDownloader.getUrlBytes(url);
            bitMap = BitmapFactory.decodeByteArray(bitmapBytes, 0, bitmapBytes.length);
            Log.i(TAG, "PictureDownloader picture downloaded");

        } catch (IOException e) {
            Log.e(TAG, "PictureDownloader Error downloading image", e);
        }

        dataStorage.put(url, bitMap);
        processRespond(token, bitMap, url);
    }

    private void processRespond(final Token token, final Bitmap bitmap, final String url){
        responseHandler.post(new Runnable() {
            @Override
            public void run() {
                if (requestMap.get(token) == null || !requestMap.get(token).second.equals(url)) {
                    return;
                }
                Listener<Token> listener = requestMap.get(token).first;
                listener.onPictureDownloaded(token, bitmap);
                requestMap.remove(token);
            }
        });
    }

    public void clearQueue() {
        handler.removeMessages(MESSAGE_DOWNLOAD);
        requestMap.clear();
    }

    public void setDataStorage(DataStorage<String, Bitmap> dataStorage) {
        this.dataStorage = dataStorage;
    }

    public DataStorage<String, Bitmap> getDataStorage() {
        return dataStorage;
    }
}
