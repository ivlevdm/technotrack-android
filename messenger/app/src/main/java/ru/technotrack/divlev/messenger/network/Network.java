package ru.technotrack.divlev.messenger.network;


public interface Network {
    interface NetworkListener {
        void onNetworkError(String msg);

        void onReceiveMessage(String msg);
    }

    void start();

    void finish();

    void send(String msg);

    void setListener(NetworkListener listener);
}
