package ru.technotrack.divlev.messenger.fragment.login;

import android.os.AsyncTask;
import android.util.Log;

import com.google.gson.JsonObject;

import ru.technotrack.divlev.messenger.config.ApplicationConfig;
import ru.technotrack.divlev.messenger.config.MessageType;
import ru.technotrack.divlev.messenger.logic.ApplicationLogic;
import ru.technotrack.divlev.messenger.util.Utils;


public class LoginInteractorImpl implements LoginInteractor, ApplicationLogic.ApplicationLogicListener {
    private static String TAG = LoginInteractorImpl.class.getSimpleName().toUpperCase();

    private LoginInteractorListener listener;
    private String login;

    private class CalculateMD5Task extends AsyncTask<String, Void, String> {
        LoginInteractorImpl interactor;

        public CalculateMD5Task(LoginInteractorImpl interactor) {
            this.interactor = interactor;
        }

        @Override
        protected String doInBackground(String... params) {
            return Utils.md5(params[0]);
        }

        @Override
        protected void onPostExecute(String s) {
            interactor.listener.onOfflineCheckSuccess(interactor.login, s);
        }
    }

    public LoginInteractorImpl(LoginInteractorListener listener) {
        this.listener = listener;
    }

    @Override
    public void validateCredentials(String username, String password) {
        if (username.isEmpty()) {
            listener.onUsernameError("Login is empty.");
            return;
        }

        if (password.isEmpty()) {
            listener.onPasswordError("Password is empty.");
            return;
        }

        login = username;
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
                        listener.onLoginSuccess();
                        break;
                    case ApplicationConfig.ServerError.ErrNeedRegister:
                        listener.onUsernameError("Login doesn't exist.");
                        break;
                    case ApplicationConfig.ServerError.ErrInvalidPass:
                        listener.onPasswordError("Password is incorrect.");
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
