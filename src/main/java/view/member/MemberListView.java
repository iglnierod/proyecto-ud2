package view.member;

import controller.Controller;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class MemberListView extends JDialog {
    private String[] columnNames;
    private JTable table;
    private Controller controller;

    public MemberListView(JFrame parent, Controller controller) {
        super(parent, "Biblioteca", true);
        setModalityType(Dialog.ModalityType.APPLICATION_MODAL);
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

        JLabel lblAvailable = new JLabel("Lista de socios");
        lblAvailable.setHorizontalAlignment(JLabel.CENTER);
        lblAvailable.setFont(new Font("Tahoma", Font.BOLD, 16));
        contentPane.add(lblAvailable, BorderLayout.NORTH);

        JScrollPane scrollPane = new JScrollPane();
        contentPane.add(scrollPane, BorderLayout.CENTER);

        try {
            columnNames = new String[]{"DNI", "Nombre", "Email"};
            String[][] data = new String[0][0];
            table = new JTable(data, columnNames);
            table.setBounds(30, 40, 200, 300);
            table.setDefaultEditor(Object.class, null);
            table.setRowSelectionAllowed(false);
        } catch (Exception e) {
            e.printStackTrace();
            return;
        }
        scrollPane.setViewportView(table);
        if (loadData(table)) {
            this.setVisible(true);
        }
    }

    private boolean loadData(JTable table) {
        DefaultTableModel model = Controller.getMembersTableModel();
        if (model.getRowCount() == 0) {
            JOptionPane.showMessageDialog(this, "No hay socios", "ERROR", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        table.setModel(model);
        return true;
    }

}
