package ru.technotrack.divlev.messenger.fragment.register;


public interface RegisterPresenter {
    void validateCredentials(String username, String password, String nickname);

    void openLogin();
}
