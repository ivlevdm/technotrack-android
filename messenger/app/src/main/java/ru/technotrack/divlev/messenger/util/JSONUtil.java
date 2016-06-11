package ru.technotrack.divlev.messenger.util;


import android.util.Log;
import android.util.Pair;

import com.google.gson.JsonElement;
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

    public static JsonObject getLoginFailJson() {
        JsonObject data = new JsonObject();

        data.addProperty("status", 1);
        data.addProperty("error", "");
        data.addProperty("sid", "");
        data.addProperty("cid", "");

        return data;
    }

    public static Pair<String, JsonObject> parseMessage(String answer) {
        JsonObject json = parser.parse(answer).getAsJsonObject();
        String action = json.get("action").getAsString();
        JsonObject data;

        if (action.equals("welcome")) {
            data = new JsonObject();
            data.addProperty("time", json.get("time").getAsInt());
        } else {
            data = json.get("data").getAsJsonObject();
        }
        return new Pair<>(action, data);
    }
}
