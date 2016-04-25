package ru.technotrack.divlev.homework2;

import android.app.Fragment;
import android.view.View;


public abstract class BaseFragment<ParentActivity extends BaseFragmentInterface> extends Fragment {

    protected String TAG = getClass().getSimpleName().toUpperCase();

    public void applyFragment(BaseFragment<ParentActivity> baseFragment) {
        ParentActivity activity = (ParentActivity) getActivity();
        if (activity == null) {
            return;
        }
        activity.applyFragment(baseFragment);
    }

    protected void finish() {
        ParentActivity activity = (ParentActivity) getActivity();
        if (activity == null) {
            return;
        }
        activity.finishFragment();
    }

    protected View findViewById(int id) {
        ParentActivity activity = (ParentActivity) getActivity();
        if (activity == null) {
            return null;
        }
        return activity.findViewById(id);
    }

    @Override
    public void onResume() {
        super.onResume();
    }
}