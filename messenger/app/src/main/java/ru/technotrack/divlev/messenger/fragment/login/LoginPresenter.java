package ru.technotrack.divlev.messenger.fragment.login;


public interface LoginPresenter {
    void validateCredentials(String username, String password);

    void openRegisterScreen();
}