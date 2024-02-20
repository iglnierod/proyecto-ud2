package model.rents.dao;

import model.rents.Rent;

import java.util.ArrayList;

public interface RentsDAO {
    public ArrayList<Rent> getAll();

    public Rent getById(int uuid);

    public boolean create(Rent rent);

    public void update(Rent rent);

    public void delete(Rent rent);
}
