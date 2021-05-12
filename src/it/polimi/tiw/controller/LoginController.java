
package it.polimi.tiw.controller;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Optional;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringEscapeUtils;
import org.apache.commons.lang.StringUtils;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.WebContext;
import org.thymeleaf.templatemode.TemplateMode;
import org.thymeleaf.templateresolver.ServletContextTemplateResolver;

import it.polimi.tiw.bean.User;
import it.polimi.tiw.dao.UserDAO;
import it.polimi.tiw.utils.ConnectionHandler;

@WebServlet("/login")
public class LoginController extends HttpServlet {

    private static final String USER_SESSION_ATTRIBUTE = "user";
    private static final String USER_PARAM             = "username";
    private static final String PWD_PARAM              = "pwd";
    private static final String LOGIN_PAGE_PATH        = "/login.html";
    private static final String HOME_PAGE_PATH         = "/home.html";

    private static final long   serialVersionUID       = 1L;
    private Connection          connection             = null;
    private TemplateEngine      templateEngine;

    public LoginController() {

        super();
    }

    @Override
    public void init() throws ServletException {

        connection = ConnectionHandler.getConnection(getServletContext());
        ServletContext servletContext = getServletContext();
        ServletContextTemplateResolver templateResolver = new ServletContextTemplateResolver(servletContext);
        templateResolver.setTemplateMode(TemplateMode.HTML);
        this.templateEngine = new TemplateEngine();
        this.templateEngine.setTemplateResolver(templateResolver);
        templateResolver.setSuffix(".html");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {

        String usrn;
        String pwd;
        try {
            usrn = StringEscapeUtils.escapeJava(request.getParameter(USER_PARAM));
            pwd = StringEscapeUtils.escapeJava(request.getParameter(PWD_PARAM));

            if (StringUtils.isBlank(usrn) || StringUtils.isBlank(pwd)) {
                throw new Exception("Missing or empty credential value");
            }

        } catch (Exception e) {
            // for debugging only e.printStackTrace();
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Missing credential value");
            return;
        }

        try {
            Optional<User> user = getUserEntity(usrn, pwd);
            if (!user.isPresent()) {
                ServletContext servletContext = getServletContext();
                final WebContext ctx = new WebContext(request, response, servletContext, request.getLocale());
                ctx.setVariable("errorMsg", "Incorrect username or password");
                templateEngine.process(LOGIN_PAGE_PATH, ctx, response.getWriter());
            } else {
                request.getSession().setAttribute(USER_SESSION_ATTRIBUTE, user.get());
                response.sendRedirect(getServletContext().getContextPath() + HOME_PAGE_PATH);
            }
        } catch (SQLException e) {
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Not Possible to check credentials");
        }
    }

    private Optional<User> getUserEntity(String usrn, String pwd) throws SQLException {

        UserDAO userDao = new UserDAO(connection);
        return userDao.checkCredentials(usrn, pwd);
    }

    @Override
    public void destroy() {

        try {
            ConnectionHandler.closeConnection(connection);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
