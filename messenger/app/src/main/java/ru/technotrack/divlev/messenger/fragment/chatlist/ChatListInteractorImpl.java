package ru.technotrack.divlev.messenger.fragment.chatlist;


import com.google.gson.JsonObject;

import ru.technotrack.divlev.messenger.logic.ApplicationLogic;

public class ChatListInteractorImpl implements ChatListInteractor, ApplicationLogic.ApplicationLogicListener {
    ChatListInteractorListener listener;

    public ChatListInteractorImpl(ChatListInteractorListener listener) {
        this.listener = listener;
    }

    @Override
    public void onConnectionStateChange(ConnectionState state, String msg) {

    }

    @Override
    public void onMessageReceived(String type, JsonObject data) {

    }
}
