package ru.technotrack.divlev.messenger.fragment.welcome;


public class WelcomeInteractorImpl implements WelcomeInteractor, WelcomeLogic.OnNetworkConnectListener {
    private WelcomeInteractor.OnNetworkConnectListener listener;

    public WelcomeInteractorImpl(OnNetworkConnectListener listener) {
        this.listener = listener;
    }

    @Override
    public void onConnectStart() {
        listener.onConnectStart();
    }

    @Override
    public void onConnectError(String msg) {
        listener.onConnectError(msg);
    }
}
