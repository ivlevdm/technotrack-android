package ru.technotrack.divlev.messenger.fragment.login;


public interface LoginLogic {

    interface OnLoginFinishedListener {
        void onUsernameError();

        void onPasswordError();

        void onConnectionError();
    }

    void login(String username, String password, OnLoginFinishedListener listener);

    void openRegisterScreen();
}

