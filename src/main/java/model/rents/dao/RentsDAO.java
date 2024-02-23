package model.rents.dao;

import model.rents.Rent;

import java.sql.Timestamp;
import java.util.ArrayList;

public interface RentsDAO {
    public ArrayList<Rent> getAll();

    public ArrayList<ArrayList<String>> getHistory();

    public Rent getById(String uuid);

    public boolean create(Rent rent);

    public boolean end(String uuid, Timestamp now);

    public void delete(Rent rent);
}
