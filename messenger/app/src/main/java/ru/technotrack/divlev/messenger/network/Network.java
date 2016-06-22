package ru.technotrack.divlev.messenger.network;


public interface Network {
    enum NetworkErrorType {
        SOCKET_OPENING_FAILED,
        SOCKET_IS_CLOSED
    }

    interface NetworkListener {
        void onNetworkError(NetworkErrorType error);

        void onReceiveMessage(String msg);
    }

    void start();

    void finish();

    void send(String msg);

    void setListener(NetworkListener listener);
}
