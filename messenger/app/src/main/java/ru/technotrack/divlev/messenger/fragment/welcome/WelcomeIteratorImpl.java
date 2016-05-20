package ru.technotrack.divlev.messenger.fragment.welcome;


public class WelcomeIteratorImpl implements WelcomeIterator, WelcomeLogic.OnNetworkConnectLister {
    private WelcomeIterator.OnNetworkConnectListener listener;

    public WelcomeIteratorImpl(OnNetworkConnectListener listener) {
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
