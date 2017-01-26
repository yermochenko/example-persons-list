package db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import domain.User;

public class UserStorage extends BasicStorage {
    public User readByLoginAndPassword(String login, String password) throws SQLException {
        String sql = "SELECT `id` FROM `user` WHERE `login` = ? AND `password` = ?";
        Connection c = getConnection();
        PreparedStatement s = null;
        ResultSet r = null;
        try {
            s = c.prepareStatement(sql);
            s.setString(1, login);
            s.setString(2, password);
            r = s.executeQuery();
            User user = null;
            if(r.next()) {
                user = new User();
                user.setId(r.getInt("id"));
                user.setLogin(login);
                user.setPassword(password);
            }
            c.commit();
            return user;
        } catch(SQLException e) {
            try { c.rollback(); } catch(SQLException e1) {}
            throw e;
        } finally {
            try { r.close(); } catch(NullPointerException | SQLException e) {}
            try { s.close(); } catch(NullPointerException | SQLException e) {}
        }
    }
}