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
import domain.Person;
import domain.Type;

public class ContactSaveServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        Contact contact = buildContact(req);
        if(contact != null) {
            StorageCreator storageCreator = null;
            try {
                storageCreator = new StorageCreator();
                ContactStorage s = storageCreator.newContactStorage();
                s.save(contact);
            } catch(SQLException e) {
                throw new ServletException(e);
            } finally {
                if(storageCreator != null) {
                    storageCreator.close();
                }
            }
        }
        resp.sendRedirect(req.getContextPath() + "/edit.html?id=" + contact.getPerson().getId());
    }

    private Contact buildContact(HttpServletRequest req) {
        try {
            Contact contact = new Contact();
            try {
                contact.setId(Integer.parseInt(req.getParameter("id")));
            } catch(NumberFormatException e) {}
            contact.setPerson(new Person());
            contact.getPerson().setId(Integer.parseInt(req.getParameter("person")));
            contact.setType(new Type());
            contact.getType().setId(Integer.parseInt(req.getParameter("type")));
            contact.setValue(req.getParameter("value"));
            if(contact.getValue() == null) {
                throw new NullPointerException();
            }
            return contact;
        } catch(NumberFormatException | NullPointerException e) {
            return null;
        }
    }
}