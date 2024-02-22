package model.rents.dao;

import model.rents.Rent;

import java.sql.Connection;
import java.sql.Timestamp;
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
    public ArrayList<Rent> getHistory() {
        return null;
    }

    @Override
    public Rent getById(String uuid) {
        return null;
    }

    @Override
    public boolean create(Rent rent) {
        return false;
    }

    @Override
    public boolean end(String uuid, Timestamp now) {
        return false;
    }

    @Override
    public void delete(Rent rent) {

    }
}
