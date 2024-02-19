package model.member;

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
}
