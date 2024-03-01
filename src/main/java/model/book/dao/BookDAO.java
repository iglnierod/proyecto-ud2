package model.book.dao;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import model.book.Book;

import java.util.ArrayList;

public interface BookDAO {
    public ArrayList<Book> getAll();

    public ArrayList<Book> getAvailable();

    public ArrayList<Book> getNotAvailable();

    public Book getById(int id);

    public boolean create(Book book);

    public void update(Book book);

    public void delete(Book book);

    public JsonArray export();
}
