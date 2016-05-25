package ru.technotrack.divlev.messenger.fragment.login;


import ru.technotrack.divlev.messenger.logic.ApplicationLogic;

public class LoginPresenterImpl implements LoginPresenter, LoginInteractor.OnLoginFinishedListener {
    private LoginView view;
    private LoginInteractor interactor;
    private LoginLogic logic = ApplicationLogic.instance();

    public LoginPresenterImpl(LoginView view) {
        this.view = view;
    }

    @Override
    public void validateCredentials(String username, String password) {

    }

    @Override
    public void openRegisterScreen() {

    }

    @Override
    public void onUsernameError(String msg) {

    }

    @Override
    public void onPasswordError(String msg) {

    }

    @Override
    public void onConnectionError(String msg) {

    }

    @Override
    public void onSuccess(String username, String password) {

    }
}
