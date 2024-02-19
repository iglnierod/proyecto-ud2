package model.book.dao;

import model.book.Book;

import java.sql.Connection;
import java.util.ArrayList;

public class BookDAOSQLite {
    Connection connection;

    public BookDAOSQLite(Connection connection) {
        this.connection = connection;
    }
    public ArrayList<Book> getAll() {
        return null;
    }

    public Book getById(int id) {
        return null;
    }

    public void create(Book book) {

    }

    public void update(Book book) {

    }

    public void delete(Book book) {

    }
}
