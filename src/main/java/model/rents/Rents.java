package model.rents;

import java.util.HashMap;

public class Rents {
    private HashMap<String, Rent> rents;

    public Rents() {

    }

    public Rents(HashMap<String, Rent> rents) {
        this.rents = rents;
    }

    // METHODS
    public void add(Rent rent) {
        this.rents.put(rent.getUuid(), rent);
    }
}
