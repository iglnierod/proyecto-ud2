package model.rents.dao;

import model.rents.Rent;

import java.util.ArrayList;

public interface RentsDAO {
    public ArrayList<Rent> getAll();

    public Rent getById(String uuid);

    public boolean create(Rent rent);

    public boolean end(String uuid);

    public void delete(Rent rent);
}
