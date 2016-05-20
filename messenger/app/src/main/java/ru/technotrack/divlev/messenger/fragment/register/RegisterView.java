package ru.technotrack.divlev.messenger.fragment.register;


public interface RegisterView {
    void showProgress();

    void hideProgress();

    void setUserNameError();

    void setPasswordError();

    void setNicknameError();

    void showConnectionError();
}
