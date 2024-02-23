package model.rents.dao;

import model.rents.Rent;

import java.sql.*;
import java.util.ArrayList;
import java.util.UUID;

public class RentsDAOMySQL implements RentsDAO {
    private Connection connection;

    public RentsDAOMySQL(Connection connection) {
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
        String query = "SELECT r.uuid, b.id, b.title, b.author, m.id, m.name, m.email, r.beginning, r.ending\n" +
                "FROM rents r\n" +
                "JOIN books b ON r.id_book = b.id\n" +
                "JOIN members m ON r.id_member = m.id\n" +
                "ORDER BY r.beginning ASC;";
        ArrayList<ArrayList<String>> rentedList = new ArrayList<>();
        try (Statement stmt = connection.createStatement()) {
            ResultSet rs = stmt.executeQuery(query);
            while (rs.next()) {
                ArrayList<String> rentedItem = new ArrayList<>();
                rentedItem.add(rs.getString("uuid"));
                rentedItem.add(String.valueOf(rs.getInt(2))); // book id
                rentedItem.add(rs.getString("title"));
                rentedItem.add(rs.getString("author"));
                rentedItem.add(rs.getString(5)); // member id
                rentedItem.add(rs.getString("name"));
                rentedItem.add(rs.getString("email"));
                rentedItem.add(rs.getString("beginning"));
                rentedItem.add(rs.getString("ending") == null ? "NULL" : rs.getString("ending"));

                rentedList.add(rentedItem);
            }
            return rentedList;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public Rent getById(String uuid) {
        return null;
    }

    @Override
    public boolean create(Rent rent) {
        String query = "INSERT INTO rents VALUES(?,?,?,?,?)";
        try (PreparedStatement ps = this.connection.prepareStatement(query)) {
            ps.setString(1, rent.getUuid().toString());
            ps.setInt(2, rent.getBookID());
            ps.setString(3, rent.getMemberID());
            ps.setString(4, rent.getBeginningDate());
            ps.setString(5, rent.getEndingDate());
            ps.executeUpdate();

            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean end(String uuid, Timestamp now) {

        String query = "UPDATE rents SET ending = ? WHERE uuid = ?";
        try (PreparedStatement ps = this.connection.prepareStatement(query)) {
            ps.setString(1, now.toString());
            ps.setString(2, uuid);

            ps.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public void delete(Rent rent) {

    }


}
