package model.rents;

import org.mindrot.jbcrypt.BCrypt;
import utils.Util;

import java.sql.Timestamp;

public class Rent {
    private String uuid;
    private int bookID;
    private String memberID;
    private String beginningDate;
    private String endingDate;

    public Rent() {

    }

    public Rent(int bookID, String memberID, Timestamp beginningDate, Timestamp endingDate) {
        this.bookID = bookID;
        this.memberID = memberID;
        this.beginningDate = Util.formatDate(beginningDate);
        this.endingDate = Util.formatDate(endingDate);
        this.uuid = encrypt(this.bookID + this.memberID);
    }

    public Rent(int bookID, String memberID, String beginningDate, String endingDate) {
        this.bookID = bookID;
        this.memberID = memberID;
        this.beginningDate = beginningDate;
        this.endingDate = endingDate;
    }

    // METHODS
    private String encrypt(String password) {
        return BCrypt.hashpw(password, BCrypt.gensalt());
    }

    @Override
    public String toString() {
        return "Rent{" +
                "uuid='" + uuid + '\'' +
                ", bookID=" + bookID +
                ", memberID='" + memberID + '\'' +
                ", beginningDate='" + beginningDate + '\'' +
                ", endingDate='" + endingDate + '\'' +
                '}';
    }

    // GETTERS & SETTERS
    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public int getBookID() {
        return bookID;
    }

    public void setBookID(int bookID) {
        this.bookID = bookID;
    }

    public String getMemberID() {
        return memberID;
    }

    public void setMemberID(String memberID) {
        this.memberID = memberID;
    }

    public String getBeginningDate() {
        return beginningDate;
    }

    public void setBeginningDate(String beginningDate) {
        this.beginningDate = beginningDate;
    }

    public String getEndingDate() {
        return endingDate;
    }

    public void setEndingDate(String endingDate) {
        this.endingDate = endingDate;
    }
}
