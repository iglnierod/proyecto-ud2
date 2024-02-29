package utils;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class JSON {
    public static void write(File file, JsonObject... jsonObjects) {
        Gson gson = new Gson();
        JsonArray main = new JsonArray();
        for (JsonObject object : jsonObjects) {
            main.add(object);
        }

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
            writer.write(gson.toJson(main));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
