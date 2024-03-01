package model.rent.dao;

import com.google.gson.JsonArray;
import model.rent.Rent;

import java.sql.Connection;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.UUID;

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
    public ArrayList<ArrayList<String>> getHistory() {
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
    public boolean end(UUID uuid, Timestamp now) {
        return false;
    }

    @Override
    public void delete(Rent rent) {

    }

    @Override
    public JsonArray export() {
        return null;
    }
}
