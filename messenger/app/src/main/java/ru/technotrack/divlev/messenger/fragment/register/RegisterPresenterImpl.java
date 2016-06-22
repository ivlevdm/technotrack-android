package ru.technotrack.divlev.messenger.fragment.register;

import ru.technotrack.divlev.messenger.fragment.BaseFragmentActivity;
import ru.technotrack.divlev.messenger.fragment.chatlist.ChatListFragment;
import ru.technotrack.divlev.messenger.logic.ApplicationLogic;
import ru.technotrack.divlev.messenger.logic.ApplicationLogicImpl;

public class RegisterPresenterImpl implements RegisterPresenter, RegisterIterator.RegisterInteractorListener{
    private RegisterView view;
    private RegisterIterator interactor;
    private ApplicationLogic logic = ApplicationLogicImpl.instance();

    public RegisterPresenterImpl(RegisterView view) {
        this.view = view;
        interactor = new RegisterInteractorImpl(this);
        logic.setListener((ApplicationLogic.ApplicationLogicListener)interactor);
    }

    @Override
    public void openLoginScreen() {
        BaseFragmentActivity activity = logic.getActivity();
        activity.finishFragment();
    }

    @Override
    public void openChatListScreen() {
        BaseFragmentActivity activity = logic.getActivity();
        activity.finishFragment();
        activity.finishFragment();
        activity.applyFragment(new ChatListFragment());
    }

    @Override
    public void validateCredentials(String username, String password, String nickname) {
        interactor.validateCredentials(username, password, nickname);
    }

    @Override
    public void onUsernameError(String msg) {
        view.setUserNameError(msg);
    }

    @Override
    public void onPasswordError(String msg) {
        view.setPasswordError(msg);
    }

    @Override
    public void onNicknameError(String msg) {
        view.setNicknameError(msg);
    }

    @Override
    public void onOfflineCheckSuccess(String login, String password, String nickname) {
        logic.register(login, password, nickname);
    }
}
