package ru.technotrack.divlev.homework2.fragment;


import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import ru.technotrack.divlev.homework2.R;
import ru.technotrack.divlev.homework2.activity.TechListActivity;
import ru.technotrack.divlev.homework2.adapter.TechViewPagerAdapter;


public class ViewPageFragment extends BaseFragment {
    private ViewPager mPager;
    private TechViewPagerAdapter mPagerAdapter;
    private int currPos;

    public static ViewPageFragment create(int currPos) {
        ViewPageFragment fragment = new ViewPageFragment();
        fragment.currPos = currPos;
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(
                R.layout.fragment_screen_slide_page, container, false);

        return rootView;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mPager = (ViewPager) findViewById(R.id.pager);
        mPagerAdapter = new TechViewPagerAdapter(((TechListActivity)getActivity()).getSupportFragmentManager());
        mPager.setAdapter(mPagerAdapter);
    }
}
