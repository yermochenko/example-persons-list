package web;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import db.ContactStorage;
import db.PersonStorage;
import db.StorageCreator;
import db.TypeStorage;
import domain.Contact;
import domain.Person;
import domain.Type;

public class ContactEditServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        StorageCreator storageCreator = null;
        try {
            storageCreator = new StorageCreator();
            TypeStorage ts = storageCreator.newTypeStorage();
            List<Type> types = ts.readAll();
            req.setAttribute("types", types);
            Integer id = null;
            try {
                id = Integer.parseInt(req.getParameter("id"));
            } catch(NumberFormatException e) {}
            if(id != null) {
                ContactStorage cs = storageCreator.newContactStorage();
                Contact contact = cs.findById(id);
                req.setAttribute("contact", contact);
            } else {
                try {
                    id = Integer.parseInt(req.getParameter("person"));
                } catch(NumberFormatException e) {}
                if(id != null) {
                    PersonStorage ps = storageCreator.newPersonStorage();
                    Person person = ps.readById(id);
                    req.setAttribute("person", person);
                }
            }
            getServletContext().getRequestDispatcher("/WEB-INF/jsp/contact/edit.jsp").forward(req, resp);
        } catch(SQLException e) {
            throw new ServletException(e);
        } finally {
            if(storageCreator != null) {
                storageCreator.close();
            }
        }
    }
}