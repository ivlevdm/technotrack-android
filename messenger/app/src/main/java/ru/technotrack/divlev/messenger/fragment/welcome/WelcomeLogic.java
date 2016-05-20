package ru.technotrack.divlev.messenger.fragment.welcome;

public interface WelcomeLogic {
    interface OnNetworkConnectLister {
        void onConnectStart();

        void onConnectError(String msg);
    }

    void setListener(WelcomeLogic.OnNetworkConnectLister listener);
}
