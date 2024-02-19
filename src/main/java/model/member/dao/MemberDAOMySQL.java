package model.member.dao;

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
        return null;
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
}
