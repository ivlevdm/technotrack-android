package ru.technotrack.divlev.messenger.fragment.register;


public interface RegisterView {
    void showProgress();

    void hideProgress();

    void setUserNameError(String msg);

    void setPasswordError(String msg);

    void setNicknameError(String msg);

    void showConnectionError(String msg);
}
