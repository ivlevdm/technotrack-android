package ru.technotrack.divlev.messenger.fragment.welcome;


public interface WelcomeInteractor {

    interface WelcomeInteractorListener {
        void onLoginFail();

        void onLoginSuccess();

        void onConnectionFinished();

        void onConnectError(String msg);

        void onConnectionChange(boolean is_connecting);
    }

    boolean isConnecting();
}
