package view.rent;

import controller.Controller;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class EndRentView extends JDialog {
    private JTextField idField;
    private Controller controller;
    private JFrame parent;

    public EndRentView(JFrame parent, Controller controller) {
        super(parent, "Biblioteca", true);
        setModalityType(Dialog.ModalityType.APPLICATION_MODAL);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setMinimumSize(new Dimension(400, 258));
        setResizable(false);
        JPanel southPanel = new JPanel();
        southPanel.setLayout(new BorderLayout());
        JPanel contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(50, 15, 15, 15));
        setLocationRelativeTo(parent);

        this.parent = parent;
        this.controller = controller;

        setContentPane(contentPane);
        contentPane.setLayout(new BorderLayout());

        add(southPanel, BorderLayout.SOUTH);

        JPanel centerPanel = new JPanel();
        add(centerPanel, BorderLayout.CENTER);
        centerPanel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();

        JLabel lblReturn = new JLabel("Finalizar préstamo");
        lblReturn.setHorizontalAlignment(SwingConstants.CENTER);
        lblReturn.setFont(new Font("Tahoma", Font.BOLD, 16));
        add(lblReturn, BorderLayout.NORTH);

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(3, 3, 3, 3);
        gbc.anchor = GridBagConstraints.WEST;

        JLabel lblID = new JLabel("Código de devolución:");
        centerPanel.add(lblID, gbc);

        gbc.gridx = 1;

        idField = new JTextField(20);
        centerPanel.add(idField);

        JButton btnReturn = new JButton("Aceptar");
        btnReturn.addActionListener(e -> {
            /**
             * completar
             */
            this.dispose();
        });
        southPanel.add(btnReturn, BorderLayout.EAST);

        JButton btnCancel = new JButton("Cancelar");
        btnCancel.addActionListener(e -> dispose());
        southPanel.add(btnCancel, BorderLayout.WEST);

        this.setVisible(true);
    }
}
