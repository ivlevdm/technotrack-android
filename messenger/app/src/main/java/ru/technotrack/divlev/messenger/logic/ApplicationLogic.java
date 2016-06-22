package ru.technotrack.divlev.messenger.logic;


import com.google.gson.JsonObject;

import ru.technotrack.divlev.messenger.fragment.BaseFragmentActivity;

public interface ApplicationLogic {
    interface ApplicationLogicListener {
        enum ConnectionState {
            CONNECTING,
            SUCCESS,
            ERROR
        }

        void onConnectionStateChange(ConnectionState state, String msg);

        void onMessageReceived(String type, JsonObject data);
    }

    void setListener(ApplicationLogicListener listener);

    BaseFragmentActivity getActivity();

    void restoreConnection();

    void login(String username, String password);

    void register(String username, String password, String nickname);

    void uploadChatList();
}
