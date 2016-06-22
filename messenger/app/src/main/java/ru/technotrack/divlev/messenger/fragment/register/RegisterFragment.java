package ru.technotrack.divlev.messenger.fragment.register;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import ru.technotrack.divlev.messenger.R;
import ru.technotrack.divlev.messenger.fragment.BaseFragment;

public class RegisterFragment extends BaseFragment implements RegisterView {
    private RegisterPresenter presenter;
    private ProgressBar progressBar;
    private Button loginButton;
    private Button registerButton;
    private EditText login;
    private EditText password;
    private EditText nickname;

    public RegisterFragment() {
        presenter = new RegisterPresenterImpl(this);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        progressBar = (ProgressBar) findViewById(R.id.progress);
        loginButton = (Button) findViewById(R.id.loginButton);
        registerButton = (Button) findViewById(R.id.registerButton);
        login = (EditText) findViewById(R.id.loginText);
        password = (EditText) findViewById(R.id.passText);
        nickname = (EditText) findViewById(R.id.nickText);

        loginButton.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        presenter.openLoginScreen();
                    }
                }
        );

        registerButton.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        presenter.validateCredentials(login.getText().toString(),
                                password.getText().toString(), nickname.getText().toString());
                    }
                }
        );
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View result = inflater.inflate(R.layout.register_fragment, container, false);
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

    @Override
    public void setUserNameError(String msg) {
        showMessage(msg);
    }

    @Override
    public void setPasswordError(String msg) {
        showMessage(msg);
    }

    @Override
    public void setNicknameError(String msg) {
        showMessage(msg);
    }

    @Override
    public void showConnectionError(String msg) {
        showMessage(msg);
    }

    private void showMessage(String msg) {
        Toast.makeText(getContext(), msg, Toast.LENGTH_LONG).show();
    }
}
