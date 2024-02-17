package view.member;

import controller.Controller;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

public class AddMemberView extends JDialog {
    private JTextField dniField;
    private JTextField nameField;
    private JTextField emailField;

    public AddMemberView(JFrame parent, Controller controller) {
        super(parent, "Biblioteca", true);
        setModalityType(ModalityType.APPLICATION_MODAL);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setMinimumSize(new Dimension(400, 258));
        setResizable(false);
        JPanel southPanel = new JPanel();
        southPanel.setLayout(new BorderLayout());
        JPanel contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(25, 15, 15, 15));
        setLocationRelativeTo(parent);

        setContentPane(contentPane);
        contentPane.setLayout(new BorderLayout());

        add(southPanel, BorderLayout.SOUTH);

        JPanel centerPanel = new JPanel();
        add(centerPanel, BorderLayout.CENTER);
        centerPanel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();

        JLabel lblAddBook = new JLabel("Añadir socio");
        lblAddBook.setHorizontalAlignment(SwingConstants.CENTER);
        lblAddBook.setFont(new Font("Tahoma", Font.BOLD, 16));
        add(lblAddBook, BorderLayout.NORTH);

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(3, 3, 3, 3);
        gbc.anchor = GridBagConstraints.WEST;

        JLabel lblID = new JLabel("DNI:");
        centerPanel.add(lblID, gbc);

        gbc.gridx = 1;

        dniField = new JTextField(20);
        centerPanel.add(dniField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;

        JLabel lblName = new JLabel("Nombre:");
        centerPanel.add(lblName, gbc);

        gbc.gridx = 1;

        nameField = new JTextField(20);
        centerPanel.add(nameField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;

        JLabel lblEmail = new JLabel("Email:");
        centerPanel.add(lblEmail, gbc);

        gbc.gridx = 1;

        emailField = new JTextField(20);
        centerPanel.add(emailField, gbc);

        JButton btnAddMember = new JButton("Aceptar");
        btnAddMember.addActionListener(e -> {
            /**
             * completar
             */
            this.dispose();
        });
        southPanel.add(btnAddMember, BorderLayout.EAST);

        JButton btnCancel = new JButton("Cancelar");
        btnCancel.addActionListener(e -> this.dispose());
        southPanel.add(btnCancel, BorderLayout.WEST);

        this.setVisible(true);
    }
}