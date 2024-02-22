package model.database;

import java.io.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class Database implements Serializable {

    // 1L = Only MySQL | 2L = MySQL + SQLite
    @Serial
    private static final long serialVersionUID = 2L;

    public static final String DEFAULT_TIMESTAMP = "2001-01-01 00:00:00";
    private String host;
    private int port;
    private String user;
    private String password;
    private String databaseName;
    private static final File CONFIG_FILE = new File("config.bin");
    private boolean configLoaded;
    private File sqliteDatabase;
    private Engine selectedEngine;

    public Database() {

    }

    public Database(File sqliteDatabase) {
        this.sqliteDatabase = sqliteDatabase;
        this.selectedEngine = Engine.sqlite;
        this.host = null;
        this.port = -1;
        this.user = null;
        this.password = null;
        this.databaseName = null;
        createConfigFile();
    }

    public Database(String host, int port, String user, String password, String databaseName) {
        this.host = host;
        this.port = port;
        this.user = user;
        this.password = password;
        this.databaseName = databaseName;
        this.selectedEngine = Engine.mysql;
        createConfigFile();
    }

    // METHODS
    private void createConfigFile() {
        try (ObjectOutputStream objectOutputStream = new ObjectOutputStream(new FileOutputStream(CONFIG_FILE))) {
            objectOutputStream.writeObject(this);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static Database loadConfigFile() {
        Database db = new Database();
        db.setConfigLoaded(false);
        if (!CONFIG_FILE.exists()) {
            return db;
        }

        try (ObjectInputStream objectInputStream = new ObjectInputStream(new FileInputStream(CONFIG_FILE))) {
            db = (Database) objectInputStream.readObject();
            db.setConfigLoaded(true);
            System.out.println(db);
            return db;
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        return new Database();
    }

    public Connection getConnection() {
        String url;
        Connection con;
        Statement stmt;
        switch (selectedEngine) {
            case mysql -> {
                url = String.format("jdbc:mysql://%s:%d/%s", getHost(), getPort(), getDatabaseName());
                try {
                    con = DriverManager.getConnection(url, getUser(), getPassword());
                    stmt = con.createStatement();
                    stmt.executeUpdate("USE " + getDatabaseName());
                    return con;
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                return null;
            }
            case sqlite -> {
                url = String.format("jdbc:sqlite:%s", sqliteDatabase.getAbsolutePath());
                try {
                    con = DriverManager.getConnection(url);
                    return con;
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }

    // GETTERS & SETTERS
    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getDatabaseName() {
        return databaseName;
    }

    public void setDatabaseName(String databaseName) {
        this.databaseName = databaseName;
    }

    public boolean configFileExists() {
        return CONFIG_FILE.exists();
    }

    public boolean isConfigLoaded() {
        return configLoaded;
    }

    public void setConfigLoaded(boolean configLoaded) {
        this.configLoaded = configLoaded;
    }

    public File getSqliteDatabase() {
        return sqliteDatabase;
    }

    public void setSqliteDatabase(File sqliteDatabase) {
        this.sqliteDatabase = sqliteDatabase;
    }

    public Engine getSelectedEngine() {
        return selectedEngine;
    }

    public void setSelectedEngine(Engine selectedEngine) {
        this.selectedEngine = selectedEngine;
    }

    @Override
    public String toString() {
        return "Database{" +
                "host='" + host + '\'' +
                ", port=" + port +
                ", user='" + user + '\'' +
                ", password='" + password + '\'' +
                ", databaseName='" + databaseName + '\'' +
                ", configLoaded=" + configLoaded +
                ", sqliteDatabase=" + sqliteDatabase +
                '}';
    }
}
