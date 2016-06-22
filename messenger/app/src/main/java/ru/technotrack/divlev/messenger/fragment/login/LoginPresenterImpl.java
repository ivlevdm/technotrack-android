package ru.technotrack.divlev.messenger.fragment.login;


import ru.technotrack.divlev.messenger.fragment.BaseFragment;
import ru.technotrack.divlev.messenger.fragment.BaseFragmentActivity;
import ru.technotrack.divlev.messenger.fragment.chatlist.ChatListFragment;
import ru.technotrack.divlev.messenger.fragment.register.RegisterFragment;
import ru.technotrack.divlev.messenger.logic.ApplicationLogic;
import ru.technotrack.divlev.messenger.logic.ApplicationLogicImpl;

public class LoginPresenterImpl implements LoginPresenter, LoginInteractor.LoginInteractorListener {
    private LoginView view;
    private LoginInteractor interactor;
    private ApplicationLogic logic = ApplicationLogicImpl.instance();

    public LoginPresenterImpl(LoginView view) {
        this.view = view;
        interactor = new LoginInteractorImpl(this);
        logic.setListener((ApplicationLogic.ApplicationLogicListener)interactor);
    }

    @Override
    public void validateCredentials(String username, String password) {
        view.showProgress();
        interactor.validateCredentials(username, password);
    }

    @Override
    public void openRegisterScreen() {
        BaseFragmentActivity activity = logic.getActivity();
        activity.applyFragment(new RegisterFragment());
    }

    @Override
    public void onUsernameError(String msg) {
        view.hideProgress();
        view.setUserNameError(msg);
    }

    @Override
    public void onPasswordError(String msg) {
        view.hideProgress();
        view.setPasswordError(msg);
    }

    @Override
    public void onConnectionError(String msg) {
        view.hideProgress();
        view.showConnectionError(msg);
    }

    @Override
    public void onOfflineCheckSuccess(String username, String password) {
        logic.login(username, password);
    }

    @Override
    public void onLoginSuccess() {
        BaseFragmentActivity activity = logic.getActivity();
        activity.finishFragment();
        activity.applyFragment(new ChatListFragment());
    }
}
