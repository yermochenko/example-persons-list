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
import domain.Type;

public class ContactStorage extends BasicStorage {
    public List<Contact> readByPersonId(Integer id) throws SQLException {
        String sql = "SELECT `id`, `type_id`, `value` FROM `contact` WHERE `person_id` = ?";
        Connection c = getConnection();
        PreparedStatement s = null;
        ResultSet r = null;
        try {
            s = c.prepareStatement(sql);
            s.setInt(1, id);
            r = s.executeQuery();
            List<Contact> contacts = new ArrayList<>();
            while(r.next()) {
                Contact contact = new Contact();
                contact.setId(r.getInt("id"));
                contact.setPerson(new Person());
                contact.getPerson().setId(id);
                contact.setType(new Type());
                contact.getType().setId(r.getInt("type_id"));
                contact.setValue(r.getString("value"));
                contacts.add(contact);
            }
            c.commit();
            return contacts;
        } catch(SQLException e) {
            try { c.rollback(); } catch(SQLException e1) {}
            throw e;
        } finally {
            try { r.close(); } catch(NullPointerException | SQLException e) {}
            try { s.close(); } catch(NullPointerException | SQLException e) {}
        }
    }

    public Contact readById(Integer id) throws SQLException {
        String sql = "SELECT `type_id`, `person_id`, `value` FROM `contact` WHERE `id` = ?";
        Connection c = getConnection();
        PreparedStatement s = null;
        ResultSet r = null;
        try {
            s = c.prepareStatement(sql);
            s.setInt(1, id);
            r = s.executeQuery();
            Contact contact = null;
            if(r.next()) {
                contact = new Contact();
                contact.setId(id);
                contact.setType(new Type());
                contact.getType().setId(r.getInt("type_id"));
                contact.setPerson(new Person());
                contact.getPerson().setId(r.getInt("person_id"));
                contact.setValue(r.getString("value"));
            }
            c.commit();
            return contact;
        } catch(SQLException e) {
            try { c.rollback(); } catch(SQLException e1) {}
            throw e;
        } finally {
            try { r.close(); } catch(NullPointerException | SQLException e) {}
            try { s.close(); } catch(NullPointerException | SQLException e) {}
        }
    }

    public Integer create(Contact contact) throws SQLException {
        String sql = "INSERT INTO `contact`(`person_id`, `type_id`, `value`) VALUES (?, ?, ?)";
        Connection c = getConnection();
        PreparedStatement s = null;
        ResultSet r = null;
        try {
            s = c.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            s.setInt(1, contact.getPerson().getId());
            s.setInt(2, contact.getType().getId());
            s.setString(3, contact.getValue());
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

    public void update(Contact contact) throws SQLException {
        String sql = "UPDATE `contact` SET `person_id` = ?, `type_id` = ?, `value` = ? WHERE `id` = ?";
        Connection c = getConnection();
        PreparedStatement s = null;
        try {
            s = c.prepareStatement(sql);
            s.setInt(1, contact.getPerson().getId());
            s.setInt(2, contact.getType().getId());
            s.setString(3, contact.getValue());
            s.setInt(4, contact.getId());
            s.executeUpdate();
            c.commit();
        } catch(SQLException e) {
            try { c.rollback(); } catch(SQLException e1) {}
            throw e;
        } finally {
            try { s.close(); } catch(NullPointerException | SQLException e) {}
        }
    }

    public void save(Contact contact) throws SQLException {
        if(contact.getId() != null) {
            update(contact);
        } else {
            Integer id = create(contact);
            contact.setId(id);
        }
    }

    public void delete(Integer id) throws SQLException {
        String sql = "DELETE FROM `contact` WHERE `id` = ?";
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