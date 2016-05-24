package ru.technotrack.divlev.messenger.fragment.welcome;

public interface WelcomeLogic {
    interface OnNetworkConnectListener {
        void onConnectStart();

        void onConnectError(String msg);
    }

    void setListener(OnNetworkConnectListener listener);
}
