package model.member;

import model.book.Book;

import javax.swing.table.DefaultTableModel;
import java.util.ArrayList;
import java.util.HashMap;

public class Members {
    private HashMap<String, Member> members;

    public Members() {
        members = new HashMap<>();
    }

    // METHODS
    public void add(Member member) {
        this.members.put(member.getId(), member);
    }

    public void remove(String id) {
        this.members.remove(id);
    }

    public void load(ArrayList<Member> membersList) {
        for(Member m : membersList) {
            members.put(m.getId(), m);
        }
    }

    public DefaultTableModel getAvailableBooksTableModel() {
        DefaultTableModel model = new DefaultTableModel();
        model.addColumn("DNI");
        model.addColumn("Nombre");
        model.addColumn("Email");

        for (Member m : members.values()) {
            String[] memberArray = {m.getId(), m.getName(), m.getEmail()};
            model.addRow(memberArray);
        }

        return model;
    }
}
