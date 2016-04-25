package ru.technotrack.divlev.homework2;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

public class TechListFragment extends BaseFragment {
    private ListView listView;
    private PictureDownloader<TechnologyAdapter.ViewHolder> pictureDownloaderThread;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View result = inflater.inflate(R.layout.tech_list_fragment, container, false);
        listView = (ListView) result.findViewById(R.id.tech_list_view);

        pictureDownloaderThread = new PictureDownloader<>(new Handler());
        pictureDownloaderThread.start();
        pictureDownloaderThread.setDataStorage(new LruCacheBitmapStorage());
        pictureDownloaderThread.getLooper();

        Log.i(TAG, "pictureDownloaderThread started");

        return result;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        listView.setAdapter(new TechnologyAdapter(getActivity(), pictureDownloaderThread));
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        pictureDownloaderThread.quit();
        Log.i(TAG, "pictureDownloaderThread stopped");
        pictureDownloaderThread.clearQueue();
    }
}
