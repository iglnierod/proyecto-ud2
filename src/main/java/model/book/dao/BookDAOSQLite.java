package model.book.dao;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import model.book.Book;

import java.sql.Connection;
import java.util.ArrayList;

public class BookDAOSQLite implements BookDAO {
    Connection connection;

    public BookDAOSQLite(Connection connection) {
        this.connection = connection;
    }

    public ArrayList<Book> getAll() {
        return null;
    }

    @Override
    public ArrayList<Book> getAvailable() {
        return null;
    }

    @Override
    public ArrayList<Book> getNotAvailable() {
        return null;
    }

    public Book getById(int id) {
        return null;
    }

    public boolean create(Book book) {
        return false;
    }

    public void update(Book book) {

    }

    public void delete(Book book) {

    }

    @Override
    public JsonArray export() {
        return null;
    }
}
