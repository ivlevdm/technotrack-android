package ru.technotrack.divlev.messenger.logic;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Message;
import android.util.Log;
import android.util.Pair;

import com.google.gson.JsonObject;

import ru.technotrack.divlev.messenger.MainActivityBase;
import ru.technotrack.divlev.messenger.fragment.BaseFragmentActivity;
import ru.technotrack.divlev.messenger.fragment.login.LoginFragment;
import ru.technotrack.divlev.messenger.fragment.welcome.WelcomeFragment;
import ru.technotrack.divlev.messenger.network.Network;
import ru.technotrack.divlev.messenger.network.NetworkImpl;
import ru.technotrack.divlev.messenger.util.JSONUtil;

public class ApplicationLogicImpl implements ApplicationLogic, Network.NetworkListener {
    private static ApplicationLogicImpl appLogic;
    private static String TAG = ApplicationLogicImpl.class.getSimpleName().toUpperCase();

    private MainActivityBase activity;

    private ApplicationLogicListener listener;
    private Network network;
    private ApplicationLogicLooper looper;

    private final int MESSAGE_SERVER_ANSWER = 0;
    private final int MESSAGE_RESTORE_CONNECTION = 1;
    private final int MESSAGE_LOGIN = 2;
    private final int MESSAGE_START_NETWORK = 3;

    public static ApplicationLogicImpl instance() {
        if (appLogic == null) {
            appLogic = new ApplicationLogicImpl();
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
                            handleRestoreSession();
                            break;
                        case MESSAGE_LOGIN:
                            handleLogin((Pair<String, String>)msg.obj);
                            break;
                        case MESSAGE_START_NETWORK:
                            handleStartNetwork();
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

            Pair<String, JsonObject> message = JSONUtil.parseMessage(answer);

            listener.onMessageReceived(message.first, message.second);
        }

        private void queueLogin(String username, String pass) {
            handler.obtainMessage(MESSAGE_LOGIN, new Pair<>(username, pass));
        }

        private void handleLogin(Pair<String, String> loginInfo) {
            network.send(JSONUtil.prepareLoginJson(loginInfo.first, loginInfo.second));
        }

        private void queueRestoreSession() {
            handler.obtainMessage(MESSAGE_RESTORE_CONNECTION).sendToTarget();
        }

        private void handleRestoreSession() {
            SharedPreferences sharedPref = activity.getPreferences(Context.MODE_PRIVATE);
            String login = sharedPref.getString("saved_login", "");
            String pass = sharedPref.getString("saved_pass", "");

            if (login.isEmpty() || pass.isEmpty()) {
                Log.i(TAG, "A password or a login isn't stored.");
                processMessageReceive("auth", JSONUtil.getLoginFailJson());
            }

            queueLogin(login, pass);
        }

        private void queueStartNetwork() {
            handler.obtainMessage(MESSAGE_START_NETWORK).sendToTarget();
        }

        private void handleStartNetwork() {
            network.start();
            processStartNetwork();
        }

        private void processMessageReceive(final String action, final JsonObject data) {
            responseHandler.post(new Runnable() {
                @Override
                public void run() {
                    listener.onMessageReceived(action, data);
                }
            });
        }

        private void processStartNetwork() {
            responseHandler.post(new Runnable() {
                @Override
                public void run() {
                    listener.onConnectionStateChange(
                            ApplicationLogicListener.ConnectionState.CONNECTING, null);
                }
            });
        }
    }


    private ApplicationLogicImpl() {}


    public void setActivity(MainActivityBase activity) {
        this.activity = activity;
    }

    public void startApp() {
        activity.applyFragment(new WelcomeFragment());

        network = NetworkImpl.instance();
        network.setListener(this);
        looper = new ApplicationLogicLooper(new Handler());

        looper.start();
        looper.getLooper();
    }

    public void startNetwork() {
        looper.queueStartNetwork();
    }

    @Override
    public void setListener(ApplicationLogicListener listener) {
        this.listener = listener;
    }

    @Override
    public BaseFragmentActivity getActivity() {
        return activity;
    }

    @Override
    public void restoreConnection() {
        looper.queueRestoreSession();
    }

    @Override
    public void onReceiveMessage(String msg) {
        looper.queueServerAnswer(msg);
    }

    @Override
    public void onNetworkError(String msg) {

    }

    @Override
    public void login(String username, String password) {

    }
}
