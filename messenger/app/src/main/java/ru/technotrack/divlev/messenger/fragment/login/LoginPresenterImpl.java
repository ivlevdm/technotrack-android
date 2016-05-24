package ru.technotrack.divlev.messenger.fragment.login;


import ru.technotrack.divlev.messenger.logic.ApplicationLogic;

public class LoginPresenterImpl implements LoginPresenter, LoginInteractor.OnLoginFinishedListener {
    private LoginView view;
    private LoginInteractor interactor;
    private LoginLogic logic = ApplicationLogic.instance();

    public LoginPresenterImpl(LoginView view) {
        this.view = view;
    }


}
