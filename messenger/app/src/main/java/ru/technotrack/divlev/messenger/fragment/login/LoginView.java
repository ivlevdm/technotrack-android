package ru.technotrack.divlev.messenger.fragment.login;


public interface LoginView {
    void showProgress();

    void hideProgress();

    void setUserNameError();

    void setPasswordError();

    void showConnectionError();
}
