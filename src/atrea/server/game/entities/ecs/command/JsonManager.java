package atrea.server.game.entities.ecs.command;

import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;

import java.io.StringReader;

public class JsonManager {
    private static Gson gson = new Gson();

    public static JsonReader getGson(String json) {
        return gson.newJsonReader(new StringReader(json));
    }
}
