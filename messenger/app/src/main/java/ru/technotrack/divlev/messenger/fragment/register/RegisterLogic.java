package ru.technotrack.divlev.messenger.fragment.register;


public interface RegisterLogic {

    interface OnLoginFinishedListener {
        void onUsernameError();

        void onConnectionError();
    }

    void register(String username, String password, String nickname, OnLoginFinishedListener listener);

    void openLoginScreen();
}
