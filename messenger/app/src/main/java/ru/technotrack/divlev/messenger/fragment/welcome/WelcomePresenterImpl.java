package ru.technotrack.divlev.messenger.fragment.welcome;


import ru.technotrack.divlev.messenger.logic.ApplicationLogic;

public class WelcomePresenterImpl implements WelcomePresenter,
        WelcomeInteractor.WelcomeInteractorListener {
    private WelcomeView view;
    private WelcomeInteractor interactor;
    private WelcomeLogic logic;

    public WelcomePresenterImpl(WelcomeView view) {
        this.view = view;

        interactor = new WelcomeInteractorImpl(this);
        logic = ApplicationLogic.instance();
        logic.setListener((WelcomeLogic.WelcomeLogicListener) interactor);
    }


    @Override
    public void onConnectError(String msg) {
        view.hideProgress();
        view.showError(msg);
    }

    @Override
    public boolean isConnecting() {
        return interactor.isConnecting();
    }

    @Override
    public void onConnectionChange(boolean is_connecting) {
        if (is_connecting) {
            view.showProgress();
        } else {
            view.hideProgress();
        }
    }
}
