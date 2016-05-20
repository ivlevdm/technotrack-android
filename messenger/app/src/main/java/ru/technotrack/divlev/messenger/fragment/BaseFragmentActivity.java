package ru.technotrack.divlev.messenger.fragment;

import android.view.View;

public interface BaseFragmentActivity {
    void finishFragment();

    void applyFragment(BaseFragment baseFragment);

    View findViewById(int id);
}
