package web;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import db.StorageCreator;
import db.TypeStorage;
import domain.Type;

public class TypeListServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        StorageCreator storageCreator = null;
        try {
            storageCreator = new StorageCreator();
            TypeStorage s = storageCreator.newTypeStorage();
            List<Type> types = s.readAll();
            req.setAttribute("types", types);
            getServletContext().getRequestDispatcher("/WEB-INF/jsp/type/index.jsp").forward(req, resp);
        } catch(SQLException e) {
            throw new ServletException(e);
        } finally {
            if(storageCreator != null) {
                storageCreator.close();
            }
        }
    }
}