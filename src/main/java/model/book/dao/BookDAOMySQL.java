package model.book.dao;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import model.book.Book;

import java.sql.*;
import java.util.ArrayList;

public class BookDAOMySQL implements BookDAO {
    private Connection connection;

    public BookDAOMySQL(Connection connection) {
        this.connection = connection;
    }

    @Override
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
                "SELECT id_book FROM rents WHERE ending IS NULL ORDER BY beginning DESC);\n";
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

    @Override
    public Book getById(int id) {
        return null;
    }

    @Override
    public boolean create(Book book) {
        String query = "INSERT INTO books(title, author) VALUES(?,?)";
        try (PreparedStatement ps = this.connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, book.getTitle());
            ps.setString(2, book.getAuthor());

            ps.executeUpdate();

            ResultSet keys = ps.getGeneratedKeys();
            while (keys.next()) {
                int id = keys.getInt(1);
                book.setId(id);
            }
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public void update(Book book) {

    }

    @Override
    public void delete(Book book) {

    }

    public JsonArray export() {
        JsonArray booksArray = new JsonArray();
        for (Book b : getAll()) {
            JsonObject bookObject = new JsonObject();
            bookObject.addProperty("id", b.getId());
            bookObject.addProperty("title", b.getTitle());
            bookObject.addProperty("author", b.getAuthor());
            booksArray.add(bookObject);
        }
        return booksArray;
    }
}
