package controller;

import utils.ANSI;
import view.MainView;


public class Controller {
    private static final Controller MAIN_CONTROLLER = new Controller();
    private Controller() {}
    public static Controller getInstance(){
        return MAIN_CONTROLLER;
    }

    public void start() {
        ANSI.printBlue("Controller.start()");
        new MainView(this).setVisible(true);
    }
}
