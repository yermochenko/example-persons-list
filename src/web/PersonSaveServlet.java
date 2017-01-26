package web;

import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import db.PersonStorage;
import db.StorageCreator;
import domain.Person;
import domain.Sex;

public class PersonSaveServlet extends HttpServlet {
    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("dd.MM.yyyy");

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        Person person = buildPerson(req);
        if(person != null) {
            StorageCreator storageCreator = null;
            try {
                storageCreator = new StorageCreator();
                PersonStorage s = storageCreator.newPersonStorage();
                s.save(person);
            } catch(SQLException e) {
                throw new ServletException(e);
            } finally {
                if(storageCreator != null) {
                    storageCreator.close();
                }
            }
        }
        resp.sendRedirect(req.getContextPath() + "/index.html");
    }

    private Person buildPerson(HttpServletRequest req) {
        try {
            Person person = new Person();
            try {
                person.setId(Integer.parseInt(req.getParameter("id")));
            } catch(NumberFormatException e) {}
            person.setFirstName(req.getParameter("first-name"));
            person.setMiddleName(req.getParameter("middle-name"));
            person.setLastName(req.getParameter("last-name"));
            if(person.getFirstName() == null || person.getMiddleName() == null || person.getLastName() == null) {
                throw new NullPointerException();
            }
            person.setHeight(Double.parseDouble(req.getParameter("height")));
            person.setWeight(Double.parseDouble(req.getParameter("weight")));
            person.setCitizen(req.getParameter("citizen") != null);
            person.setSex(Sex.values()[Integer.parseInt(req.getParameter("sex"))]);
            person.setBirthday(DATE_FORMAT.parse(req.getParameter("birthday")));
            return person;
        } catch(NumberFormatException | ArrayIndexOutOfBoundsException | NullPointerException | ParseException e) {
            return null;
        }
    }
}