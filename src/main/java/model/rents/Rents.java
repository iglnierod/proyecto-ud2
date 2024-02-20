package model.rents;

import model.book.Book;

import java.util.ArrayList;
import java.util.HashMap;

public class Rents {
    private HashMap<String, Rent> rents;

    public Rents() {
        this.rents = new HashMap<>();
    }

    public Rents(HashMap<String, Rent> rents) {
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

    public boolean exists(String id) {
        return this.rents.get(id) != null;
    }
}
