
package it.polimi.tiw.controller;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import it.polimi.tiw.bean.PersonBean;
import it.polimi.tiw.dao.PersonDAO;

@WebServlet("/PersonsControllerDAO")
public class PersonsControllerDAO extends HttpServlet {

    private static final long serialVersionUID = 1L;
    private Connection        connection       = null;

    public void init()  {

        try {
            ServletContext context = getServletContext();
            String driver = context.getInitParameter("dbDriver");
            String url = context.getInitParameter("dbUrl");
            String user = context.getInitParameter("dbUser");
            String password = context.getInitParameter("dbPassword");
            Class.forName(driver);
            connection = DriverManager.getConnection(url, user, password);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void doGet(HttpServletRequest req, HttpServletResponse res) throws IOException {

        String city = req.getParameter("city");
        PersonDAO personDao = new PersonDAO(connection);
        res.setContentType("text/plain");
        List<PersonBean> persons;
        try {
            persons = personDao.findPersonsByCity(city);
            String path = "/ShowPersons.jsp";
            req.setAttribute("number", persons.size());
            req.setAttribute("persons", persons);
            RequestDispatcher dispatcher = req.getRequestDispatcher(path);
            dispatcher.forward(req, res);
        } catch (

        Exception e) {
            e.printStackTrace();
            res.sendError(500, "Database access failed");
        }
    }

    public void destroy() {

        try {
            if (connection != null) {
                connection.close();
            }
        } catch (SQLException sqle) {
        }
    }
}
