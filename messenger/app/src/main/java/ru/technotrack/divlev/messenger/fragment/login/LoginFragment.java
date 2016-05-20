package ru.technotrack.divlev.messenger.fragment.login;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import ru.technotrack.divlev.messenger.R;
import ru.technotrack.divlev.messenger.fragment.BaseFragment;

public class LoginFragment extends BaseFragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View result = inflater.inflate(R.layout.login_fragment, container, false);
        return result;
    }
}
