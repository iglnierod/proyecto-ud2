package view;

import controller.Controller;
import view.book.AddBookView;
import view.book.AvailableBooksView;
import view.book.ImportTrendingBooksView;
import view.book.RentedBooksView;
import view.layout.NativeLayout;
import view.member.AddMemberView;
import view.member.MemberListView;
import view.rent.EndRentView;
import view.rent.RentHistoryView;
import view.rent.StartRentView;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.net.URL;

public class MainView extends JFrame {
    private JButton btnRentBook;
    private JButton btnAvailableBooks;
    private JButton btnReturnBook;
    private JButton btnViewMembers;
    private JButton btnRentedBooks;
    private JButton btnRentHistory;
    private JButton btnAddMember;
    private JButton btnAddBook;
    private JMenuItem menuProperties;
    private JMenuItem menuImportJSON;
    private JMenuItem menuExportJSON;
    private JMenuItem menuImportAPIBooks;
    private JMenuItem menuExitApp;
    private Controller controller;


    public MainView(Controller controller) {
        super("Biblioteca");
        this.controller = controller;
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setMinimumSize(new Dimension(700, 400));
        setResizable(false);
        setLocationRelativeTo(null);
        setLayout();
        setUpButtonListeners();
        setUpTopMenuListeners();
    }

    private ImageIcon getImage(String path) {
        URL resource = getClass().getClassLoader().getResource(path);
        if (resource == null) {
            throw new IllegalArgumentException("File not found!");
        } else {
            try {
                File file = new File(resource.toURI());
                BufferedImage myPicture = ImageIO.read(file);
                return new ImageIcon(myPicture);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }


    private void setLayout() {
        NativeLayout.set();

        Dimension preferredButtonDimension = new Dimension(185,25);
        Font font = new Font("Tahoma", Font.BOLD, 12);
        Font mainMenuFont = new Font("Tahoma", Font.BOLD, 24);
        JPanel buttonPanel = new JPanel();
        buttonPanel.setBorder(new EmptyBorder(8, 8, 8, 8));
        add(buttonPanel, BorderLayout.CENTER);

        JPanel northPanel = new JPanel();
        northPanel.setMinimumSize(new Dimension(150, 150));
        northPanel.setLayout(new BorderLayout(0, 15));
        northPanel.setBorder(new EmptyBorder(12, 0, 0, 0));
        add(northPanel, BorderLayout.NORTH);
        buttonPanel.setLayout(new GridBagLayout());

        addMenuBar();


        // Image
        ImageIcon img = getImage("logo-bg.png");
        JLabel picLabel = new JLabel(img);
        northPanel.add(picLabel);


        // Buttons
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(8, 2, 8, 2);
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.weightx = 1;

        btnRentBook = new JButton("Iniciar préstamo");
        btnRentBook.setPreferredSize(preferredButtonDimension);
        btnRentBook.setFont(font);
        buttonPanel.add(btnRentBook, gbc);

        gbc.gridx = 1;

        btnRentHistory = new JButton("Histórico de préstamos");
        btnRentHistory.setPreferredSize(preferredButtonDimension);
        btnRentHistory.setFont(font);
        buttonPanel.add(btnRentHistory, gbc);

        gbc.gridy = 1;
        gbc.gridx = 0;

        btnReturnBook = new JButton("Finalizar préstamo");
        btnReturnBook.setPreferredSize(preferredButtonDimension);
        btnReturnBook.setFont(font);
        buttonPanel.add(btnReturnBook, gbc);

        gbc.gridx = 1;

        btnAddBook = new JButton("Añadir libro");
        btnAddBook.setPreferredSize(preferredButtonDimension);
        btnAddBook.setFont(font);
        buttonPanel.add(btnAddBook, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;

        btnRentedBooks = new JButton("Ver libros prestados");
        btnRentedBooks.setPreferredSize(preferredButtonDimension);
        btnRentedBooks.setFont(font);
        buttonPanel.add(btnRentedBooks, gbc);

        gbc.gridx = 1;

        btnViewMembers = new JButton("Ver socios");
        btnViewMembers.setPreferredSize(preferredButtonDimension);
        btnViewMembers.setFont(font);
        buttonPanel.add(btnViewMembers, gbc);

        gbc.gridx = 0;
        gbc.gridy = 3;

        btnAvailableBooks = new JButton("Ver libros disponibles");
        btnAvailableBooks.setPreferredSize(preferredButtonDimension);
        btnAvailableBooks.setFont(font);
        buttonPanel.add(btnAvailableBooks, gbc);

        gbc.gridx = 1;

        btnAddMember = new JButton("Añadir socio");
        btnAddMember.setPreferredSize(preferredButtonDimension);
        btnAddMember.setFont(font);
        buttonPanel.add(btnAddMember, gbc);
    }

    private void addMenuBar() {
        JMenuBar menuBar = new JMenuBar();
        setJMenuBar(menuBar);

        JMenu exportarMenu = new JMenu("Archivo");
        menuBar.add(exportarMenu);

        menuProperties = new JMenuItem("Configuración base de datos");
        menuImportJSON = new JMenuItem("Importar JSON");
        menuExportJSON = new JMenuItem("Exportar JSON");
        menuImportAPIBooks = new JMenuItem("Importar libros en tendencias de Open Library");
        menuExitApp = new JMenuItem("Salir");

        exportarMenu.add(menuProperties);
        exportarMenu.addSeparator();
        exportarMenu.add(menuImportJSON);
        exportarMenu.add(menuExportJSON);
        exportarMenu.addSeparator();
        exportarMenu.add(menuImportAPIBooks);
        exportarMenu.addSeparator();
        exportarMenu.add(menuExitApp);
    }

    private void setUpButtonListeners() {
        btnAddBook.addActionListener(e -> new AddBookView(this, controller));
        btnAddMember.addActionListener(e -> new AddMemberView(this, controller));
        btnRentBook.addActionListener(e -> new StartRentView(this, controller));
        btnAvailableBooks.addActionListener(e -> new AvailableBooksView(this, controller));
        btnViewMembers.addActionListener(e -> new MemberListView(this, controller));
        btnRentedBooks.addActionListener(e -> new RentedBooksView(this, controller));
        btnReturnBook.addActionListener(e -> new EndRentView(this, controller));
        btnRentHistory.addActionListener(e -> new RentHistoryView(this, controller));
    }


    private void setUpTopMenuListeners() {
        menuProperties.addActionListener(e -> new DatabaseConfigView(this,controller));

        menuImportJSON.addActionListener(e -> {
            JFileChooser fileChooser = new JFileChooser();
            int returnValue = fileChooser.showOpenDialog(null);

            if (returnValue == JFileChooser.APPROVE_OPTION) {
                java.io.File selectedFile = fileChooser.getSelectedFile();
                String filePath = selectedFile.getAbsolutePath();
                System.out.println("Archivo seleccionado: " + filePath);
                Controller.importData(selectedFile);
            }
        });

        menuExportJSON.addActionListener(e -> {
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setSelectedFile(new File("export.json"));
            int returnValue = fileChooser.showOpenDialog(null);

            if (returnValue == JFileChooser.APPROVE_OPTION) {
                java.io.File selectedFile = fileChooser.getSelectedFile();
                String filePath = selectedFile.getAbsolutePath();
                System.out.println("Archivo seleccionado: " + filePath);
                Controller.export(selectedFile);
            }
        });

        menuImportAPIBooks.addActionListener(e -> new ImportTrendingBooksView(this,controller));

        menuExitApp.addActionListener(e -> {
            this.dispose();
        });
    }
}
