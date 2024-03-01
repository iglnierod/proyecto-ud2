package utils;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class JSON {
    public static void write(File file, JsonObject main) {
        Gson gson = new Gson();
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
            writer.write(gson.toJson(main));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
