package ru.technotrack.divlev.homework2.fragment;


import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.ref.WeakReference;
import java.net.URL;

import ru.technotrack.divlev.homework2.R;
import ru.technotrack.divlev.homework2.utils.DataStorage;
import ru.technotrack.divlev.homework2.utils.HttpDownloader;
import ru.technotrack.divlev.homework2.utils.TechnologyDescription;

public class ScreenSlidePageFragment extends BaseFragment {
    private static String TAG = ScreenSlidePageFragment.class.getSimpleName();

    private TechnologyDescription desc;
    private DataStorage<String, Bitmap> dataStorage;

    public static ScreenSlidePageFragment create(TechnologyDescription desc, DataStorage<String, Bitmap> dataStorage) {
        ScreenSlidePageFragment fragment = new ScreenSlidePageFragment();
        fragment.desc = desc;
        fragment.dataStorage = dataStorage;

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(
                R.layout.pager_item, container, false);

        TextView tv = (TextView)rootView.findViewById(R.id.dict_word);
        if (desc.getInfo() != null) {
            tv.setText(desc.getInfo());
        }

        ImageView iv = (ImageView)rootView.findViewById(R.id.dict_image);
        String pictureUrl = desc.getPictureUrl();

        if (dataStorage != null && dataStorage.contains(pictureUrl)) {
            iv.setImageBitmap(dataStorage.get(pictureUrl));
        } else {
            LoadImageTask lt = new LoadImageTask(iv, pictureUrl);
            lt.execute();
            iv.setImageResource(R.drawable.wtf);
        }

        return rootView;
    }

    private class LoadImageTask extends AsyncTask<Void, Void, Bitmap> {
        private final WeakReference<ImageView> weakIv;
        private final String name;

        public LoadImageTask(ImageView iv, String name) {
            super();
            this.weakIv = new WeakReference<>(iv);
            this.name = name;
        }

        @Override
        protected Bitmap doInBackground(Void... params) {
            try {
                byte[] bitmapBytes = HttpDownloader.getUrlBytes(name);
                Bitmap bitmap = BitmapFactory.decodeByteArray(bitmapBytes, 0, bitmapBytes.length);
                return bitmap;

            } catch (IOException e) {
                Log.e("LoadImageTask", "LoadImageTask.LoadBitmap IOException " + e.getMessage(), e);
            }
            return null;
        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            if (isCancelled())
                bitmap = null;

            ImageView iv = weakIv.get();
            if (iv != null) {
                iv.setImageBitmap(bitmap);
            }
        }
    }
}
