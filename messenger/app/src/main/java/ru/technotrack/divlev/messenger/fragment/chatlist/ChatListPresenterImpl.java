package ru.technotrack.divlev.messenger.fragment.chatlist;


import ru.technotrack.divlev.messenger.logic.ApplicationLogic;
import ru.technotrack.divlev.messenger.logic.ApplicationLogicImpl;

public class ChatListPresenterImpl implements ChatListPresenter, ChatListInteractor.ChatListInteractorListener {
    private ChatListInteractor interactor;
    private ChatListView view;
    private ApplicationLogic logic = ApplicationLogicImpl.instance();

    public ChatListPresenterImpl(ChatListView view) {
        this.view = view;
        interactor = new ChatListInteractorImpl(this);
        logic.setListener((ApplicationLogic.ApplicationLogicListener)interactor);
    }

    @Override
    public void uploadChatList() {
        logic.uploadChatList();
    }
}
