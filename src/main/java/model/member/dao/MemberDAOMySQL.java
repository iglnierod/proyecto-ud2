package model.member.dao;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import model.book.Book;
import model.member.Member;

import java.sql.*;
import java.util.ArrayList;

public class MemberDAOMySQL implements MemberDAO {
    private Connection connection;

    public MemberDAOMySQL(Connection connection) {
        this.connection = connection;
    }

    @Override
    public ArrayList<Member> getAll() {
        String query = "SELECT * FROM members";
        ArrayList<Member> membersList = new ArrayList<>();
        try (Statement stmt = connection.createStatement()) {
            ResultSet rs = stmt.executeQuery(query);
            while (rs.next()) {
                Member m = new Member();
                m.setId(rs.getString("id"));
                m.setName(rs.getString("name"));
                m.setEmail(rs.getString("email"));

                membersList.add(m);
            }
            return membersList;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public Member getById(int id) {
        return null;
    }

    @Override
    public boolean create(Member member) {
        String query = "INSERT INTO members(id, name, email) VALUES(?,?,?)";
        try (PreparedStatement ps = this.connection.prepareStatement(query)) {
            ps.setString(1, member.getId());
            ps.setString(2, member.getName());
            ps.setString(3, member.getEmail());

            ps.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public void update(Member member) {

    }

    @Override
    public void delete(Member member) {

    }

    @Override
    public JsonArray export() {
        JsonArray membersArray = new JsonArray();
        for (Member m : getAll()) {
            JsonObject jsonObject = new JsonObject();
            jsonObject.addProperty("id", m.getId());
            jsonObject.addProperty("name", m.getName());
            jsonObject.addProperty("email", m.getEmail());
            membersArray.add(jsonObject);
        }
        return membersArray;
    }

    @Override
    public void importData(ArrayList<Member> members, boolean emptyTable) {
        if (emptyTable) {
            emptyTable();
        }

        for (Member m : members) {
            create(m);
        }
    }

    @Override
    public void emptyTable() {
        String query = "DELETE FROM members";
        try (Statement stmt = connection.createStatement()) {
            stmt.executeUpdate(query);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
