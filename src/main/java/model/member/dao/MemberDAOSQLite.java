package model.member.dao;

import com.google.gson.JsonArray;
import model.member.Member;
import model.member.Members;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class MemberDAOSQLite implements MemberDAO {
    Connection connection;

    public MemberDAOSQLite(Connection connection) {
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
        return false;
    }

    @Override
    public void update(Member member) {

    }

    @Override
    public void delete(Member member) {

    }

    @Override
    public JsonArray export() {
        return null;
    }

    @Override
    public Members importData(ArrayList<Member> members) {
        return null;
    }

    @Override
    public void emptyTable() {

    }

}
