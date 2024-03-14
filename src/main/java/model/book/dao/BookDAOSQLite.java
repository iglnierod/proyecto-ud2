package model.book.dao;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import model.book.Book;
import model.book.Books;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class BookDAOSQLite implements BookDAO {
    Connection connection;

    public BookDAOSQLite(Connection connection) {
        this.connection = connection;
    }

    public ArrayList<Book> getAll() {
        String query = "SELECT * FROM books";
        ArrayList<Book> booksList = new ArrayList<>();
        try (Statement stmt = connection.createStatement()) {
            ResultSet rs = stmt.executeQuery(query);
            while (rs.next()) {
                Book b = new Book();
                b.setId(rs.getInt("id"));
                b.setTitle(rs.getString("title"));
                b.setAuthor(rs.getString("author"));

                booksList.add(b);
            }
            return booksList;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public ArrayList<Book> getAvailable() {
        String query = "SELECT * FROM books WHERE id NOT IN (" +
                "SELECT id_book FROM rents WHERE ending IS NULL ORDER BY beginning DESC);";
        ArrayList<Book> booksList = new ArrayList<>();
        try (Statement stmt = connection.createStatement()) {
            ResultSet rs = stmt.executeQuery(query);
            while (rs.next()) {
                Book b = new Book();
                b.setId(rs.getInt("id"));
                b.setTitle(rs.getString("title"));
                b.setAuthor(rs.getString("author"));

                booksList.add(b);
            }
            return booksList;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public ArrayList<Book> getNotAvailable() {
        String query = "SELECT * FROM books WHERE id IN (SELECT id_book FROM rents WHERE ending IS NULL)";
        ArrayList<Book> booksList = new ArrayList<>();
        try (Statement stmt = connection.createStatement()) {
            ResultSet rs = stmt.executeQuery(query);
            while (rs.next()) {
                Book b = new Book();
                b.setId(rs.getInt("id"));
                b.setTitle(rs.getString("title"));
                b.setAuthor(rs.getString("author"));

                booksList.add(b);
            }
            return booksList;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public Book getById(int id) {
        return null;
    }

    public boolean create(Book book) {
        return false;
    }

    @Override
    public void createWithID(Book book) {

    }

    public void update(Book book) {

    }

    public void delete(Book book) {

    }

    @Override
    public JsonArray export() {
        return null;
    }

    @Override
    public Books importData(ArrayList<Book> books) {
        return null;
    }

    @Override
    public void emptyTable() {

    }

}
