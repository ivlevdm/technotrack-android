package ru.technotrack.divlev.messenger.fragment.welcome;


import ru.technotrack.divlev.messenger.logic.ApplicationLogic;

public class WelcomePresenterImpl implements WelcomePresenter,
        WelcomeInteractor.OnNetworkConnectListener {
    private WelcomeView view;
    private WelcomeInteractor interactor;
    private WelcomeLogic logic;

    public WelcomePresenterImpl(WelcomeView view) {
        this.view = view;

        interactor = new WelcomeInteractorImpl(this);
        logic = ApplicationLogic.instance();
        logic.setListener((WelcomeLogic.OnNetworkConnectListener) interactor);
    }

    @Override
    public void onConnectStart() {
        view.showProgress();
    }

    @Override
    public void onConnectError(String msg) {
        //view.hideProgress();
        view.showError(msg);
    }
}
