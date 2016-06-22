package ru.technotrack.divlev.messenger.fragment.login;

public interface LoginInteractor {

    interface LoginInteractorListener {
        void onUsernameError(String msg);

        void onPasswordError(String msg);

        void onConnectionError(String msg);

        void onOfflineCheckSuccess(String username, String password);

        void onLoginSuccess();
    }

    void validateCredentials(String username, String password);
}
