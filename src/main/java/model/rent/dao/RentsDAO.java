package model.rent.dao;

import com.google.gson.JsonArray;
import model.rent.Rent;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.UUID;

public interface RentsDAO {
    public ArrayList<Rent> getAll();

    public ArrayList<ArrayList<String>> getHistory();

    public Rent getById(String uuid);

    public boolean create(Rent rent);

    public boolean end(UUID uuid, Timestamp now);

    public void delete(Rent rent);

    public JsonArray export();
}
