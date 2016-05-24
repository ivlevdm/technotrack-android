package ru.technotrack.divlev.messenger.util;


import android.util.Log;

import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;

public class JSONUtil {
    private static String TAG = JSONUtil.class.getSimpleName().toUpperCase();
    private static JsonParser parser = new JsonParser();

    private JSONUtil() {}

    public static boolean isJSONValid(String json) {
        try {
            parser.parse(json);
        } catch (JsonSyntaxException e) {
            Log.e(TAG, e.getMessage());
            return false;
        }
        return true;
    }

}
