package ru.technotrack.divlev.homework1;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

class ListAdapter extends BaseAdapter {
    private final Context context;


    public ListAdapter(Context context) {
        this.context = context;
    }


    static class ViewHolder {
        public ImageView imageView;
        public TextView textView;
    }

    @Override
    public int getCount() {
        return 1000000;
    }

    @Override
    public Object getItem(int position) {
        return NumberToRusString.convert(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;

        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater)
                    context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.listrow, null, true);
            holder = new ViewHolder();
            holder.textView = (TextView) convertView.findViewById(R.id.Itemtext);
            holder.imageView = (ImageView) convertView.findViewById(R.id.icon);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.textView.setText(NumberToRusString.convert(position + 1));
        if (position % 2 == 0) {
            convertView.setBackgroundColor(Color.WHITE);
        } else {
            convertView.setBackgroundColor(Color.GRAY);
        }
        return convertView;
    }

}