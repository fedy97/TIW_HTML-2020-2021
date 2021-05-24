
package it.polimi.tiw.controller;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import javax.servlet.ServletContext;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringEscapeUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.thymeleaf.context.WebContext;

import it.polimi.tiw.bean.ArticleBean;
import it.polimi.tiw.bean.User;
import it.polimi.tiw.dao.ArticleDAO;
import it.polimi.tiw.utils.GenericServlet;

@WebServlet("/search")
public class SearchController extends GenericServlet {

    private static final Logger log                = LoggerFactory.getLogger(SearchController.class.getSimpleName());

    private static final String HINT_ATTRIBUTE     = "hint";

    private static final String RESULT_CONTEXT_VAR = "searchedArticles";

    private static final String RESULTS_PAGE_PATH  = "/results.html";

    private static final long   serialVersionUID   = 1L;

    public SearchController() {

        super();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {

        Optional<User> user = getUserData(req);
        if (!user.isPresent()) {
            resp.sendRedirect(getServletContext().getContextPath() + LOGIN_PAGE_PATH);
            return;
        }

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
            ctx.setVariable(RESULT_CONTEXT_VAR, foundArticles);
            templateEngine.process(RESULTS_PAGE_PATH, ctx, resp.getWriter());
        } catch (Exception e) {
            log.error("Something went wrong when extracting article by keyword {}. Cause is {}", keyword,
                    e.getMessage());
            resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR,
                    "Something went wrong when searching the specified articles");
        }

    }

    private List<ArticleBean> getSearchedArticles(String keyword) throws SQLException {

        ArticleDAO articleDAO = new ArticleDAO(connection);
        return articleDAO.findArticleByKeyword(keyword);
    }

}
