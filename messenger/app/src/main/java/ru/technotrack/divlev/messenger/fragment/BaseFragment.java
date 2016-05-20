package ru.technotrack.divlev.messenger.fragment;


import android.support.v4.app.Fragment;
import android.view.View;

public abstract class BaseFragment<ParentActivity extends BaseFragmentActivity> extends Fragment {

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