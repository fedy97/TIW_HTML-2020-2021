
package it.polimi.tiw.controller;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringEscapeUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.WebContext;
import org.thymeleaf.templatemode.TemplateMode;
import org.thymeleaf.templateresolver.ServletContextTemplateResolver;

import it.polimi.tiw.bean.ArticleBean;
import it.polimi.tiw.dao.ArticleDAO;
import it.polimi.tiw.utils.ConnectionHandler;

@WebServlet("/search")
public class SearchController extends HttpServlet {

    private static final Logger log               = LoggerFactory.getLogger(SearchController.class.getSimpleName());

    private static final String HINT_ATTRIBUTE    = "hint";
    private static final String RESULTS_PAGE_PATH = "/results.html";

    private static final long   serialVersionUID  = 1L;
    private Connection          connection        = null;
    private TemplateEngine      templateEngine;

    public SearchController() {

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
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {

        String keyword;
        try {
            keyword = StringEscapeUtils.escapeJava(req.getParameter(HINT_ATTRIBUTE));
            if (StringUtils.isBlank(keyword)) {
                throw new Exception("Missing or empty search keyword");
            }

        } catch (Exception e) {
            log.error("Something went wrong when extracting search hint parameters. Cause is {}", e.getMessage());
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Missing or empty search keyword");
            return;
        }

        try {
            log.debug("Search keyword {}", keyword);
            List<ArticleBean> foundArticles = getSearchedArticles(keyword);
            ServletContext servletContext = getServletContext();
            final WebContext ctx = new WebContext(req, resp, servletContext, req.getLocale());
            ctx.setVariable("searchedArticles", foundArticles);
            templateEngine.process(RESULTS_PAGE_PATH, ctx, resp.getWriter());
        } catch (Exception e) {
            log.error("Something went wrong when extracting article by keyword {}. Cause is {}", keyword,
                    e.getMessage());
            resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR,
                    "Something went wrong when searching the specified articles");
        }

    }

    private List<ArticleBean> getSearchedArticles(String keyword) throws SQLException, IllegalAccessException,
            InstantiationException, NoSuchMethodException, InvocationTargetException {

        ArticleDAO articleDAO = new ArticleDAO(connection);
        List<ArticleBean> bean = articleDAO.findArticleByViews("1");
        log.debug("FOUND {}", bean.get(0).toString());
        return articleDAO.findArticleByKeyword(keyword);
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
