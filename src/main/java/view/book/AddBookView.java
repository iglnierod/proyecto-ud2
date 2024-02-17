package view.book;

import controller.Controller;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class AddBookView extends JDialog {
    private JTextField titleField;
    private JTextField authorField;

    public AddBookView(JFrame parent, Controller controller) {
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

        JLabel lblAddBook = new JLabel("Añadir libro");
        lblAddBook.setHorizontalAlignment(SwingConstants.CENTER);
        lblAddBook.setFont(new Font("Tahoma", Font.BOLD, 16));
        add(lblAddBook, BorderLayout.NORTH);

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(3, 3, 3, 3);
        gbc.anchor = GridBagConstraints.WEST;

        JLabel lblTitle = new JLabel("Título:");
        centerPanel.add(lblTitle, gbc);

        gbc.gridx = 1;

        titleField = new JTextField(20);
        centerPanel.add(titleField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;

        JLabel lblAuthor = new JLabel("Autor/a:");
        centerPanel.add(lblAuthor, gbc);

        gbc.gridx = 1;

        authorField = new JTextField(20);
        centerPanel.add(authorField, gbc);

        JButton btnAddBook = new JButton("Aceptar");
        btnAddBook.addActionListener(e -> {
            /**
             * completar
             */
            this.dispose();
        });
        southPanel.add(btnAddBook, BorderLayout.EAST);

        JButton btnCancel = new JButton("Cancelar");
        btnCancel.addActionListener(e -> this.dispose());
        southPanel.add(btnCancel, BorderLayout.WEST);

        this.setVisible(true);
    }
}
