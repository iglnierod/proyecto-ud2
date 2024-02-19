package controller;

import model.database.Database;
import utils.ANSI;
import view.DatabaseConfigView;
import view.MainView;

import java.io.File;


public class Controller {
    private static final Controller MAIN_CONTROLLER = new Controller();
    private static Database database;

    private Controller() {
        database = Database.loadConfigFile();
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
        //System.out.println(database);
    }

    public static void setMySqlConfig(String host, int port, String user, String password, String databaseName) {
        database = new Database(host, port, user, password, databaseName);
        System.out.println(database);
    }
}
