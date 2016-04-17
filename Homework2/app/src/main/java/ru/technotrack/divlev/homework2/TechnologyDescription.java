package ru.technotrack.divlev.homework2;

import android.content.Context;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public class TechnologyDescription {
    private int id;
    private String title;
    private String pictureUrl;
    private String info;

    static public List<TechnologyDescription> parseFromJson(Context context, String jsonString) throws JSONException{
        if (jsonString == null) {
            throw new IllegalArgumentException(context.getString(R.string.json_exception_msg));
        }

        List<TechnologyDescription> data = new LinkedList<>();
        JSONObject json = new JSONObject(jsonString).getJSONObject("technology");
        Iterator<String> iter = json.keys();

        while (iter.hasNext()) {
            String key = iter.next();
            JSONObject technologyJson = json.getJSONObject(key);
            TechnologyDescription technology = new TechnologyDescription();

            technology.setId(technologyJson.getInt("id"));
            technology.setTitle(technologyJson.getString("title"));
            technology.setPictureUrl(technologyJson.getString("picture"));
            if (technologyJson.has("info")) {
                technology.setInfo(technologyJson.getString("info"));
            }

            data.add(technology);
        }

        return data;
    }

    public TechnologyDescription() {}

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

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
