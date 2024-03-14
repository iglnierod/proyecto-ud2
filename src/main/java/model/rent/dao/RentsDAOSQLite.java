package model.rent.dao;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import model.rent.Rent;
import model.rent.Rents;

import java.sql.*;
import java.util.ArrayList;
import java.util.UUID;

public class RentsDAOSQLite implements RentsDAO {
    private Connection connection;

    public RentsDAOSQLite(Connection connection) {
        this.connection = connection;
    }

    @Override
    public ArrayList<Rent> getAll() {
        String query = "SELECT * FROM rents";
        ArrayList<Rent> rentsList = new ArrayList<>();
        try (Statement stmt = connection.createStatement()) {
            ResultSet rs = stmt.executeQuery(query);
            while (rs.next()) {
                Rent r = new Rent();
                r.setUuid(UUID.fromString(rs.getString("uuid")));
                r.setBookID(rs.getInt("id_book"));
                r.setMemberID(rs.getString("id_member"));
                r.setBeginningDate(rs.getString("beginning"));
                r.setEndingDate(rs.getString("ending"));

                rentsList.add(r);
            }
            return rentsList;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
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

    @Override
    public Rents importData(ArrayList<Rent> rents) {
        return null;
    }

    @Override
    public void emptyTable() {

    }
}
