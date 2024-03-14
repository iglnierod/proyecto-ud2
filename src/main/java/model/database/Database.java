package model.database;

import org.springframework.core.io.PathResource;
import org.springframework.core.io.support.EncodedResource;
import org.springframework.jdbc.datasource.init.ScriptUtils;
import utils.ANSI;

import java.io.*;
import java.sql.*;
import java.util.LinkedHashSet;
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

    // SQLite
    private File SQLiteFile;
    private Engine engine;

    public Database() {

    }

    public Database(File SQLiteFile) {
        this.engine = Engine.sqlite;

        this.SQLiteFile = SQLiteFile;
        if (isConnectionValid())
            createConfigFile();
    }

    public Database(String host, int port, String user, String password, String databaseName) {
        this.engine = Engine.mysql;

        this.host = host;
        this.port = port;
        this.user = user;
        this.password = password;
        this.databaseName = databaseName;
        if (isConnectionValid())
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
            if (db.isConnectionValid()) {
                if (!db.isCreated()) {
                    db.createDatabase();
                }
                return db;
            }
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        return new Database();
    }

    public Connection getConnection() {
        switch (engine) {
            case mysql -> {
                String url = String.format("jdbc:mysql://%s:%d/%s", getHost(), getPort(), getDatabaseName());
                try {
                    Connection con = DriverManager.getConnection(url, getUser(), getPassword());
                    Statement stmt = con.createStatement();
                    stmt.executeUpdate("USE " + (getDatabaseName().isEmpty() ? NAME : getDatabaseName()));
                    return con;
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                return null;
            }
            case sqlite -> {
                String url = String.format("jdbc:sqlite:%s", SQLiteFile.getAbsolutePath());
                try {
                    return DriverManager.getConnection(url, getUser(), getPassword());
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                return null;
            }
            default -> {
                return null;
            }
        }
    }

    public boolean isConnectionValid() {
        if (engine == Engine.mysql) {
            String url = String.format("jdbc:mysql://%s:%d", getHost(), getPort());
            try (Connection con = DriverManager.getConnection(url, getUser(), getPassword())) {
                ANSI.printBlue("isConnectionValid(): Conexión válida");
                return true;
            } catch (SQLException e) {
                System.err.println("isConnectionValid(): Conexión no válida");
                return false;
            }
        }

        if (engine == Engine.sqlite) {
            String url = String.format("jdbc:sqlite:%s", SQLiteFile.getAbsolutePath());
            try (Connection con = DriverManager.getConnection(url)) {
                ResultSet rs = con.createStatement().executeQuery("SELECT name FROM sqlite_master WHERE type = 'table'");
                Set<String> existingTables = new LinkedHashSet<>();
                while (rs.next()) {
                    existingTables.add(rs.getString(1));
                }
                if (!existingTables.containsAll(Set.of(TABLES))) {
                    createDatabase();
                }
                return true;
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
        return false;
    }

    public boolean isCreated() {
        String url = String.format("jdbc:mysql://%s:%d/", getHost(), getPort());
        Connection con = null;
        try {
            con = DriverManager.getConnection(url, getUser(), getPassword());
            Statement stmt = con.createStatement();
            stmt.executeUpdate("USE " + NAME);
            ResultSet rs = stmt.executeQuery("SHOW TABLES;");
            Set<String> existingTables = new LinkedHashSet<>();
            while (rs.next()) {
                existingTables.add(rs.getString(1));
            }

            if (!existingTables.containsAll(Set.of(TABLES))) {
                throw new SQLException();
            }

            stmt.close();
            con.close();
            ANSI.printBlue("isCreated(): true");

        } catch (SQLException e) {
            System.err.println("isCreated(): false - " + e.getMessage());
            createDatabase();
        }
        return false;
    }

    public boolean createDatabase() {
        System.out.println("createDatabase()");
        try {
            Connection connection = null;
            String path = null;
            if (engine == Engine.mysql) {
                connection = DriverManager.getConnection(String.format("jdbc:mysql://%s:%d", getHost(), getPort()), "root", "abc123.");
                path = "scripts\\create-library-MySQL.sql";
            }
            if (engine == Engine.sqlite) {
                connection = DriverManager.getConnection(String.format("jdbc:sqlite:%s", SQLiteFile.getAbsolutePath()));
                path = "scripts\\create-library-SQLite.sql";
            }
            boolean continueOrError = false;
            boolean ignoreFailedDrops = false;
            String commentPrefix = "//";
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
            this.databaseName = Database.NAME;
            return true;
        } catch (SQLException e) {
            System.err.println("createDatabase(): fallo ejecutando script");
            //e.printStackTrace();
            return false;
        }
    }

    public boolean check() {
        if (isConnectionValid()) {
            if (!isCreated()) {
                createDatabase();
            }
            return true;
        }
        return false;
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
