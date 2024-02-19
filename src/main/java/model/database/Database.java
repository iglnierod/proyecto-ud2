package model.database;

import java.io.*;

public class Database implements Serializable {
    private String host;
    private int port;
    private String user;
    private String password;
    private String databaseName;
    private final File CONFIG_FILE = new File("config.bin");

    public Database() {
    }

    public Database(String host, int port, String user, String password, String databaseName) {
        this.host = host;
        this.port = port;
        this.user = user;
        this.password = password;
        this.databaseName = databaseName;
        createConfigFile();
    }

    private void createConfigFile() {
        try(ObjectOutputStream objectOutputStream = new ObjectOutputStream(new FileOutputStream(CONFIG_FILE))) {
            objectOutputStream.writeObject(this);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

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

    @Override
    public String toString() {
        return "Database{" +
                "host='" + host + '\'' +
                ", port=" + port +
                ", user='" + user + '\'' +
                ", password='" + password + '\'' +
                ", databaseName='" + databaseName + '\'' +
                ", CONFIG_FILE=" + CONFIG_FILE +
                '}';
    }
}
