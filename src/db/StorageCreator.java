package db;

import java.sql.Connection;
import java.sql.SQLException;

public class StorageCreator {
    private Connection connection;
    private PersonStorage personStorage;
    private TypeStorage typeStorage;
    private ContactStorage contactStorage;
    private UserStorage userStorage;

    public StorageCreator() throws SQLException {
        connection = Connector.getConnection();
    }

    public PersonStorage newPersonStorage() {
        if(personStorage == null) {
            personStorage = new PersonStorage();
            personStorage.setConnection(connection);
            personStorage.setContactStorage(newContactStorage());
            personStorage.setTypeStorage(newTypeStorage());
        }
        return personStorage;
    }

    public TypeStorage newTypeStorage() {
        if(typeStorage == null) {
            typeStorage = new TypeStorage();
            typeStorage.setConnection(connection);
        }
        return typeStorage;
    }

    public ContactStorage newContactStorage() {
        if(contactStorage == null) {
            contactStorage = new ContactStorage();
            contactStorage.setConnection(connection);
            contactStorage.setPersonStorage(newPersonStorage());
            contactStorage.setTypeStorage(newTypeStorage());
        }
        return contactStorage;
    }

    public UserStorage newUserStorage() {
        if(userStorage == null) {
            userStorage = new UserStorage();
            userStorage.setConnection(connection);
        }
        return userStorage;
    }

    public void close() {
        try { connection.close(); } catch(SQLException e) {}
    }
}