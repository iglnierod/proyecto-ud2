package controller;

import com.google.gson.JsonObject;
import model.book.Book;
import model.book.Books;
import model.book.dao.BookDAO;
import model.book.dao.BookDAOMySQL;
import model.book.dao.BookDAOSQLite;
import model.database.Database;
import model.member.Member;
import model.member.Members;
import model.member.dao.MemberDAO;
import model.member.dao.MemberDAOMySQL;
import model.member.dao.MemberDAOSQLite;
import model.rent.Rent;
import model.rent.Rents;
import model.rent.dao.RentsDAO;
import model.rent.dao.RentsDAOMySQL;
import model.rent.dao.RentsDAOSQLite;
import utils.ANSI;
import utils.JSON;
import view.DatabaseConfigView;
import view.MainView;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.io.File;
import java.sql.Connection;
import java.sql.Timestamp;
import java.util.UUID;


public class Controller {
    private static final Controller MAIN_CONTROLLER = new Controller();
    private static Database database;
    private static Books books;
    private static BookDAO bookDAO;
    private static Members members;
    private static MemberDAO memberDAO;
    private static Rents rents;
    private static RentsDAO rentsDAO;
    //
    private static int selectedBookID;
    private static String selectedMemberID;
    private static MainView mainView;

    private Controller() {
        database = Database.loadConfigFile();
        books = new Books();
        members = new Members();
        rents = new Rents();
        mainView = new MainView(this);
    }

    public static Controller getInstance() {
        return MAIN_CONTROLLER;
    }

    public void start() {
        ANSI.printBlue("Controller.start()");

        if (!database.isConfigLoaded()) {
            new DatabaseConfigView(null, this).setVisible(true);
        } else {
            initiate();
        }
        System.out.println(database);
    }

    private static void initiate() {
        mainView.setVisible(true);
        Connection connection = database.getConnection();
        switch (database.getEngine()) {
            case mysql -> {
                bookDAO = new BookDAOMySQL(connection);
                memberDAO = new MemberDAOMySQL(connection);
                rentsDAO = new RentsDAOMySQL(connection);
            }
            case sqlite -> {
                bookDAO = new BookDAOSQLite(connection);
                memberDAO = new MemberDAOSQLite(connection);
                rentsDAO = new RentsDAOSQLite(connection);
            }
        }
        books.load(bookDAO.getAll());
        members.load(memberDAO.getAll());
        rents.load(rentsDAO.getAll());
    }


    public static boolean setMySqlConfig(String host, int port, String user, String password, String databaseName) {
        database = new Database(host, port, user, password, databaseName);
        if (database.check()) {
            initiate();
            return true;
        }
        return false;
    }

    public static boolean setSQLiteConfig(File file) {
        database = new Database(file);
        if (database.check()) {
            initiate();
            return true;
        }
        return false;
    }

    // Use case: Add book
    public static boolean addBook(String bookTitle, String bookAuthor) {
        Book book = new Book(bookTitle, bookAuthor);
        if (bookDAO.create(book)) {
            books.add(book);
            return true;
        }
        return false;
    }

    // User case: Add member
    public static boolean addMember(String id, String name, String email) {
        Member member = new Member(id, name, email);
        if (memberDAO.create(member)) {
            members.add(member);
            return true;
        }
        return false;
    }

    // Use case: view available books
    public static DefaultTableModel getAvailableBooksTableModel() {
        return books.getAvailableBooksTableModel(bookDAO.getAvailable());
    }

    public static DefaultTableModel getAllBooksTableModel() {
        return books.getAllBooksTableModel();
    }

    // Use case: view members
    public static DefaultTableModel getMembersTableModel() {
        return members.getMembersTableModel(memberDAO.getAll());
    }

    // Use case: rent bookñ
    public static void setSelectedBookID(int selectedBookID) {
        Controller.selectedBookID = selectedBookID;
    }

    public static void setSelectedMemberID(String selectedMemberID) {
        Controller.selectedMemberID = selectedMemberID;
    }

    public static void rentBook() {
        Timestamp now = new Timestamp(System.currentTimeMillis());
        System.out.println("beginning: " + now);
        Rent rent = new Rent(selectedBookID, selectedMemberID, now, null);
        if (rentsDAO.create(rent)) {
            rents.add(rent);
        }
    }

    public static boolean isBooksEmpty() {
        return bookDAO.getAvailable().isEmpty();
    }

    public static boolean isMembersEmpty() {
        return members.isEmpty();
    }

    // Use case: end rent
    public static boolean isRentsEmpty() {
        return rents.isEmpty();
    }

    public static boolean endRent(UUID uuid) {
        System.out.println(rents.get(uuid));
        if (rents.exists(uuid) && rents.get(uuid).getEndingDate() == null) {
            Timestamp now = new Timestamp(System.currentTimeMillis());
            System.out.println("engind: " + now);
            if (rentsDAO.end(uuid, now)) {
                rents.get(uuid).setEndingDate(now.toString());
                System.out.println(rents.get(uuid));
                return true;
            }
        }
        return false;
    }


    // Use case: view not available books
    public static DefaultTableModel getNotAvailableBooksTableModel() {
        return books.getNotAvailableBooksTableModel(bookDAO.getNotAvailable());
    }

    // Use case: view rented history
    public static DefaultTableModel getRentedHistoryTableModel() {
        return rents.getRentedHistory(rentsDAO.getHistory());
    }

    // Use case: export db to json
    public static void export(File selectedFile) {
        JsonObject main = new JsonObject();
        main.add("books", bookDAO.export());
        main.add("members", memberDAO.export());
        main.add("rents", rentsDAO.export());
        JSON.write(selectedFile, main);
    }

    // Use case: import db from json
    public static void importData(File selectedFile) {
        emptyAllTables();
        books = bookDAO.importData(JSON.getBooks(selectedFile));
        members = memberDAO.importData(JSON.getMembers(selectedFile));
        rents = rentsDAO.importData(JSON.getRents(selectedFile));
    }

    private static void emptyAllTables() {
        rentsDAO.emptyTable();
        bookDAO.emptyTable();
        memberDAO.emptyTable();
    }
}
