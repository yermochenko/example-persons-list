package web;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import domain.Person;

public class ViewPersonListServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
                                         throws ServletException, IOException {
        @SuppressWarnings("unchecked")
        List<Person> persons = (List<Person>)req.getAttribute("persons");
        resp.setCharacterEncoding("UTF-8");
        PrintWriter w = resp.getWriter();
        w.println("<HTML>");
        w.println("<HEAD>");
        w.println("<META http-equiv=\"Content-Type\"");
        w.println("      content=\"text/html; charset=UTF-8\">");
        w.println("<TITLE>Список людей</TITLE>");
        w.println("<STYLE>");
        w.println("TABLE {");
        w.println("border-collapse: collapse;");
        w.println("}");
        w.println("TH, TD {");
        w.println("border: 1px solid black;");
        w.println("padding: 5px 30px 5px 10px;");
        w.println("}");
        w.println("</STYLE>");
        w.println("</HEAD>");
        w.println("<BODY>");
        w.println("<TABLE>");
        w.println("<TR>");
        w.println("<TH>ID</TH>");
        w.println("<TH>Фамилия и инициалы</TH>");
        w.println("<TH>Рост</TH>");
        w.println("<TH>Вес</TH>");
        w.println("<TH>Гражданство</TH>");
        w.println("</TR>");
        for(Person person : persons) {
            w.println("<TR>");
            w.printf("<TD>%02d</TD>", person.getId());
            w.printf(
                "<TD>%s&nbsp;%c.&nbsp;%c.</TD>",
                person.getLastName(),
                person.getFirstName().charAt(0),
                person.getMiddleName().charAt(0)
            );
            w.printf("<TD>%.2f</TD>", person.getHeight());
            w.printf("<TD>%.2f</TD>", person.getWeight());
            w.printf("<TD>%s</TD>", person.isCitizen() ? "Да" : "Нет");
            w.println("</TR>");
        }
        w.println("</TABLE>");
        w.println("</BODY>");
        w.println("</HTML>");
    }
}