package ru.technotrack.divlev.homework2.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import ru.technotrack.divlev.homework2.R;
import ru.technotrack.divlev.homework2.utils.DataStorage;
import ru.technotrack.divlev.homework2.utils.PictureDownloader;
import ru.technotrack.divlev.homework2.utils.TechnologyDescription;

public class TechListAdapter extends BaseAdapter {
    private static String TAG = TechListAdapter.class.getSimpleName();

    private List<TechnologyDescription> items = TechnologyDescription.getData();
    private PictureDownloader<ViewHolder> pictureDownloaderThread;
    private DataStorage<String, Bitmap> dataStorage = null;

    private Context context;

    public TechListAdapter(Context context, PictureDownloader<ViewHolder> downloader) {
        this.context = context;
        this.pictureDownloaderThread = downloader;

        this.dataStorage = pictureDownloaderThread.getDataStorage();

        pictureDownloaderThread.setListener(new PictureDownloader.Listener<ViewHolder>() {
            @Override
            public void onPictureDownloaded(ViewHolder holder, Bitmap picture) {
                setImageToHolder(holder, picture);
            }
        });
    }

    @Override
    public int getCount() {
        return items.size();
    }

    @Override
    public Object getItem(int position) {
        return items.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        View view = convertView;

        if (view == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.tech_list_item, null, true);
            holder = new ViewHolder();
            holder.img = (ImageView) view.findViewById(R.id.img_list_item);
            holder.title = (TextView) view.findViewById(R.id.title_list_item);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }

        TechnologyDescription technology = items.get(position);
        String pictureUrl = technology.getPictureUrl();
        holder.title.setText(technology.getTitle());

        if (dataStorage != null && dataStorage.contains(pictureUrl)) {
            setImageToHolder(holder, dataStorage.get(pictureUrl));
        } else {
            setImageToHolder(holder, null);
            pictureDownloaderThread.queuePicture(holder, pictureUrl);
        }
        return view;
    }


    private void setImageToHolder(ViewHolder holder, Bitmap picture) {
        if (picture != null) {
            holder.img.setImageBitmap(picture);
            Log.i(TAG, "Image for holder.coverSmall was changed");
        } else {
            holder.img.setImageResource(R.drawable.wtf);
            Log.e(TAG, "Image for holder.coverSmall was changed by FailImage");
        }
    }

    public static class ViewHolder {
        public ImageView img;
        public TextView title;
    }
}
