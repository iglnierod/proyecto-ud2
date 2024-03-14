package view;

import controller.Controller;
import model.database.Engine;
import utils.ANSI;
import view.layout.NativeLayout;

import javax.swing.*;
import java.awt.*;
import java.io.File;

public class DatabaseConfigView extends JFrame {
    private JTabbedPane tabsPane;
    private JTextField hostField;
    private JTextField portField;
    private JTextField usernameField;
    private JTextField passwordField;
    private JTextField dbNameField;
    private String sqlitePathFile;
    private JLabel sqliteLabel;
    private JButton btnSqlite;
    private Controller controller;

    public DatabaseConfigView(JFrame parent, Controller controller) {
        super(System.getenv("APP_NAME"));
        ANSI.printCyan("DatabaseConfigView");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setMinimumSize(new Dimension(500, 350));
        setResizable(false);
        NativeLayout.set();
        setLocationRelativeTo(parent);
        this.controller = controller;

        add(getHeader(), BorderLayout.PAGE_START); // Header
        add(getTabs(), BorderLayout.CENTER); // Tabs
        add(getFooter(), BorderLayout.PAGE_END); // Footer

        getCurrentTabStatus();
        this.setVisible(true);
    }

    private void getCurrentTabStatus() {
        Engine engine = Engine.mysql;
        int tabNum = getTabSelectedFromEngine(engine);
        tabsPane.setSelectedIndex(tabNum);
    }

    private JLabel getHeader() {
        JLabel label = new JLabel("Configuración de la base de datos");
        label.setBorder(BorderFactory.createEmptyBorder(20, 0, 0, 0));
        label.setHorizontalAlignment(SwingConstants.CENTER);
        label.setFont(new Font("Tahoma", Font.BOLD, 16));
        return label;
    }

    private String getEngineSelectedFromTab() {
        int tabNum = tabsPane.getSelectedIndex();
        if (tabNum == 0) return "mysql";
        if (tabNum == 1) return "sqlite";
        return "";
    }

    private int getTabSelectedFromEngine(Engine engine) {
        if (engine == Engine.mysql) return 0;
        if (engine == Engine.sqlite) return 1;
        return 0;
    }

    private JPanel getFooter() {
        ANSI.printCyan("DatabaseConfigView.getFooter()");
        JPanel panel = new JPanel();
        panel.setBorder(BorderFactory.createEmptyBorder(0, 0, 20, 0));
        JButton btnSave = new JButton("Guardar");

        btnSave.addActionListener(e -> {
            String engine = getEngineSelectedFromTab();

            if (engine.equals("mysql")) {
                String host = hostField.getText();
                int port;
                try {
                    port = Integer.parseInt(portField.getText());
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(this, "El puerto no es válido", "ERROR", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                String user = usernameField.getText();
                String password = passwordField.getText();
                String databaseName = dbNameField.getText();

                if (!Controller.setMySqlConfig(host, port, user, password, databaseName)) {
                    JOptionPane.showMessageDialog(this, "La conexión no se ha podido establecer", "ERROR", JOptionPane.ERROR_MESSAGE);
                } else {
                    this.dispose();
                }

            }

            if (engine.equals("sqlite")) {
                if (Controller.setSQLiteConfig(new File(sqlitePathFile))) {

                }
            }
        });

        panel.add(btnSave);
        return panel;
    }

    private JTabbedPane getTabs() {
        ANSI.printCyan("DatabaseConfigView.getTabs()");
        tabsPane = new JTabbedPane();
        tabsPane.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        tabsPane.addTab("MySQL", getMySqlTab());
        tabsPane.addTab("SQLite", getSQLiteTab());
        return tabsPane;
    }

    private JPanel getMySqlTab() {
        ANSI.printCyan("DatabaseConfigView.getMySqlTab()");
        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(2, 2, 2, 2);
        gbc.anchor = GridBagConstraints.WEST;

        gbc.gridx = 0;
        gbc.gridy = 0;

        JLabel lblID = new JLabel("Servidor (host):");
        centerPanel.add(lblID, gbc);

        gbc.gridx = 1;

        hostField = new JTextField(20);
        centerPanel.add(hostField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;

        JLabel lblTitle = new JLabel("Puerto:");
        centerPanel.add(lblTitle, gbc);

        gbc.gridx = 1;

        portField = new JTextField(20);
        centerPanel.add(portField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;

        JLabel lblAuthor = new JLabel("Usuario:");
        centerPanel.add(lblAuthor, gbc);

        gbc.gridx = 1;

        usernameField = new JTextField(20);
        centerPanel.add(usernameField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 3;

        JLabel lblPassword = new JLabel("Contraseña:");
        centerPanel.add(lblPassword, gbc);

        gbc.gridx = 1;

        passwordField = new JPasswordField(20);
        centerPanel.add(passwordField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 4;

        JLabel lblDBname = new JLabel("Nombre base de datos:");
        centerPanel.add(lblDBname, gbc);

        gbc.gridx = 1;

        dbNameField = new JTextField(20);
        centerPanel.add(dbNameField, gbc);

        return centerPanel;
    }

    private JPanel getSQLiteTab() {
        ANSI.printCyan("DatabaseConfigView.getSQLiteTab()");
        JPanel panel = new JPanel();
        btnSqlite = new JButton("Seleccionar fichero SQLite");
        sqliteLabel = new JLabel("No hay ningún fichero elegido");

        btnSqlite.addActionListener(e -> {
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setSelectedFile(new File("biblioteca.sqlite"));
            int returnValue = fileChooser.showOpenDialog(null);

            if (returnValue == JFileChooser.APPROVE_OPTION) {
                java.io.File selectedFile = fileChooser.getSelectedFile();
                sqlitePathFile = selectedFile.getAbsolutePath();
                System.out.println("Archivo seleccionado: " + sqlitePathFile);
                sqliteLabel.setText(sqlitePathFile);
            }
        });

        panel.add(sqliteLabel);
        panel.add(btnSqlite);
        return panel;
    }
}
