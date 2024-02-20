package model.rents.dao;

import model.rents.Rent;

import java.sql.*;
import java.util.ArrayList;

public class RentsDAOMySQL implements RentsDAO {
    private Connection connection;

    public RentsDAOMySQL(Connection connection) {
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
        String query = "INSERT INTO rents VALUES(?,?,?,?,?)";
        try (PreparedStatement ps = this.connection.prepareStatement(query)) {
            ps.setString(1, rent.getUuid());
            ps.setInt(2, rent.getBookID());
            ps.setString(3, rent.getMemberID());
            ps.setTimestamp(4, java.sql.Timestamp.valueOf(rent.getBeginningDate()));
            ps.setTimestamp(5, java.sql.Timestamp.valueOf(rent.getEndingDate()));

            ps.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public void update(Rent rent) {

    }

    @Override
    public void delete(Rent rent) {

    }
}
