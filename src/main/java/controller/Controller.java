package controller;

import com.google.gson.JsonObject;
import model.book.Book;
import model.book.Books;
import model.book.dao.BookDAO;
import model.book.dao.BookDAOMySQL;
import model.database.Database;
import model.member.Member;
import model.member.Members;
import model.member.dao.MemberDAO;
import model.member.dao.MemberDAOMySQL;
import model.rent.Rent;
import model.rent.Rents;
import model.rent.dao.RentsDAO;
import model.rent.dao.RentsDAOMySQL;
import utils.ANSI;
import utils.JSON;
import view.DatabaseConfigView;
import view.MainView;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.io.File;
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
        bookDAO = new BookDAOMySQL(database.getConnection());
        memberDAO = new MemberDAOMySQL(database.getConnection());
        rentsDAO = new RentsDAOMySQL(database.getConnection());
        books.load(bookDAO.getAll());
        members.load(memberDAO.getAll());
        rents.load(rentsDAO.getAll());
    }


    public static boolean setMySqlConfig(String host, int port, String user, String password, String databaseName) {
        database = new Database(host, port, user, password, databaseName);
        if (database.isConnectionValid()) {
            if (database.isCreated()) {
                initiate();
                return true;
            } else {
                if (database.createDatabase()) {
                    initiate();
                    return true;
                } else {
                    JOptionPane.showMessageDialog(null, "La conexión es válida pero no existe la base de datos", "ERROR", JOptionPane.ERROR_MESSAGE);
                    return false;
                }
            }
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
        return members.getAvailableBooksTableModel();
    }

    // Use case: rent book
    public static void setSelectedBookID(int selectedBookID) {
        Controller.selectedBookID = selectedBookID;
    }

    public static void setSelectedMemberID(String selectedMemberID) {
        Controller.selectedMemberID = selectedMemberID;
    }

    public static void rentBook() {
        Timestamp now = new Timestamp(System.currentTimeMillis());
        Rent rent = new Rent(selectedBookID, selectedMemberID, now, null);
        if (rentsDAO.create(rent)) {
            rents.add(rent);
        }
    }

    // Use case: end rent
    public static boolean endRent(UUID uuid) {
        System.out.println(rents.get(uuid));
        if (rents.exists(uuid) && rents.get(uuid).getEndingDate() == null) {
            Timestamp now = new Timestamp(System.currentTimeMillis());
            if (rentsDAO.end(uuid, now)) {
                rents.get(uuid).setEndingDate(now.toString());
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
        main.add("books",bookDAO.export());
        main.add("members",memberDAO.export());
        main.add("rents",rentsDAO.export());
        JSON.write(selectedFile, main);
    }
}
