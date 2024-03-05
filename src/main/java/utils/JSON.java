package utils;

import com.google.gson.*;
import com.google.gson.reflect.TypeToken;
import model.book.Book;
import model.member.Member;
import model.rent.Rent;

import java.io.*;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;

public class JSON {
    public static void write(File file, JsonObject main) {
        Gson gson = new Gson();
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
            writer.write(gson.toJson(main));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static HashMap<String, ArrayList<Object>> read(File jsonFile) {
        HashMap<String, ArrayList<Object>> jsonData = new HashMap<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(jsonFile))) {
            Gson gson = new Gson();
            Type typeBooks = new TypeToken<ArrayList<Book>>() {
            }.getType();
            Type typeMembers = new TypeToken<ArrayList<Member>>() {
            }.getType();
            Type typeRents = new TypeToken<ArrayList<Rent>>() {
            }.getType();

            HashMap<String, ArrayList<Object>> data = gson.fromJson(reader, HashMap.class);
            ArrayList<Book> books = gson.fromJson(gson.toJson(data.get("books")), typeBooks);
            ArrayList<Member> members = gson.fromJson(gson.toJson(data.get("members")), typeMembers);
            ArrayList<Rent> rents = gson.fromJson(gson.toJson(data.get("rents")), typeRents);

            jsonData.put("books", new ArrayList<>(books));
            jsonData.put("members", new ArrayList<>(members));
            jsonData.put("rents", new ArrayList<>(rents));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return jsonData;
    }

    public static ArrayList<Book> getBooks(File jsonFile) {
        ArrayList<Book> books = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(jsonFile))) {
            StringBuilder jsonContent = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                jsonContent.append(line);
            }

            // Parsear el contenido JSON
            JsonElement jsonElement = JsonParser.parseString(jsonContent.toString());
            JsonObject jsonObject = jsonElement.getAsJsonObject();
            JsonArray booksArray = jsonObject.getAsJsonArray("books");

            // Iterar sobre los libros y crear objetos Book
            for (JsonElement element : booksArray) {
                JsonObject bookObject = element.getAsJsonObject();
                Book book = new Book();
                book.setId(bookObject.get("id").getAsInt());
                book.setTitle(bookObject.get("title").getAsString());
                book.setAuthor(bookObject.get("author").getAsString());
                books.add(book);
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return books;
    }

    public static ArrayList<Member> getMembers(File jsonFile) {
        ArrayList<Member> members = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(jsonFile))) {
            StringBuilder jsonContent = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                jsonContent.append(line);
            }

            // Parsear el contenido JSON
            JsonElement jsonElement = JsonParser.parseString(jsonContent.toString());
            JsonObject jsonObject = jsonElement.getAsJsonObject();
            JsonArray booksArray = jsonObject.getAsJsonArray("books");

            // Iterar sobre los libros y crear objetos Book
            for (JsonElement element : booksArray) {
                JsonObject bookObject = element.getAsJsonObject();
                Member member = new Member();
                member.setId(bookObject.get("id").getAsString());
                member.setName(bookObject.get("name").getAsString());
                member.setEmail(bookObject.get("email").getAsString());
                members.add(member);
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return members;
    }

    public static ArrayList<Book> getRents(File jsonFile) {
        ArrayList<Book> books = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(jsonFile))) {
            StringBuilder jsonContent = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                jsonContent.append(line);
            }

            // Parsear el contenido JSON
            JsonElement jsonElement = JsonParser.parseString(jsonContent.toString());
            JsonObject jsonObject = jsonElement.getAsJsonObject();
            JsonArray booksArray = jsonObject.getAsJsonArray("books");

            // Iterar sobre los libros y crear objetos Book
            for (JsonElement element : booksArray) {
                JsonObject bookObject = element.getAsJsonObject();
                Book book = new Book();
                book.setId(bookObject.get("id").getAsInt());
                book.setTitle(bookObject.get("title").getAsString());
                book.setAuthor(bookObject.get("author").getAsString());
                books.add(book);
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return books;
    }
}
