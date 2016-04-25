package ru.technotrack.divlev.homework2.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.List;

import ru.technotrack.divlev.homework2.utils.TechnologyDescription;


public class TechViewPagerAdapter extends FragmentStatePagerAdapter {
    private static String TAG = TechViewPagerAdapter.class.getSimpleName();

    private List<TechnologyDescription> items = TechnologyDescription.getData();

    public TechViewPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        return null;
    }

    @Override
    public int getCount() {
        return items.size();
    }
}
