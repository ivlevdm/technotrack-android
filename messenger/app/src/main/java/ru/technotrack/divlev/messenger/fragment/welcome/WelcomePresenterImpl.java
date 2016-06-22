package ru.technotrack.divlev.messenger.fragment.welcome;


import ru.technotrack.divlev.messenger.fragment.BaseFragmentActivity;
import ru.technotrack.divlev.messenger.fragment.chatlist.ChatListFragment;
import ru.technotrack.divlev.messenger.fragment.login.LoginFragment;
import ru.technotrack.divlev.messenger.logic.ApplicationLogic;
import ru.technotrack.divlev.messenger.logic.ApplicationLogicImpl;

public class WelcomePresenterImpl implements WelcomePresenter,
        WelcomeInteractor.WelcomeInteractorListener {
    private WelcomeView view;
    private WelcomeInteractor interactor;
    private ApplicationLogic logic;

    public WelcomePresenterImpl(WelcomeView view) {
        this.view = view;

        interactor = new WelcomeInteractorImpl(this);
        logic = ApplicationLogicImpl.instance();
        logic.setListener((ApplicationLogic.ApplicationLogicListener)interactor);
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
    public void onLoginFail() {
        view.hideProgress();
        logic.getActivity().finishFragment();
        logic.getActivity().applyFragment(new LoginFragment());
    }

    @Override
    public void onLoginSuccess() {
        BaseFragmentActivity activity = logic.getActivity();
        activity.finishFragment();
        activity.applyFragment(new ChatListFragment());
    }

    @Override
    public void onConnectionFinished() {
        logic.restoreConnection();
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
