package web;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import db.ContactStorage;
import db.StorageCreator;
import domain.Contact;

public class ContactDeleteServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        Integer id = null;
        try {
            id = Integer.parseInt(req.getParameter("id"));
        } catch(NumberFormatException e) {}
        Contact contact = null;
        if(id != null) {
            StorageCreator storageCreator = null;
            try {
                storageCreator = new StorageCreator();
                ContactStorage s = storageCreator.newContactStorage();
                contact = s.findById(id);
                s.delete(id);
            } catch(SQLException e) {
                throw new ServletException(e);
            } finally {
                if(storageCreator != null) {
                    storageCreator.close();
                }
            }
        }
        if(contact != null) {
            resp.sendRedirect(req.getContextPath() + "/edit.html?id=" + contact.getPerson().getId());
        } else {
            resp.sendRedirect(req.getContextPath() + "/index.html");
        }
    }
}