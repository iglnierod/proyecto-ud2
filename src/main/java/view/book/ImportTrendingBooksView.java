package view.book;

import controller.Controller;
import utils.ANSI;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class ImportTrendingBooksView extends JDialog {
    private Controller controller;
    private JLabel statusLabel;
    private JButton cancelButton;
    public ImportTrendingBooksView(JFrame parent, Controller controller) {
        ANSI.printRedBg("ImportTrendingBooksView");
        setModalityType(ModalityType.APPLICATION_MODAL);
        setMinimumSize(new Dimension(400, 130));
        setLayout(new GridLayout(2, 1));
        setResizable(false);
        setLocationRelativeTo(parent);
        this.controller = controller;

        JPanel southPanel = new JPanel();
        southPanel.setLayout(new BorderLayout());
        JPanel contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(25, 15, 15, 15));
        setLocationRelativeTo(parent);

        setContentPane(contentPane);
        contentPane.setLayout(new BorderLayout());

        add(southPanel, BorderLayout.SOUTH);

        JPanel centerPanel = new JPanel();
        add(centerPanel, BorderLayout.NORTH);
        centerPanel.setLayout(new GridBagLayout());

        statusLabel = new JLabel("Descargando libros desde API...");
        centerPanel.add(statusLabel);

        SwingWorker sw1 = new ImportTrendingBooksWorker(controller, parent, statusLabel);
        sw1.execute();

        cancelButton = new JButton("Cancelar");
        cancelButton.addActionListener(e -> {
            sw1.cancel(true);
            System.out.println("Descarga cancelada");
            this.dispose();
        });
        southPanel.add(cancelButton, BorderLayout.EAST);

        sw1.addPropertyChangeListener(e -> {
            if(e.getNewValue() == SwingWorker.StateValue.DONE) {
                this.dispose();
            }
        });

        this.setVisible(true);
    }
}
