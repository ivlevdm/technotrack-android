package ru.technotrack.divlev.homework2.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.widget.ImageView;

import java.util.List;

import ru.technotrack.divlev.homework2.fragment.ScreenSlidePageFragment;
import ru.technotrack.divlev.homework2.utils.PictureDownloader;
import ru.technotrack.divlev.homework2.utils.TechnologyDescription;


public class TechViewPagerAdapter extends FragmentStatePagerAdapter {
    private static String TAG = TechViewPagerAdapter.class.getSimpleName();
    private PictureDownloader<ImageView> pictureDownloader;

    private List<TechnologyDescription> items = TechnologyDescription.getData();

    public TechViewPagerAdapter(FragmentManager fm, PictureDownloader<ImageView> pictureDownloader) {
        super(fm);
        this.pictureDownloader = pictureDownloader;
    }

    @Override
    public Fragment getItem(int position) {
        return ScreenSlidePageFragment.create(items.get(position), pictureDownloader);
    }

    @Override
    public int getCount() {
        return items.size();
    }
}
