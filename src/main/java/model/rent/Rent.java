package model.rent;

import utils.Util;

import java.sql.Timestamp;
import java.util.UUID;

public class Rent {
    private UUID uuid;
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
        this.uuid = UUID.randomUUID();
    }

    public Rent(int bookID, String memberID, String beginningDate, String endingDate) {
        this.bookID = bookID;
        this.memberID = memberID;
        this.beginningDate = beginningDate;
        this.endingDate = endingDate;
    }

    // METHODS

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


    public UUID getUuid() {
        return uuid;
    }

    public void setUuid(UUID uuid) {
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
