package ru.technotrack.divlev.homework2.fragment;


import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import ru.technotrack.divlev.homework2.R;
import ru.technotrack.divlev.homework2.activity.TechListActivity;
import ru.technotrack.divlev.homework2.adapter.TechViewPagerAdapter;
import ru.technotrack.divlev.homework2.utils.DataStorage;
import ru.technotrack.divlev.homework2.utils.PictureDownloader;


public class ViewPageFragment extends BaseFragment {
    private ViewPager mPager;
    private TechViewPagerAdapter mPagerAdapter;
    private int currPos;
    private DataStorage<String, Bitmap> dataStorage;
    private PictureDownloader<ImageView> pictureDownloaderThread;

    public static ViewPageFragment create(int currPos, DataStorage<String, Bitmap> dataStorage) {
        ViewPageFragment fragment = new ViewPageFragment();
        fragment.currPos = currPos;
        fragment.dataStorage = dataStorage;
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

        pictureDownloaderThread = new PictureDownloader<>(new Handler());
        pictureDownloaderThread.setDataStorage(dataStorage);

        mPager = (ViewPager) findViewById(R.id.pager);
        mPagerAdapter = new TechViewPagerAdapter(((TechListActivity)getActivity()).getSupportFragmentManager(),
                pictureDownloaderThread);
        mPager.setAdapter(mPagerAdapter);
        mPager.setCurrentItem(currPos);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        pictureDownloaderThread.quit();
        Log.i(TAG, "pictureDownloaderThread stopped");
        pictureDownloaderThread.clearQueue();
    }
}
