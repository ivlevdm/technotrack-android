package ru.technotrack.divlev.messenger.fragment.welcome;


public interface WelcomeIterator {

    interface OnNetworkConnectListener {
        void onConnectStart();

        void onConnectError(String msg);
    }
}
