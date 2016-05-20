package ru.technotrack.divlev.messenger.fragment.welcome;

public interface WelcomeView {
    void showProgress();

    void hideProgress();

    void showError(String msg);
}
