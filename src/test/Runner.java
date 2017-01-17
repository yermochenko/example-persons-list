package test;

import java.awt.BorderLayout;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import db.Connector;
import db.PersonStorage;
import domain.Person;

public class Runner {
    public static void main(String[] args) {
        Connection c = null;
        try {
            Connector.init(
                "com.mysql.jdbc.Driver",
                "jdbc:mysql://localhost:3306/persons_db?useUnicode=true&characterEncoding=UTF-8",
                "root",
                "root"
            );
            c = Connector.getConnection();
            PersonStorage s = new PersonStorage();
            s.setConnection(c);
            List<Person> persons = s.readAll();
            PersonTableModel m = new PersonTableModel(persons);
            JFrame window = new JFrame("Список людей");
            window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            window.setBounds(100, 100, 640, 480);
            JTable table = new JTable(m);
            JScrollPane scrollPane = new JScrollPane(table);
            window.add(scrollPane, BorderLayout.CENTER);
            window.setVisible(true);
            window.validate();
        } catch(ClassNotFoundException | SQLException e) {
            StringWriter sw = new StringWriter();
            e.printStackTrace(new PrintWriter(sw));
            JOptionPane.showMessageDialog(null, sw);
        } finally {
            try {
                c.close();
            } catch(NullPointerException | SQLException e) {}
        }
    }
}