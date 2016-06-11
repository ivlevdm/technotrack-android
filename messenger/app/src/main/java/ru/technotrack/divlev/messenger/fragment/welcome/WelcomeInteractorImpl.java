package ru.technotrack.divlev.messenger.fragment.welcome;


import android.util.Log;

import com.google.gson.JsonObject;

import ru.technotrack.divlev.messenger.config.MessageType;
import ru.technotrack.divlev.messenger.logic.ApplicationLogic;

public class WelcomeInteractorImpl implements WelcomeInteractor, ApplicationLogic.ApplicationLogicListener {
    private final String TAG = WelcomeInteractorImpl.class.getSimpleName().toUpperCase();

    private WelcomeInteractorListener listener;
    private boolean is_connecting = false;

    public WelcomeInteractorImpl(WelcomeInteractorListener listener) {
        this.listener = listener;
    }

    @Override
    public void onConnectionStateChange(ConnectionState state, String msg) {
        switch (state) {
            case CONNECTING:
                is_connecting = true;
                listener.onConnectionChange(true);
                break;
            case SUCCESS:
                is_connecting = false;
                listener.onConnectionChange(false);
                break;
            case ERROR:
                is_connecting = false;
                listener.onConnectError(msg);
                break;
            default:
                Log.e(TAG, "Unknown connection state.");
        }
    }

    @Override
    public void onMessageReceived(String type, JsonObject data) {
        switch (MessageType.valueOf(type.toUpperCase())) {
            case WELCOME:
                listener.onConnectionFinished();
                break;
            case AUTH:
                int status = data.get("status").getAsInt();
                if (status == 0) {
                    listener.onLoginSuccess();
                } else {
                    listener.onLoginFail();
                }
                break;
            default:
                Log.e(TAG, "Unexpected message type.");
        }
    }

    @Override
    public boolean isConnecting() {
        return is_connecting;
    }
}
