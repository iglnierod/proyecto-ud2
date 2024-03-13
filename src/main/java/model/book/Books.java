package model.book;

import utils.ANSI;

import javax.swing.table.DefaultTableModel;
import java.util.ArrayList;
import java.util.HashMap;

public class Books {
    private HashMap<Integer, Book> books;

    public Books() {
        books = new HashMap<>();
    }

    public void load(ArrayList<Book> booksList) {
        for (Book b : booksList) {
            books.put(b.getId(), b);
        }
    }

    public Books(HashMap<Integer, Book> books) {
        this.books = books;
    }

    // METHODS
    public void add(Book book) {
        this.books.put(book.getId(), book);
        ANSI.printPurpleBg(book.toString());
    }

    public void add(String bookTitle, String bookAuthor) {
    }

    public void remove(int bookID) {
        this.books.remove(bookID);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (Book b : books.values()) {
            sb.append(b).append("\n");
        }
        return sb.toString();
    }

    public DefaultTableModel getAllBooksTableModel() {
        DefaultTableModel model = new DefaultTableModel();
        model.addColumn("ID");
        model.addColumn("Título");
        model.addColumn("Autor/a");

        for (Book b : books.values()) {
            String[] bookArray = {String.valueOf(b.getId()), b.getTitle(), b.getAuthor()};
            model.addRow(bookArray);
        }

        return model;
    }

    public DefaultTableModel getAvailableBooksTableModel(ArrayList<Book> bookList) {
        DefaultTableModel model = new DefaultTableModel();
        model.addColumn("ID");
        model.addColumn("Título");
        model.addColumn("Autor/a");
        if(bookList.isEmpty()){
            return model;
        }

        for (Book b : bookList) {
            String[] bookArray = {String.valueOf(b.getId()), b.getTitle(), b.getAuthor()};
            model.addRow(bookArray);
        }

        return model;
    }

    public DefaultTableModel getNotAvailableBooksTableModel(ArrayList<Book> bookList) {
        DefaultTableModel model = new DefaultTableModel();
        model.addColumn("ID");
        model.addColumn("Título");
        model.addColumn("Autor/a");

        if(bookList.isEmpty()){
            return model;
        }

        for (Book b : bookList) {
            String[] bookArray = {String.valueOf(b.getId()), b.getTitle(), b.getAuthor()};
            model.addRow(bookArray);
        }

        return model;
    }

    public Book get(int id) {
        return this.books.get(id);
    }
}
