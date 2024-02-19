package model.book.dao;

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
        return null;
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
}
