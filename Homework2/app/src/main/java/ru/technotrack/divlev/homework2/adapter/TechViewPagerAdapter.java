package ru.technotrack.divlev.homework2.adapter;

import android.graphics.Bitmap;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.List;

import ru.technotrack.divlev.homework2.fragment.ScreenSlidePageFragment;
import ru.technotrack.divlev.homework2.utils.DataStorage;
import ru.technotrack.divlev.homework2.utils.TechnologyDescription;


public class TechViewPagerAdapter extends FragmentStatePagerAdapter {
    private static String TAG = TechViewPagerAdapter.class.getSimpleName();
    private DataStorage<String, Bitmap> dataStorage;

    private List<TechnologyDescription> items = TechnologyDescription.getData();

    public TechViewPagerAdapter(FragmentManager fm, DataStorage<String, Bitmap> dataStorage) {
        super(fm);
        this.dataStorage = dataStorage;
    }

    @Override
    public Fragment getItem(int position) {
        return ScreenSlidePageFragment.create(items.get(position), dataStorage);
    }

    @Override
    public int getCount() {
        return items.size();
    }
}
