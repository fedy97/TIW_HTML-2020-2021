
package it.polimi.tiw.controller;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import javax.servlet.ServletContext;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import it.polimi.tiw.bean.SellerBean;
import it.polimi.tiw.dao.SellerDAO;
import org.apache.commons.lang3.StringEscapeUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.thymeleaf.context.WebContext;

import it.polimi.tiw.bean.ArticleBean;
import it.polimi.tiw.bean.UserBean;
import it.polimi.tiw.dao.ArticleDAO;
import it.polimi.tiw.utils.GenericServlet;

@WebServlet("/search")
public class SearchController extends GenericServlet {

    private static final Logger log                  = LoggerFactory.getLogger(SearchController.class.getSimpleName());

    private static final String HINT_ATTRIBUTE       = "hint";
    private static final String ARTICLE_ID_ATTRIBUTE = "article_id";

    private static final String RESULT_CONTEXT_VAR   = "searchedArticles";
    private static final String HINT_CONTEXT_VAR     = "hint";

    private static final String RESULTS_PAGE_PATH    = "/results.html";

    private static final long   serialVersionUID     = 1L;

    public SearchController() {

        super();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {

        Optional<UserBean> user = getUserData(req);
        if (!user.isPresent()) {
            resp.sendRedirect(getServletContext().getContextPath() + LOGIN_PAGE_PATH);
            return;
        }

        String keyword;
        String articleId;
        try {
            keyword = StringEscapeUtils.escapeJava(req.getParameter(HINT_ATTRIBUTE));
            articleId =  req.getParameter(ARTICLE_ID_ATTRIBUTE);
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
            ctx.setVariable(HINT_CONTEXT_VAR, keyword);
            if(StringUtils.isNotBlank(articleId)){

            }

            templateEngine.process(RESULTS_PAGE_PATH, ctx, resp.getWriter());
        } catch (Exception e) {
            log.error("Something went wrong when extracting article by keyword {}. Cause is {}", keyword,
                    e.getMessage());
            resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR,
                    "Something went wrong when searching the specified articles");
        }

    }

    private void addArticleDetails(String articleId, WebContext ctx) throws SQLException {
        SellerDAO sellerDAO = new SellerDAO(connection);
        List<SellerBean> articleSellers = sellerDAO.findSellerByArticleId(articleId);

    }

    private List<ArticleBean> getSearchedArticles(String keyword) throws SQLException {

        ArticleDAO articleDAO = new ArticleDAO(connection);
        return articleDAO.findArticleByKeyword(keyword);
    }

}
