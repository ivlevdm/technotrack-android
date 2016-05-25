package ru.technotrack.divlev.messenger.logic;

import android.annotation.SuppressLint;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Message;
import android.util.Log;

import ru.technotrack.divlev.messenger.MainActivityBase;
import ru.technotrack.divlev.messenger.fragment.login.LoginLogic;
import ru.technotrack.divlev.messenger.fragment.welcome.WelcomeFragment;
import ru.technotrack.divlev.messenger.fragment.welcome.WelcomeLogic;
import ru.technotrack.divlev.messenger.network.Network;
import ru.technotrack.divlev.messenger.network.NetworkImpl;

public class ApplicationLogic implements Network.NetworkListener, WelcomeLogic, LoginLogic {
    private static ApplicationLogic appLogic;
    private static String TAG = ApplicationLogic.class.getSimpleName().toUpperCase();

    private MainActivityBase activity;
    private OnNetworkConnectListener welcomeLister;
    private Network network;
    private ApplicationLogicLooper looper;

    private final int SERVER_ANSWER = 0;

    public static ApplicationLogic instance() {
        if (appLogic == null) {
            appLogic = new ApplicationLogic();
        }
        return appLogic;
    }

    private class ApplicationLogicLooper extends HandlerThread {
        private Handler handler;
        private Handler responseHandler;

        public ApplicationLogicLooper(Handler responseHandler) {
            super(TAG);
            this.responseHandler = responseHandler;
        }

        @SuppressLint("HandlerLeak")
        @Override
        protected void onLooperPrepared() {
            handler = new Handler() {
                @Override
                public void handleMessage(Message msg) {
                    if (msg.what == SERVER_ANSWER) {
                        handleServerAnswer((String)msg.obj);
                    }
                }
            };
        }

        public void queueServerAnswer(String answer) {
            handler.obtainMessage(SERVER_ANSWER, answer).sendToTarget();
        }

        private void handleServerAnswer(String answer) {
            Log.i(TAG, "Handle a server answer:" + answer);
        }
    }


    private ApplicationLogic() {}


    public void setActivity(MainActivityBase activity) {
        this.activity = activity;
    }

    public void startApp() {
        network = NetworkImpl.instance();
        network.setListener(this);
        looper = new ApplicationLogicLooper(new Handler());

        activity.applyFragment(new WelcomeFragment());
        looper.getLooper();
        looper.start();
        network.start();
    }

    @Override
    public void setListener(OnNetworkConnectListener listener) {

    }

    @Override
    public void onReceiveMessage(String msg) {
        looper.queueServerAnswer(msg);
    }

    @Override
    public void onNetworkError(String msg) {

    }

    @Override
    public void login(String username, String password, OnLoginFinishedListener listener) {

    }

    @Override
    public void openRegisterScreen() {

    }
}
