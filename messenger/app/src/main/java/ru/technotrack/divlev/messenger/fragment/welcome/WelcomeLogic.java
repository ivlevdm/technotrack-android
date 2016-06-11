package ru.technotrack.divlev.messenger.fragment.welcome;

public interface WelcomeLogic {
    interface WelcomeLogicListener {
        void onConnectStart();

        void onConnectError(String msg);
    }

    void setListener(WelcomeLogicListener listener);
}
