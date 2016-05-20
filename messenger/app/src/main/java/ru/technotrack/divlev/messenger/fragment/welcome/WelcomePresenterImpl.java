package ru.technotrack.divlev.messenger.fragment.welcome;


public class WelcomePresenterImpl implements WelcomePresenter,
        WelcomeIterator.OnNetworkConnectListener {
    private WelcomeView view;

    public WelcomePresenterImpl(WelcomeView view) {
        this.view = view;
    }

    @Override
    public void onConnectStart() {
        view.showProgress();
    }

    @Override
    public void onConnectError(String msg) {
        view.hideProgress();
        view.showError(msg);
    }
}
