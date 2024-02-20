package model.rents.dao;

import model.rents.Rent;

import java.sql.Connection;
import java.util.ArrayList;

public class RentsDAOSQLite implements RentsDAO {
    private Connection connection;

    public RentsDAOSQLite(Connection connection) {
        this.connection = connection;
    }

    @Override
    public ArrayList<Rent> getAll() {
        return null;
    }

    @Override
    public Rent getById(int uuid) {
        return null;
    }

    @Override
    public boolean create(Rent rent) {
        return false;
    }

    @Override
    public void update(Rent rent) {

    }

    @Override
    public void delete(Rent rent) {

    }
}
