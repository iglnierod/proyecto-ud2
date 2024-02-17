package view.rent;

import controller.Controller;
import view.layout.NativeLayout;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class StartRentView extends JDialog {
    private String[] booksColumnNames;
    private JTable booksTable;
    private String[] membersColumnNames;
    private JTable membersTable;
    private JLabel lblRent2;
    private JPanel contentPane1;
    private JPanel contentPane2;
    private Controller controller;
    private JFrame parent;
    private int step;
    public StartRentView(JFrame frame, Controller controller) {
        super(frame);
        NativeLayout.set();
        setModalityType(ModalityType.APPLICATION_MODAL);
        setTitle("Biblioteca");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setMinimumSize(new Dimension(500, 350));
        setResizable(false);
        setLayout(new BorderLayout());
        JPanel contentPanel = new JPanel();
        contentPanel.setLayout(new BorderLayout());
        contentPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        setContentPane(contentPanel);
        setLocationRelativeTo(frame);

        this.controller = controller;
        this.parent = frame;
        this.step = 1;

        header();
        tableBooks();
        buttons();

        this.setVisible(true);
    }

    private void header() {
        JPanel northPanel = new JPanel();
        northPanel.setLayout(new BoxLayout(northPanel,BoxLayout.Y_AXIS));
        add(northPanel, BorderLayout.NORTH);

        JLabel lblRent = new JLabel("Iniciar préstamo de libro");
        lblRent.setHorizontalAlignment(JLabel.CENTER);
        Font fontBold = new Font("Tahoma", Font.BOLD, 16);
        lblRent.setFont(fontBold);
        lblRent.setAlignmentX(Component.CENTER_ALIGNMENT);
        northPanel.add(lblRent);

        lblRent2 = new JLabel("Selecciona el libro a prestar:");
        lblRent2.setAlignmentX(Component.CENTER_ALIGNMENT);
        northPanel.add(lblRent2);
    }

    private void buttons() {
        JPanel southPanel = new JPanel();
        southPanel.setLayout(new BorderLayout());
        add(southPanel, BorderLayout.SOUTH);

        JButton btnRent = new JButton("Siguiente");
        btnRent.addActionListener(e -> {
            if(step == 1) {
                table2();
                lblRent2.setText("Selecciona el socio que realiza el préstamo:");
                btnRent.setText("Aceptar");
                step = 2;
                return;
            }
            if(step == 2) {

                this.dispose();
            }
        });
        southPanel.add(btnRent, BorderLayout.EAST);

        JButton btnCancel = new JButton("Cancelar");
        btnCancel.addActionListener(e -> dispose());
        southPanel.add(btnCancel, BorderLayout.WEST);
    }

    private void tableBooks() {
        JPanel contentPane = new JPanel();
        contentPane.setLayout(new GridBagLayout());
        add(contentPane, BorderLayout.CENTER);
        contentPane1 = contentPane;

        contentPane.setBorder(new EmptyBorder(15, 15, 15, 15));
        contentPane.setLayout(new BorderLayout(0, 50));

        JScrollPane scrollPane = new JScrollPane();
        contentPane.add(scrollPane);

        try {
            booksColumnNames = new String[] { "ID", "Título", "Autor/a" };
            String[][] data = new String[0][0]; // Completar con libros
            booksTable = new JTable(data, booksColumnNames);
            booksTable.setDefaultEditor(Object.class, null);
        } catch (Exception e) {
            e.printStackTrace();
            return;
        }

        scrollPane.setViewportView(booksTable);
        this.revalidate();
        this.repaint();
    }

    private void table2() {
        JPanel contentPane = new JPanel();
        contentPane.setLayout(new GridBagLayout());
        remove(contentPane1);
        add(contentPane, BorderLayout.CENTER);
        contentPane2 = contentPane;

        contentPane.setBorder(new EmptyBorder(15, 15, 15, 15));
        contentPane.setLayout(new BorderLayout(0, 50));


        try {
            membersColumnNames = new String[] { "DNI", "Nombre", "Email" };
            String[][] data = new String[0][0]; // Completar con socios
            membersTable = new JTable(data, membersColumnNames);
            membersTable.setDefaultEditor(Object.class, null);
        } catch (Exception e) {
            e.printStackTrace();
            return;
        }

        JScrollPane scrollPane = new JScrollPane();
        contentPane.add(scrollPane);
        scrollPane.setViewportView(membersTable);

        this.revalidate();
        this.repaint();
    }
}


