package ru.technotrack.divlev.messenger.util;


import android.util.Log;
import android.util.Pair;

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

    public static String prepareRegisterJson(String login, String pass_md5, String nickname) {
        JsonObject action = new JsonObject();
        JsonObject data = new JsonObject();

        action.addProperty("action", "register");
        data.addProperty("login", login);
        data.addProperty("pass", pass_md5);
        data.addProperty("nick", nickname);
        action.add("data", data);

        return action.toString();
    }

    public static String prepareChannelListJson(String cid, String sid) {
        JsonObject action = new JsonObject();
        JsonObject data = new JsonObject();

        action.addProperty("action", "channellist");
        data.addProperty("cid", cid);
        data.addProperty("sid", sid);
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

    public static int getDataStatus(JsonObject data) {
        return data.get("status").getAsInt();
    }
}
