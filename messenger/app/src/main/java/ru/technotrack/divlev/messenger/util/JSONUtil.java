package ru.technotrack.divlev.messenger.util;


import android.util.Log;

import com.google.gson.JsonObject;
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

    public static String prepareLoginJson(String login, String pass_md5) {
        JsonObject action = new JsonObject();
        JsonObject data = new JsonObject();

        action.addProperty("action", "auth");
        data.addProperty("login", login);
        data.addProperty("pass", pass_md5);
        action.add("data", data);

        return action.toString();
    }

}
