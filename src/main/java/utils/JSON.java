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
import java.util.UUID;

public class JSON {
    public static void write(File file, JsonObject main) {
        Gson gson = new Gson();
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
            writer.write(gson.toJson(main));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
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
            JsonArray membersArray = jsonObject.getAsJsonArray("members");

            // Iterar sobre los libros y crear objetos Book
            for (JsonElement element : membersArray) {
                JsonObject memberObject = element.getAsJsonObject();
                Member member = new Member();
                member.setId(memberObject.get("id").getAsString());
                member.setName(memberObject.get("name").getAsString());
                member.setEmail(memberObject.get("email").getAsString());
                members.add(member);
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return members;
    }

    public static ArrayList<Rent> getRents(File jsonFile) {
        ArrayList<Rent> rents = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(jsonFile))) {
            StringBuilder jsonContent = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                jsonContent.append(line);
            }

            // Parsear el contenido JSON
            JsonElement jsonElement = JsonParser.parseString(jsonContent.toString());
            JsonObject jsonObject = jsonElement.getAsJsonObject();
            JsonArray rentsArray = jsonObject.getAsJsonArray("rents");

            // Iterar sobre los libros y crear objetos Book
            for (JsonElement element : rentsArray) {
                JsonObject rentObject = element.getAsJsonObject();
                Rent rent = new Rent();
                rent.setUuid(UUID.fromString(rentObject.get("uuid").getAsString()));
                rent.setBookID(rentObject.get("id_book").getAsInt());
                rent.setMemberID(rentObject.get("id_member").getAsString());
                rent.setBeginningDate(rentObject.get("beginning").getAsString());
                rent.setEndingDate(rentObject.get("ending").getAsString());
                rents.add(rent);
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return rents;
    }
}
