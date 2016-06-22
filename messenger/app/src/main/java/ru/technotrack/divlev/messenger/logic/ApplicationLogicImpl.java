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
import ru.technotrack.divlev.messenger.config.ApplicationConfig;
import ru.technotrack.divlev.messenger.fragment.BaseFragmentActivity;
import ru.technotrack.divlev.messenger.fragment.welcome.WelcomeFragment;
import ru.technotrack.divlev.messenger.network.Network;
import ru.technotrack.divlev.messenger.network.NetworkImpl;
import ru.technotrack.divlev.messenger.util.JSONUtil;

public class ApplicationLogicImpl implements ApplicationLogic, Network.NetworkListener {
    private static ApplicationLogicImpl appLogic;
    private static String TAG = ApplicationLogicImpl.class.getSimpleName().toUpperCase();
    private static boolean isNetStarted = false;

    private MainActivityBase activity;

    private ApplicationLogicListener listener;
    private Network network;
    private ApplicationLogicLooper looper;

    private final int MESSAGE_SERVER_ANSWER = 0;
    private final int MESSAGE_RESTORE_CONNECTION = 1;
    private final int MESSAGE_LOGIN = 2;
    private final int MESSAGE_START_NETWORK = 3;
    private final int MESSAGE_REGISTER = 4;
    private final int MESSAGE_RESTART_NETWORK = 5;
    private final int MESSAGE_UPLOAD_CHATLIST = 6;

    public static ApplicationLogicImpl instance() {
        if (appLogic == null) {
            appLogic = new ApplicationLogicImpl();
        }
        return appLogic;
    }

    private class ApplicationLogicLooper extends HandlerThread {
        private Handler handler;
        private Handler responseHandler;

        private class UserRegistrationInfo {
            String login;
            String pass;
            String nickname;

            public UserRegistrationInfo(String login, String pass, String nickname) {
                this.login = login;
                this.pass = pass;
                this.nickname = nickname;
            }
        }

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
                        case MESSAGE_REGISTER:
                            handleRegistration((UserRegistrationInfo) msg.obj);
                            break;
                        case MESSAGE_RESTART_NETWORK:
                            handleRestartNetwork();
                            break;
                        case MESSAGE_UPLOAD_CHATLIST:
                            handleUploadChatList();
                            break;
                        default:
                            Log.e(TAG, "Unknown message.");
                    }
                }
            };
        }

        private void storeLoginInfo(String login, String pass) {
            SharedPreferences sharedPref = activity.getPreferences(Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPref.edit();
            editor.putString("saved_login", login);
            editor.putString("saved_pass", pass);
            editor.apply();
        }

        private void resetLoginInfo() {
            storeLoginInfo("", "");
        }

        private void queueServerAnswer(String answer) {
            handler.obtainMessage(MESSAGE_SERVER_ANSWER, answer).sendToTarget();
        }

        private void handleServerAnswer(String answer) {
            Log.i(TAG, "Handle a server answer:" + answer);

            Pair<String, JsonObject> message = JSONUtil.parseMessage(answer);

            if (message.first.equals("auth") && JSONUtil.getDataStatus(message.second) != 0) {
                resetLoginInfo();
            }

            //FIXME: dirty hack
            if (message.first.equals("auth") && JSONUtil.getDataStatus(message.second) == 0) {
                ApplicationConfig.cid = message.second.get("cid").getAsString();
                ApplicationConfig.sid = message.second.get("sid").getAsString();
            }

            processServerAnswer(message);
        }

        private void processServerAnswer(final Pair<String, JsonObject> message) {
            responseHandler.post(new Runnable() {
                @Override
                public void run() {
                    listener.onMessageReceived(message.first, message.second);
                }
            });
        }

        private void queueLogin(String username, String pass) {
            handler.obtainMessage(MESSAGE_LOGIN, new Pair<>(username, pass)).sendToTarget();
        }

        private void handleLogin(Pair<String, String> loginInfo) {
            storeLoginInfo(loginInfo.first, loginInfo.second);
            network.send(JSONUtil.prepareLoginJson(loginInfo.first, loginInfo.second));
        }

        private void queueRegistration(String username, String pass, String nickname) {
            UserRegistrationInfo info = new UserRegistrationInfo(username, pass, nickname);
            handler.obtainMessage(MESSAGE_REGISTER, info).sendToTarget();
        }

        private void handleRegistration(UserRegistrationInfo info) {
            network.send(JSONUtil.prepareRegisterJson(info.login, info.pass, info.nickname));
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
                return;
            }

            queueLogin(login, pass);
        }

        private void queueStartNetwork() {
            handler.obtainMessage(MESSAGE_START_NETWORK).sendToTarget();
        }

        private void queueRestartNetwork() {
            handler.obtainMessage(MESSAGE_RESTART_NETWORK).sendToTarget();
        }

        private void handleRestartNetwork() {
            processNetworkStatusChange(ApplicationLogicListener.ConnectionState.ERROR, "");
            network.finish();
            network.start();
            processNetworkStatusChange(ApplicationLogicListener.ConnectionState.CONNECTING, "");
        }

        private void handleStartNetwork() {
            network.start();
            processNetworkStatusChange(ApplicationLogicListener.ConnectionState.CONNECTING, "");
        }

        private void processMessageReceive(final String action, final JsonObject data) {
            responseHandler.post(new Runnable() {
                @Override
                public void run() {
                    listener.onMessageReceived(action, data);
                }
            });
        }

        private void processNetworkStatusChange(final ApplicationLogicListener.ConnectionState state,
                                                final String msg) {
            responseHandler.post(new Runnable() {
                @Override
                public void run() {
                    listener.onConnectionStateChange(state, msg);
                }
            });
        }

        private void queueUploadChatList() {
            handler.obtainMessage(MESSAGE_UPLOAD_CHATLIST).sendToTarget();
        }

        private void handleUploadChatList() {
            network.send(JSONUtil.prepareChannelListJson(ApplicationConfig.cid, ApplicationConfig.sid));
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
        if (isNetStarted)
            return;
        looper.queueStartNetwork();
        isNetStarted = true;
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
    public void onNetworkError(Network.NetworkErrorType error) {
        switch (error) {
            case SOCKET_IS_CLOSED:
                looper.queueRestartNetwork();
                break;
            case SOCKET_OPENING_FAILED:
                break;
            default:
                Log.e(TAG, "Unknown network error.");
        }
    }

    @Override
    public void login(String username, String password) {
        looper.queueLogin(username, password);
    }

    @Override
    public void register(String username, String password, String nickname) {
        looper.queueRegistration(username, password, nickname);
    }

    @Override
    public void uploadChatList() {
        looper.queueUploadChatList();
    }
}
