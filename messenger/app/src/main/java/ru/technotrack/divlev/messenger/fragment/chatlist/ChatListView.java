package ru.technotrack.divlev.messenger.fragment.chatlist;


public interface ChatListView {
    void showProgress();

    void hideProgress();

    void setUserNameError();

    void setPasswordError();

    void showConnectionError();
}
