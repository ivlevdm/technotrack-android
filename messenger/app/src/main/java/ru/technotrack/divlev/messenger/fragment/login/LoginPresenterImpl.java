package ru.technotrack.divlev.messenger.fragment.login;


public class LoginPresenterImpl implements LoginPresenter, LoginIterator.OnLoginFinishedListener {
    private LoginView view;

    public LoginPresenterImpl(LoginView view) {
        this.view = view;
    }
}
