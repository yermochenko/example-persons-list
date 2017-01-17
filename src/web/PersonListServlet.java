package web;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import db.Connector;
import db.PersonStorage;
import domain.Person;

public class PersonListServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
                                         throws ServletException, IOException {
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
            req.setAttribute("persons", persons);
            getServletContext().getRequestDispatcher("/WEB-INF/index.html")
                                                           .forward(req, resp);
        } catch (ClassNotFoundException | SQLException e) {
            throw new ServletException(e);
        } finally {
            try { c.close(); } catch (NullPointerException | SQLException e) {}
        }
    }
}