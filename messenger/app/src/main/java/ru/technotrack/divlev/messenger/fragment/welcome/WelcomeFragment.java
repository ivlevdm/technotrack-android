package ru.technotrack.divlev.messenger.fragment.welcome;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import ru.technotrack.divlev.messenger.R;
import ru.technotrack.divlev.messenger.fragment.BaseFragment;

public class WelcomeFragment extends BaseFragment implements WelcomeView {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View result = inflater.inflate(R.layout.welcome_fragment, container, false);
        return result;
    }

    @Override
    public void showProgress() {

    }

    @Override
    public void hideProgress() {

    }

    @Override
    public void showError(String msg) {

    }
}
