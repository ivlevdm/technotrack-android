package ru.technotrack.divlev.messenger.fragment.welcome;


public interface WelcomeInteractor {

    interface WelcomeInteractorListener {
        void onConnectError(String msg);

        void onConnectionChange(boolean is_connecting);
    }

    boolean isConnecting();
}
