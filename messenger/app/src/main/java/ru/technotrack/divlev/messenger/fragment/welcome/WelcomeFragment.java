package ru.technotrack.divlev.messenger.fragment.welcome;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import ru.technotrack.divlev.messenger.R;
import ru.technotrack.divlev.messenger.fragment.BaseFragment;

public class WelcomeFragment extends BaseFragment implements WelcomeView {
    private ProgressBar progressBar;
    private WelcomePresenter presenter;

    public WelcomeFragment() {
        presenter = new WelcomePresenterImpl(this);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        progressBar = (ProgressBar) findViewById(R.id.progress);
    }

    @Override
    public void onResume() {
        super.onResume();

        if (presenter.isConnecting()) {
            progressBar.setVisibility(View.VISIBLE);
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View result = inflater.inflate(R.layout.welcome_fragment, container, false);
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
    public void showError(String msg) {
        Toast.makeText(getContext(), msg, Toast.LENGTH_LONG).show();
    }
}
