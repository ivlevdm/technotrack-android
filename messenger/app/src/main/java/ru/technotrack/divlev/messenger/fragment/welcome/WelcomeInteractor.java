package ru.technotrack.divlev.messenger.fragment.welcome;


public interface WelcomeInteractor {

    interface OnNetworkConnectListener {
        void onConnectStart();

        void onConnectError(String msg);
    }
}
