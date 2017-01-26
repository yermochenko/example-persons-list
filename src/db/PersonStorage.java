package db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import domain.Contact;
import domain.Person;
import domain.Sex;
import domain.Type;

public class PersonStorage extends BasicStorage {
    private ContactStorage contactStorage;
    private TypeStorage typeStorage;

    public void setContactStorage(ContactStorage contactStorage) {
        this.contactStorage = contactStorage;
    }

    public void setTypeStorage(TypeStorage typeStrorage) {
        this.typeStorage = typeStrorage;
    }

    public List<Person> readAll() throws SQLException {
        String sql = "SELECT `id`, `first_name`, `middle_name`, `last_name`, `height`, `weight`, `is_citizen`, `sex`, `birthday` FROM `person`";
        Connection c = getConnection();
        Statement s = null;
        ResultSet r = null;
        try {
            s = c.createStatement();
            r = s.executeQuery(sql);
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
                person.setSex(Sex.values()[r.getInt("sex")]);
                person.setBirthday(new java.util.Date(r.getDate("birthday").getTime()));
                persons.add(person);
            }
            c.commit();
            return persons;
        } catch(SQLException e) {
            try { c.rollback(); } catch(SQLException e1) {}
            throw e;
        } finally {
            try { r.close(); } catch(NullPointerException | SQLException e) {}
            try { s.close(); } catch(NullPointerException | SQLException e) {}
        }
    }

    public Person readById(Integer id) throws SQLException {
        String sql = "SELECT `first_name`, `middle_name`, `last_name`, `height`, `weight`, `is_citizen`, `sex`, `birthday` FROM `person` WHERE `id` = ?";
        Connection c = getConnection();
        PreparedStatement s = null;
        ResultSet r = null;
        try {
            s = c.prepareStatement(sql);
            s.setInt(1, id);
            r = s.executeQuery();
            Person person = null;
            if(r.next()) {
                person = new Person();
                person.setId(id);
                person.setFirstName(r.getString("first_name"));
                person.setMiddleName(r.getString("middle_name"));
                person.setLastName(r.getString("last_name"));
                person.setHeight(r.getDouble("height"));
                person.setWeight(r.getDouble("weight"));
                person.setCitizen(r.getBoolean("is_citizen"));
                person.setSex(Sex.values()[r.getInt("sex")]);
                person.setBirthday(new java.util.Date(r.getDate("birthday").getTime()));
            }
            c.commit();
            return person;
        } catch(SQLException e) {
            try { c.rollback(); } catch(SQLException e1) {}
            throw e;
        } finally {
            try { r.close(); } catch(NullPointerException | SQLException e) {}
            try { s.close(); } catch(NullPointerException | SQLException e) {}
        }
    }

    public Person findById(Integer id) throws SQLException {
        Person person = readById(id);
        if(person != null) {
            List<Contact> contacts = contactStorage.readByPersonId(id);
            person.setContacts(contacts);
            for(Contact contact : contacts) {
                contact.setPerson(person);
                Type type = typeStorage.readById(contact.getType().getId());
                contact.setType(type);
            }
        }
        return person;
    }

    public Integer create(Person person) throws SQLException {
        String sql = "INSERT INTO `person`(`first_name`, `middle_name`, `last_name`, `height`, `weight`, `is_citizen`, `sex`, `birthday`) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        Connection c = getConnection();
        PreparedStatement s = null;
        ResultSet r = null;
        try {
            s = c.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            s.setString(1, person.getFirstName());
            s.setString(2, person.getMiddleName());
            s.setString(3, person.getLastName());
            s.setDouble(4, person.getHeight());
            s.setDouble(5, person.getWeight());
            s.setBoolean(6, person.isCitizen());
            s.setInt(7, person.getSex().ordinal());
            s.setDate(8, new java.sql.Date(person.getBirthday().getTime()));
            s.executeUpdate();
            r = s.getGeneratedKeys();
            Integer id = null;
            if(r.next()) {
                id = r.getInt(1);
            }
            c.commit();
            return id;
        } catch(SQLException e) {
            try { c.rollback(); } catch(SQLException e1) {}
            throw e;
        } finally {
            try { r.close(); } catch(NullPointerException | SQLException e) {}
            try { s.close(); } catch(NullPointerException | SQLException e) {}
        }
    }

    public void update(Person person) throws SQLException {
        String sql = "UPDATE `person` SET `first_name` = ?, `middle_name` = ?, `last_name` = ?, `height` = ?, `weight` = ?, `is_citizen` = ?, `sex` = ?, `birthday` = ? WHERE `id` = ?";
        Connection c = getConnection();
        PreparedStatement s = null;
        try {
            s = c.prepareStatement(sql);
            s.setString(1, person.getFirstName());
            s.setString(2, person.getMiddleName());
            s.setString(3, person.getLastName());
            s.setDouble(4, person.getHeight());
            s.setDouble(5, person.getWeight());
            s.setBoolean(6, person.isCitizen());
            s.setInt(7, person.getSex().ordinal());
            s.setDate(8, new java.sql.Date(person.getBirthday().getTime()));
            s.setInt(9, person.getId());
            s.executeUpdate();
            c.commit();
        } catch(SQLException e) {
            try { c.rollback(); } catch(SQLException e1) {}
            throw e;
        } finally {
            try { s.close(); } catch(NullPointerException | SQLException e) {}
        }
    }

    public void save(Person person) throws SQLException {
        if(person.getId() != null) {
            update(person);
        } else {
            Integer id = create(person);
            person.setId(id);
        }
    }

    public void delete(Integer id) throws SQLException {
        String sql = "DELETE FROM `person` WHERE `id` = ?";
        Connection c = getConnection();
        PreparedStatement s = null;
        try {
            s = c.prepareStatement(sql);
            s.setInt(1, id);
            s.executeUpdate();
            c.commit();
        } catch(SQLException e) {
            try { c.rollback(); } catch(SQLException e1) {}
            throw e;
        } finally {
            try { s.close(); } catch(NullPointerException | SQLException e) {}
        }
    }
}