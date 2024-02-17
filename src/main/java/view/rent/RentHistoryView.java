package view.rent;

import controller.Controller;
import utils.ANSI;
import utils.Util;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;


public class RentHistoryView extends JDialog {
    private String[] columnNames;
    private JTable table;
    private Controller controller;
    private JFrame parent;
    public RentHistoryView(JFrame parent, Controller controller) {
        super(parent, "Biblioteca", true);
        setModalityType(Dialog.ModalityType.APPLICATION_MODAL);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setMinimumSize(new Dimension(1400, 400));
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

        JLabel lblAvailable = new JLabel("Histórico de préstamos");
        lblAvailable.setHorizontalAlignment(JLabel.CENTER);
        lblAvailable.setFont(new Font("Tahoma", Font.BOLD, 16));
        contentPane.add(lblAvailable, BorderLayout.NORTH);

        JScrollPane scrollPane = new JScrollPane();
        contentPane.add(scrollPane, BorderLayout.CENTER);

        try {
            columnNames = new String[] { "UUID", "ID", "Título", "Autor/a", "DNI", "Nombre", "Correo electrónico", "Fecha inicio", "Fecha fin" };
            String[][] data = new String[0][0];

            table = new JTable(data, columnNames);
            table.setBounds(30,40,200,300);
            table.setDefaultEditor(Object.class, null);
        } catch (Exception e) {
            e.printStackTrace();
            return;
        }

        scrollPane.setViewportView(table);
        copyToClipboard();
        this.setVisible(true);
    }

    private void copyToClipboard() {
        ANSI.printBlue("copyToClipboard()");
        table.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                int row = table.rowAtPoint(evt.getPoint());
                int col = table.columnAtPoint(evt.getPoint());
                if (row >= 0 && col >= 0) {
                    Object o = table.getModel().getValueAt(row, col);
                    Util.copyToClipBoard(o.toString());
                }
            }
        });
    }
}
