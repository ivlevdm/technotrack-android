package ru.technotrack.divlev.messenger.fragment.login;


public interface LoginView {
    void showProgress();

    void hideProgress();

    void setUserNameError(String msg);

    void setPasswordError(String msg);

    void showConnectionError(String msg);
}
