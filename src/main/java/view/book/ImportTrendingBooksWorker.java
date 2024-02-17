package view.book;

import controller.Controller;

import javax.swing.*;
import java.util.List;

public class ImportTrendingBooksWorker extends SwingWorker {
    private JLabel statusLabel;
    private Controller controller;
    private JFrame parent;

    public ImportTrendingBooksWorker(Controller controller, JFrame parent, JLabel statusLabel) {
        super();
        this.statusLabel = statusLabel;
        this.controller = controller;
        this.parent = parent;
    }

    @Override
    protected String doInBackground() throws Exception {
        System.out.println("ImportTrendingBooksWorker.doInBackground()");

        // Código para reemplazar
        for (int i = 0; i < 100; i++) {
            Thread.sleep(100);
            String lbl = String.format("Completado: %d%%",i);
            statusLabel.setText(lbl);
            publish(i);
        }

        return String.valueOf(100);
    }

    @Override
    protected void process(List chunks) {
    }

    @Override
    protected void done() {
        System.out.println("ImportTrendingBooksWorker.done()");
        JOptionPane.showMessageDialog(parent, "La descarga se ha completado", "Aviso", JOptionPane.WARNING_MESSAGE);
    }
}
