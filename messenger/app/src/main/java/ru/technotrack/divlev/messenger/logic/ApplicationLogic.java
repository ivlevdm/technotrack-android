package ru.technotrack.divlev.messenger.logic;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Message;
import android.util.Log;
import android.util.Pair;

import ru.technotrack.divlev.messenger.MainActivityBase;
import ru.technotrack.divlev.messenger.R;
import ru.technotrack.divlev.messenger.fragment.login.LoginFragment;
import ru.technotrack.divlev.messenger.fragment.login.LoginLogic;
import ru.technotrack.divlev.messenger.fragment.welcome.WelcomeFragment;
import ru.technotrack.divlev.messenger.fragment.welcome.WelcomeLogic;
import ru.technotrack.divlev.messenger.network.Network;
import ru.technotrack.divlev.messenger.network.NetworkImpl;
import ru.technotrack.divlev.messenger.util.JSONUtil;

public class ApplicationLogic implements Network.NetworkListener, WelcomeLogic, LoginLogic {
    private static ApplicationLogic appLogic;
    private static String TAG = ApplicationLogic.class.getSimpleName().toUpperCase();

    private MainActivityBase activity;
    private OnNetworkConnectListener welcomeLister;
    private Network network;
    private ApplicationLogicLooper looper;

    private final int MESSAGE_SERVER_ANSWER = 0;
    private final int MESSAGE_RESTORE_CONNECTION = 1;
    private final int MESSAGE_LOGIN = 2;

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
                    switch (msg.what) {
                        case MESSAGE_SERVER_ANSWER:
                            handleServerAnswer((String)msg.obj);
                            break;
                        case MESSAGE_RESTORE_CONNECTION:
                            handleRestoreConnection();
                            break;
                        case MESSAGE_LOGIN:
                            handleLogin((Pair<String, String>)msg.obj);
                            break;
                        default:
                            Log.e(TAG, "Unknown message.");
                    }
                }
            };
        }

        private void queueServerAnswer(String answer) {
            handler.obtainMessage(MESSAGE_SERVER_ANSWER, answer).sendToTarget();
        }

        private void handleServerAnswer(String answer) {
            Log.i(TAG, "Handle a server answer:" + answer);
        }

        private void queueLogin(String username, String pass) {
            handler.obtainMessage(MESSAGE_LOGIN, new Pair<>(username, pass));
        }

        private void handleLogin(Pair<String, String> loginInfo) {

            network.send(JSONUtil.prepareLoginJson(loginInfo.first, loginInfo.second));
        }

        private void queueRestoreConnection() {
            handler.obtainMessage(MESSAGE_RESTORE_CONNECTION).sendToTarget();
        }

        private void handleRestoreConnection() {
            SharedPreferences sharedPref = activity.getPreferences(Context.MODE_PRIVATE);
            String login = sharedPref.getString("saved_login", "");
            String pass = sharedPref.getString("saved_pass", "");

            if (login.isEmpty() || pass.isEmpty()) {
                Log.i(TAG, "A password or a login isn't stored.");
                processLoginScreenOpening();
            }

            queueLogin(login, pass);
        }

        private void processLoginScreenOpening() {
            responseHandler.post(new Runnable() {
                @Override
                public void run() {
                    activity.applyFragment(new LoginFragment());
                }
            });
        }
    }


    private ApplicationLogic() {}


    public void setActivity(MainActivityBase activity) {
        this.activity = activity;
    }

    public void startApp() {
        activity.applyFragment(new WelcomeFragment());

        network = NetworkImpl.instance();
        network.setListener(this);
        looper = new ApplicationLogicLooper(new Handler());

        looper.getLooper();
        looper.start();

        welcomeLister.onConnectError("Hello!");
        network.start();
        //looper.queueRestoreConnection();
    }

    @Override
    public void setListener(OnNetworkConnectListener listener) {
        this.welcomeLister = listener;
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
