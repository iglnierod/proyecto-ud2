package model.member.dao;

import model.book.Book;
import model.member.Member;

import java.util.ArrayList;

public interface MemberDAO {
    public ArrayList<Member> getAll();
    public Member getById(int id);
    public boolean create(Member member);
    public void update(Member member);
    public void delete(Member member);
}
