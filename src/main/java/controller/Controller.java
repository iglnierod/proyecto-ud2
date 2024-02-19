package controller;

import model.book.Book;
import model.book.Books;
import model.book.dao.BookDAO;
import model.book.dao.BookDAOMySQL;
import model.database.Database;
import utils.ANSI;
import view.DatabaseConfigView;
import view.MainView;


public class Controller {
    private static final Controller MAIN_CONTROLLER = new Controller();
    private static Database database;
    private static Books books;
    private static BookDAO bookDAO;

    private Controller() {
        database = Database.loadConfigFile();
        books = new Books();
    }

    public static Controller getInstance() {
        return MAIN_CONTROLLER;
    }

    public void start() {
        ANSI.printBlue("Controller.start()");
        MainView mainView = new MainView(this);
        mainView.setVisible(true);
        if (!database.isConfigLoaded()) {
            new DatabaseConfigView(mainView, this).setVisible(true);
        }
        bookDAO = new BookDAOMySQL(database.getConnection());
    }

    public static void setMySqlConfig(String host, int port, String user, String password, String databaseName) {
        database = new Database(host, port, user, password, databaseName);
        System.out.println(database);
    }

    // Use case: Add book
    public static boolean addBook(String bookTitle, String bookAuthor) {
        Book book = new Book(bookTitle, bookAuthor);
        if(bookDAO.create(book)) {
            books.add(book);
            return true;
        }
        return false;
    }
}
