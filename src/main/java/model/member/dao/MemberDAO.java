package model.member.dao;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import model.member.Member;

import java.util.ArrayList;

public interface MemberDAO {
    public ArrayList<Member> getAll();

    public Member getById(int id);

    public boolean create(Member member);

    public void update(Member member);

    public void delete(Member member);

    public JsonArray export();

    public void importData(ArrayList<Member> members, boolean emptyTable);

    void emptyTable();

}
