package view.book;

import controller.Controller;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class RentedBooksView extends JDialog {
    private String[] columnNames;
    private JTable table;
    private Controller controller;
    private JFrame parent;

    public RentedBooksView(JFrame parent, Controller controller) {
        super(parent, "Biblioteca", true);
        setModalityType(Dialog.ModalityType.APPLICATION_MODAL);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setMinimumSize(new Dimension(600, 400));
        setResizable(false);
        JPanel southPanel = new JPanel();
        southPanel.setLayout(new BorderLayout());
        setLocationRelativeTo(parent);
        this.parent = parent;
        this.controller = controller;

        JPanel contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(15, 15, 15, 15));
        setContentPane(contentPane);
        contentPane.setLayout(new BorderLayout(0, 50));

        JLabel lblAvailable = new JLabel("Libros prestados");
        lblAvailable.setHorizontalAlignment(JLabel.CENTER);
        lblAvailable.setFont(new Font("Tahoma", Font.BOLD, 16));
        contentPane.add(lblAvailable, BorderLayout.NORTH);

        JScrollPane scrollPane = new JScrollPane();
        contentPane.add(scrollPane, BorderLayout.CENTER);

        try {
            columnNames = new String[] { "ID", "Título", "Autor/a" };
            String[][] data = new String[0][0];
            table = new JTable(data, columnNames);
            table.setModel(Controller.getRentedHistoryTableModel());
            table.setBounds(30,40,200,300);
            table.setDefaultEditor(Object.class, null);
            table.setRowSelectionAllowed(false);

        } catch (Exception e) {
            e.printStackTrace();
            return;
        }

        scrollPane.setViewportView(table);
        this.setVisible(true);
    }
}
