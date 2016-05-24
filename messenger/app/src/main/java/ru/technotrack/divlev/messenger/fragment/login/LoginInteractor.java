package ru.technotrack.divlev.messenger.fragment.login;

public interface LoginInteractor {

    interface OnLoginFinishedListener {
        void onUsernameError(String msg);

        void onPasswordError(String msg);

        void onConnectionError(String msg);

        void onSuccess(String username, String password);
    }

    void validateCredentials(String username, String password, OnLoginFinishedListener listener);
}
