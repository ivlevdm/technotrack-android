package ru.technotrack.divlev.messenger.fragment.login;

public interface LoginIterator {

    interface OnLoginFinishedListener {
        void onUsernameError();

        void onPasswordError();

        void onConnectionError();

        void onSuccess();
    }


}
