package model.book.dao;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import model.book.Book;
import model.book.Books;

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
    public void createWithID(Book book) {
        String query = "INSERT INTO books(id, title, author) VALUES(?,?,?)";
        try (PreparedStatement ps = this.connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            ps.setInt(1, book.getId());
            ps.setString(2, book.getTitle());
            ps.setString(3, book.getAuthor());

            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
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

    @Override
    public Books importData(ArrayList<Book> booksList) {
        Books books = new Books();
        for (Book b : booksList) {
            createWithID(b);
            books.add(b);
        }
        return books;
    }

    @Override
    public void emptyTable() {
        String query = "DELETE FROM books";
        try (Statement stmt = connection.createStatement()) {
            stmt.executeUpdate(query);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
