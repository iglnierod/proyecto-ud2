package model.member.dao;

import model.book.Book;
import model.member.Member;

import java.sql.Connection;
import java.util.ArrayList;

public class MemberDAOSQLite implements MemberDAO {
    private Connection connection;

    public MemberDAOSQLite(Connection connection) {
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
        return false;
    }

    @Override
    public void update(Member member) {

    }

    @Override
    public void delete(Member member) {

    }
}
