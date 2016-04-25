package ru.technotrack.divlev.homework2.utils;

import android.content.Context;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import ru.technotrack.divlev.homework2.R;

public class TechnologyDescription {
    static private List<TechnologyDescription> data = null;

    private String title;
    private String pictureUrl;
    private String info;

    static public List<TechnologyDescription> parseFromJson(Context context, String jsonString) throws JSONException{
        if (jsonString == null) {
            throw new IllegalArgumentException(context.getString(R.string.json_exception_msg));
        }

        List<TechnologyDescription> data = new ArrayList<>();
        JSONObject json = new JSONObject(jsonString).getJSONObject("technology");
        Iterator<String> iter = json.keys();

        while (iter.hasNext()) {
            String key = iter.next();
            JSONObject technologyJson = json.getJSONObject(key);
            TechnologyDescription technology = new TechnologyDescription();

            technology.setTitle(technologyJson.getString("title"));
            technology.setPictureUrl(context.getString(R.string.url_base) + technologyJson.getString("picture"));
            if (technologyJson.has("info")) {
                technology.setInfo(technologyJson.getString("info"));
            }

            data.add(technology);
        }

        return data;
    }

    static public void initData(List<TechnologyDescription> data) {
        TechnologyDescription.data = data;
    }

    static public List<TechnologyDescription> getData() {
        return data;
    }

    public TechnologyDescription() {}

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPictureUrl() {
        return pictureUrl;
    }

    public void setPictureUrl(String pictureUrl) {
        this.pictureUrl = pictureUrl;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }
}
