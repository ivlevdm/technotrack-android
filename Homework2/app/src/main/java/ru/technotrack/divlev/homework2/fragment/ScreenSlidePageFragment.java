package ru.technotrack.divlev.homework2.fragment;


import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import ru.technotrack.divlev.homework2.R;
import ru.technotrack.divlev.homework2.utils.DataStorage;
import ru.technotrack.divlev.homework2.utils.PictureDownloader;
import ru.technotrack.divlev.homework2.utils.TechnologyDescription;

public class ScreenSlidePageFragment extends BaseFragment {
    private static String TAG = ScreenSlidePageFragment.class.getSimpleName();

    private TechnologyDescription desc;
    private PictureDownloader<ImageView> pictureDownloader;

    public static ScreenSlidePageFragment create(TechnologyDescription desc, PictureDownloader<ImageView> pictureDownloader) {
        ScreenSlidePageFragment fragment = new ScreenSlidePageFragment();
        fragment.desc = desc;
        fragment.pictureDownloader = pictureDownloader;

        fragment.pictureDownloader.setListener(new PictureDownloader.Listener<ImageView>() {
            @Override
            public void onPictureDownloaded(ImageView imageView, Bitmap picture) {
                setImageToImageView(imageView, picture);
            }
        });

        return fragment;
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
        DataStorage<String, Bitmap> dataStorage = pictureDownloader.getDataStorage();

        ImageView iv = (ImageView)rootView.findViewById(R.id.dict_image);
        String pictureUrl = desc.getPictureUrl();

        if (dataStorage != null && dataStorage.contains(pictureUrl)) {
            setImageToImageView(iv, dataStorage.get(pictureUrl));
        } else {
            setImageToImageView(iv, null);
            pictureDownloader.queuePicture(iv, pictureUrl);
        }

        return rootView;
    }

    private static void setImageToImageView(ImageView imageView, Bitmap picture) {
        if (picture != null) {
            imageView.setImageBitmap(picture);
            Log.i(TAG, "ImageView was changed");
        } else {
            imageView.setImageResource(R.drawable.wtf);
            Log.e(TAG, "ImageView was changed by FailImage");
        }
    }

}
