package db;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import domain.Person;

public class PersonStorage {
    private Connection connection;

    public Connection getConnection() {
        return connection;
    }

    public void setConnection(Connection connection) {
        this.connection = connection;
    }

    public List<Person> readAll() throws SQLException {
        String sql = "SELECT `id`, `first_name`, `middle_name`, `last_name`, `height`, `weight`, `is_citizen` FROM `person`";
        Connection c = getConnection();
        Statement s = c.createStatement();
        ResultSet r = s.executeQuery(sql);
        List<Person> persons = new ArrayList<>();
        while(r.next()) {
            Person person = new Person();
            person.setId(r.getInt("id"));
            person.setFirstName(r.getString("first_name"));
            person.setMiddleName(r.getString("middle_name"));
            person.setLastName(r.getString("last_name"));
            person.setHeight(r.getDouble("height"));
            person.setWeight(r.getDouble("weight"));
            person.setCitizen(r.getBoolean("is_citizen"));
            persons.add(person);
        }
        r.close();
        s.close();
        return persons;
    }
}