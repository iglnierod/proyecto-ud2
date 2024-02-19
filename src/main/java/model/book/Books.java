package model.book;

import utils.ANSI;
import utils.Util;

import java.util.HashMap;

public class Books {
    private HashMap<Integer, Book> books;

    public Books() {
        books = new HashMap<>();
    }

    public Books(HashMap<Integer, Book> books) {
        this.books = books;
    }

    // METHODS
    public void add(Book book) {
        this.books.put(book.getId(), book);
        ANSI.printPurpleBg(book.toString());
    }

    public void add(String bookTitle, String bookAuthor) {}

    public void remove(int bookID){
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
}
