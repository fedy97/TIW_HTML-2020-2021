
package it.polimi.tiw.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.servlet.ServletContext;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringEscapeUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.thymeleaf.context.WebContext;

import it.polimi.tiw.bean.UserBean;
import it.polimi.tiw.utils.CartEntry;
import it.polimi.tiw.utils.GenericServlet;

@WebServlet("/add")
public class SaveArticleController extends GenericServlet {

    private static final Logger log                   = LoggerFactory
            .getLogger(SaveArticleController.class.getSimpleName());

    private static final String ARTICLE_ID_FORM_DATA  = "article_id";
    private static final String ARTICLE_QTY_FORM_DATA = "article_qty";

    private static final long   serialVersionUID      = 1L;

    public SaveArticleController() {

        super();
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {

        Optional<UserBean> user = getUserData(req);
        if (!user.isPresent()) {
            resp.sendRedirect(getServletContext().getContextPath() + LOGIN_PAGE_PATH);
            return;
        }

        String articleId;
        Integer qty;
        try {
            articleId = StringEscapeUtils.escapeJava(req.getParameter(ARTICLE_ID_FORM_DATA));
            qty = Integer.parseInt(req.getParameter(ARTICLE_QTY_FORM_DATA));
            if (StringUtils.isBlank(articleId)) {
                throw new Exception("Undefined article id or qty");
            }

        } catch (Exception e) {
            log.error("Something went wrong when extracting article id or qty parameter. Cause is {}", e.getMessage());
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Missing or empty required parameters");
            return;
        }

        try {
            log.debug("Article id {}, qty {}", articleId, qty);
            ServletContext servletContext = getServletContext();
            final WebContext ctx = new WebContext(req, resp, servletContext, req.getLocale());
            List<CartEntry> savedArticles = getExistingArticles(req.getSession());
            savedArticles.add(new CartEntry(articleId, qty));
            req.getSession().setAttribute(CART_SESSION_VAR, savedArticles);
            resp.sendRedirect(getServletContext().getContextPath() + CART_CONTROLLER_PATH);
        } catch (Exception e) {
            log.error("Something went wrong when saving article number {}. Cause is {}", articleId, e.getMessage());
            resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR,
                    "Something went wrong when trying to add the article to the cart");
        }

    }

    List<CartEntry> getExistingArticles(HttpSession session) {

        Object savedArticles = session.getAttribute(CART_SESSION_VAR);
        if (savedArticles != null) return (List<CartEntry>) savedArticles;
        else
            return new ArrayList<>();
    }

}
