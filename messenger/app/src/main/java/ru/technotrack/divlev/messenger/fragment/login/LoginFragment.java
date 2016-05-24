package ru.technotrack.divlev.messenger.fragment.login;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import ru.technotrack.divlev.messenger.R;
import ru.technotrack.divlev.messenger.fragment.BaseFragment;

public class LoginFragment extends BaseFragment implements LoginView {
    private LoginPresenter presenter;
    private ProgressBar progressBar;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        presenter = new LoginPresenterImpl(this);
        progressBar = (ProgressBar) findViewById(R.id.progress);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View result = inflater.inflate(R.layout.login_fragment, container, false);
        return result;
    }

    @Override
    public void showProgress() {
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgress() {
        progressBar.setVisibility(View.INVISIBLE);
    }

    private void showMessage(String msg) {
        Toast.makeText(getContext(), msg, Toast.LENGTH_LONG).show();
    }

    @Override
    public void setUserNameError(String msg) {
        showMessage(msg);
    }

    @Override
    public void setPasswordError(String msg) {
        showMessage(msg);
    }

    @Override
    public void showConnectionError(String msg) {
        showMessage(msg);
    }
}
