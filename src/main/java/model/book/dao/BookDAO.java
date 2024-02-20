package model.book.dao;

import model.book.Book;

import java.util.ArrayList;

public interface BookDAO {
    public ArrayList<Book> getAll();

    public Book getById(int id);

    public boolean create(Book book);

    public void update(Book book);

    public void delete(Book book);
}
