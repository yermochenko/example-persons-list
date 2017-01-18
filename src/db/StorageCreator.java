package db;

import java.sql.Connection;
import java.sql.SQLException;

public class StorageCreator {
    private Connection connection;

    public StorageCreator() throws SQLException {
        connection = Connector.getConnection();
    }

    public PersonStorage newPersonStorage() {
        PersonStorage storage = new PersonStorage();
        storage.setConnection(connection);
        return storage;
    }

    public void close() {
        try { connection.close(); } catch(SQLException e) {}
    }
}