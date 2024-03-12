package model.member.dao;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import model.member.Member;
import model.member.Members;

import java.util.ArrayList;

public interface MemberDAO {
    public ArrayList<Member> getAll();

    public Member getById(int id);

    public boolean create(Member member);

    public void update(Member member);

    public void delete(Member member);

    public JsonArray export();

    public Members importData(ArrayList<Member> members);

    void emptyTable();

}
