package ru.technotrack.divlev.messenger.fragment.register;


import android.os.AsyncTask;
import android.util.Log;

import com.google.gson.JsonObject;

import ru.technotrack.divlev.messenger.config.ApplicationConfig;
import ru.technotrack.divlev.messenger.config.MessageType;
import ru.technotrack.divlev.messenger.logic.ApplicationLogic;
import ru.technotrack.divlev.messenger.util.Utils;

public class RegisterInteractorImpl implements RegisterIterator, ApplicationLogic.ApplicationLogicListener {
    private final String TAG = RegisterInteractorImpl.class.getSimpleName().toUpperCase();
    private RegisterInteractorListener listener;
    private String login;
    private String nickname;

    private class CalculateMD5Task extends AsyncTask<String, Void, String> {
        RegisterInteractorImpl interactor;

        public CalculateMD5Task(RegisterInteractorImpl interactor) {
            this.interactor = interactor;
        }

        @Override
        protected String doInBackground(String... params) {
            return Utils.md5(params[0]);
        }

        @Override
        protected void onPostExecute(String s) {
            interactor.listener.onOfflineCheckSuccess(interactor.login, s, interactor.nickname);
        }
    }

    public RegisterInteractorImpl(RegisterInteractorListener listener) {
        this.listener = listener;
    }

    @Override
    public void validateCredentials(String username, String password, String nickname) {
        if (username.isEmpty()) {
            listener.onUsernameError("Login is empty.");
            return;
        }

        if (password.isEmpty()) {
            listener.onPasswordError("Password is empty.");
            return;
        }

        if (nickname.isEmpty()) {
            listener.onNicknameError("Nickname is empty.");
        }

        login = username;
        this.nickname = nickname;

        new CalculateMD5Task(this).execute(password);
    }

    @Override
    public void onConnectionStateChange(ConnectionState state, String msg) {

    }

    @Override
    public void onMessageReceived(String type, JsonObject data) {
        switch (MessageType.valueOf(type.toUpperCase())) {
            case WELCOME:
                break;
            case AUTH:
                int status = data.get("status").getAsInt();

                switch (status) {
                    case ApplicationConfig.ServerError.ErrOK:
                        listener.onSuccess();
                        break;
                    default:
                        Log.e(TAG, "Unexpected auth error: " + status);
                }
                break;
            default:
                Log.e(TAG, "Unexpected message type: " + type + ".");
        }
    }
}
