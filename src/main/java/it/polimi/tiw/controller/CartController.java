
package it.polimi.tiw.controller;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.servlet.ServletContext;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.thymeleaf.context.WebContext;

import it.polimi.tiw.bean.ArticleBean;
import it.polimi.tiw.bean.User;
import it.polimi.tiw.dao.ArticleDAO;
import it.polimi.tiw.utils.CartEntry;
import it.polimi.tiw.utils.GenericServlet;

@WebServlet("/cart")
public class CartController extends GenericServlet {

    private static final Logger log              = LoggerFactory.getLogger(CartController.class.getSimpleName());

    private static final long   serialVersionUID = 1L;

    public CartController() {

        super();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {

        Optional<User> user = getUserData(req);
        if (!user.isPresent()) {
            resp.sendRedirect(getServletContext().getContextPath() + LOGIN_PAGE_PATH);
            return;
        }

        try {
            ServletContext servletContext = getServletContext();
            final WebContext ctx = new WebContext(req, resp, servletContext, req.getLocale());
            ctx.setVariable(CART_CONTEXT_VAR, extractArticlesInfo(getExistingArticles(req.getSession())));
            templateEngine.process(CART_PAGE_PATH, ctx, resp.getWriter());
        } catch (Exception e) {
            log.error("Something went wrong when creating cart. Cause is {}", e.getMessage());
            resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR,
                    "Something went wrong when trying to show the cart");
        }

    }

    private List<ArticleBean> extractArticlesInfo(List<CartEntry> articlesEntries) {

        ArticleDAO articleDAO = new ArticleDAO(connection);
        List<ArticleBean> articles = new ArrayList<>();
        articlesEntries.forEach(entry -> {
            try {
                Optional<ArticleBean> article = articleDAO.findArticleById(entry.getArticleId());
                article.ifPresent(articles::add);
            } catch (SQLException throwables) {
                log.error("Error when retrieving article details of article {}", entry.getArticleId());
            }
        });
        return articles;
    }

    List<CartEntry> getExistingArticles(HttpSession session) {

        Object savedArticles = session.getAttribute(CART_SESSION_VAR);
        if (savedArticles != null) return (List<CartEntry>) savedArticles;
        else
            return new ArrayList<>();
    }

}
