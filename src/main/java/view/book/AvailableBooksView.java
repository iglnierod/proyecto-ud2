package view.book;

import controller.Controller;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import java.awt.*;

public class AvailableBooksView extends JDialog {
    private String[] columnNames;
    private JTable table;
    private Controller controller;

    public AvailableBooksView(JFrame parent, Controller controller) {
        super(parent, "Biblioteca", true);
        setModalityType(ModalityType.APPLICATION_MODAL);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setMinimumSize(new Dimension(600, 400));
        setResizable(false);
        JPanel southPanel = new JPanel();
        southPanel.setLayout(new BorderLayout());
        setLocationRelativeTo(parent);
        this.controller = controller;

        JPanel contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(15, 15, 15, 15));
        setContentPane(contentPane);
        contentPane.setLayout(new BorderLayout(0, 50));

        JLabel lblAvailable = new JLabel("Libros disponibles");
        lblAvailable.setHorizontalAlignment(JLabel.CENTER);
        lblAvailable.setFont(new Font("Tahoma", Font.BOLD, 16));
        contentPane.add(lblAvailable, BorderLayout.NORTH);

        JScrollPane scrollPane = new JScrollPane();
        contentPane.add(scrollPane, BorderLayout.CENTER);

        try {
            columnNames = new String[]{"ID", "Título", "Autor/a"};
            String[][] data = new String[0][0];
            table = new JTable(data, columnNames);
            loadData(table);
            table.setBounds(30, 40, 200, 300);
            table.setDefaultEditor(Object.class, null);
            table.setRowSelectionAllowed(false);
        } catch (Exception e) {
            e.printStackTrace();
            return;
        }

        scrollPane.setViewportView(table);
        this.setVisible(true);
    }

    private void loadData(JTable table) {
        table.setModel(Controller.getAvailableBooksTableModel());
    }
}
