package ru.technotrack.divlev.messenger.fragment.register;

public interface RegisterIterator {
    interface RegisterInteractorListener {
        void onUsernameError(String msg);

        void onPasswordError(String msg);

        void onNicknameError(String msg);

        void onOfflineCheckSuccess(String login, String password, String nickname);

        void onSuccess();
    }

    void validateCredentials(String username, String password, String nickname);
}
