package model.member.dao;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import model.member.Member;
import model.member.Members;

import java.util.ArrayList;

public class MemberDAOSQLite implements MemberDAO {
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
