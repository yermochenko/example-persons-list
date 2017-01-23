package db;

import java.sql.Connection;

abstract public class BasicStorage {
    private Connection connection;

    public Connection getConnection() {
        return connection;
    }

    public void setConnection(Connection connection) {
        this.connection = connection;
    }
}