package ru.technotrack.divlev.messenger.fragment.welcome;


public class WelcomeInteractorImpl implements WelcomeInteractor, WelcomeLogic.WelcomeLogicListener {
    private WelcomeInteractorListener listener;
    private boolean is_connecting = false;

    public WelcomeInteractorImpl(WelcomeInteractorListener listener) {
        this.listener = listener;
    }

    @Override
    public void onConnectStart() {
        is_connecting = true;
        listener.onConnectionChange(true);
    }

    @Override
    public void onConnectError(String msg) {
        listener.onConnectError(msg);
    }

    @Override
    public boolean isConnecting() {
        return is_connecting;
    }
}
