package web;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import db.StorageCreator;
import db.TypeStorage;
import domain.Type;

public class TypeSaveServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        Type type = buildType(req);
        if(type != null) {
            StorageCreator storageCreator = null;
            try {
                storageCreator = new StorageCreator();
                TypeStorage s = storageCreator.newTypeStorage();
                s.save(type);
            } catch(SQLException e) {
                throw new ServletException(e);
            } finally {
                if(storageCreator != null) {
                    storageCreator.close();
                }
            }
        }
        resp.sendRedirect(req.getContextPath() + "/type/index.html");
    }

    private Type buildType(HttpServletRequest req) {
        try {
            Type type = new Type();
            try {
                type.setId(Integer.parseInt(req.getParameter("id")));
            } catch(NumberFormatException e) {}
            type.setName(req.getParameter("name"));
            if(type.getName() == null) {
                throw new NullPointerException();
            }
            return type;
        } catch(NullPointerException e) {
            return null;
        }
    }
}