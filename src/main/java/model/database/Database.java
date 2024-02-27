package model.database;

import model.book.Book;
import org.springframework.core.io.PathResource;
import org.springframework.core.io.support.EncodedResource;
import org.springframework.jdbc.datasource.init.ScriptUtils;

import java.io.*;
import java.sql.*;
import java.util.HashSet;
import java.util.Set;

public class Database implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
    private static final String NAME = "library";
    private static final String[] TABLES = new String[]{"books", "members", "rents"};
    private String host;
    private int port;
    private String user;
    private String password;
    private String databaseName;
    private static final File CONFIG_FILE = new File("config.bin");
    private boolean configLoaded;

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
            return db;
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        return new Database();
    }

    public Connection getConnection() {
        String url = String.format("jdbc:mysql://%s:%d/%s", getHost(), getPort(), getDatabaseName());
        try {
            Connection con = DriverManager.getConnection(url, getUser(), getPassword());
            Statement stmt = con.createStatement();
            stmt.executeUpdate("USE " + (getDatabaseName().equals("") ? NAME : getDatabaseName()));
            return con;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean isConnectionValid() {
        String url = String.format("jdbc:mysql://%s:%d", getHost(), getPort());
        try (Connection con = DriverManager.getConnection(url, getUser(), getPassword())) {
            System.err.println("isConnectionValid(): Conexión válida");
            return true;
        } catch (SQLException e) {
            System.err.println("isConnectionValid(): Conexión no válida");
            return false;
        }
    }

    public boolean isCreated() {
        String url = String.format("jdbc:mysql://%s:%d/%s", getHost(), getPort(), Database.NAME);
        try (Connection con = DriverManager.getConnection(url, getUser(), getPassword())) {
            Set<String> existingTables = getExistingTables(con);
            if (existingTables.containsAll(Set.of(TABLES))) {
                System.err.println("isCreated(): true");
                return true;
            } else {
                System.err.println("isCreated(): false - Not all tables exist.");
                return false;
            }
        } catch (SQLException e) {
            System.err.println("isCreated(): false - " + e.getMessage());
            return false;
        }
    }

    private Set<String> getExistingTables(Connection con) throws SQLException {
        Set<String> existingTables = new HashSet<>();
        try (Statement stmt = con.createStatement();
             ResultSet rs = stmt.executeQuery("SHOW TABLES")) {
            while (rs.next()) {
                existingTables.add(rs.getString(1));
            }
        }
        return existingTables;
    }

    public boolean createDatabase() {
        System.out.println("createDatabase()");
        try {
            Connection connection = DriverManager.getConnection(String.format("jdbc:mysql://%s:%d", getHost(), getPort()), "root", "abc123.");
            String path = "library.sql";
            boolean continueOrError = false;
            boolean ignoreFailedDrops = false;
            String commentPrefix = "#";
            String separator = ";";
            String blockCommentStartDelimiter = "/*";
            String blockCommentEndDelimiter = "*/";

            ScriptUtils.executeSqlScript(
                    connection,
                    new EncodedResource(new PathResource(path)),
                    continueOrError,
                    ignoreFailedDrops,
                    commentPrefix,
                    separator,
                    blockCommentStartDelimiter,
                    blockCommentEndDelimiter
            );
            return true;
        } catch (SQLException e) {
            System.err.println("createDatabase(): fallo ejecutando script");
            //e.printStackTrace();
            return false;
        }
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
