package model.rent;

import javax.swing.table.DefaultTableModel;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

public class Rents {
    private HashMap<UUID, Rent> rents;

    public Rents() {
        this.rents = new HashMap<>();
    }

    public Rents(HashMap<UUID, Rent> rents) {
        this.rents = rents;
    }

    // METHODS
    public void add(Rent rent) {
        this.rents.put(rent.getUuid(), rent);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (Rent r : rents.values()) {
            sb.append(r).append("\n");
        }
        return sb.toString();
    }

    public void load(ArrayList<Rent> rentsList) {
        for (Rent b : rentsList) {
            rents.put(b.getUuid(), b);
        }
    }

    public Rent get(UUID uuid) {
        return this.rents.get(uuid);
    }

    public boolean exists(UUID uuid) {
        return this.rents.get(uuid) != null;
    }

    public DefaultTableModel getRentedHistory(ArrayList<ArrayList<String>> rentedList) {
        DefaultTableModel model = new DefaultTableModel();
        String[] columnNames = new String[]{"UUID", "ID", "Título", "Autor/a", "DNI", "Nombre", "Correo electrónico", "Fecha inicio", "Fecha fin"};

        this.addColumns(model, columnNames);

        for (ArrayList<String> rentedItem : rentedList) {
            Object[] rowData = rentedItem.toArray();
            model.addRow(rowData);
        }

        return model;
    }

    private void addColumns(DefaultTableModel model, String[] columns) {
        for (String s : columns) {
            model.addColumn(s);
        }
    }

    public boolean isEmpty() {
        System.out.println(rents.size());
        return rents.isEmpty();
    }
}
