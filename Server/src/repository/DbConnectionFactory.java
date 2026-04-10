package repository;

import konfiguracija.Konfiguracija;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DbConnectionFactory {

    private static DbConnectionFactory instance;
    private Connection connection;

    private DbConnectionFactory() {
    }

    public static DbConnectionFactory getInstance() {
        if (instance == null) {
            instance = new DbConnectionFactory();
        }
        return instance;
    }

    public Connection getConnection() throws SQLException {
        if (connection == null || connection.isClosed()) {
            String url = Konfiguracija.getInstance().getProperty("url");
            String username = Konfiguracija.getInstance().getProperty("username");
            String password = Konfiguracija.getInstance().getProperty("password");
            connection = DriverManager.getConnection(url, username, password);
            connection.setAutoCommit(false);
        }
        return connection;
    }
}
