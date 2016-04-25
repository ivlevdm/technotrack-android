package ru.technotrack.divlev.homework2.fragment;

import android.view.View;

public interface BaseFragmentInterface {
    void finishFragment();

    void applyFragment(BaseFragment baseFragment);

    View findViewById(int id);
}
