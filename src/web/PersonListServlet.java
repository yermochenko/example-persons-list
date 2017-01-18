package web;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import db.PersonStorage;
import db.StorageCreator;
import domain.Person;

public class PersonListServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        StorageCreator storageCreator = null;
        try {
            storageCreator = new StorageCreator();
            PersonStorage s = storageCreator.newPersonStorage();
            List<Person> persons = s.readAll();
            req.setAttribute("persons", persons);
            getServletContext().getRequestDispatcher("/WEB-INF/jsp/index.jsp").forward(req, resp);
        } catch(SQLException e) {
            throw new ServletException(e);
        } finally {
            if(storageCreator != null) {
                storageCreator.close();
            }
        }
    }
}