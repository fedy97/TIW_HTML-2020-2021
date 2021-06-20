
package it.polimi.tiw.controller;

import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;

import javax.servlet.ServletContext;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.thymeleaf.context.WebContext;

import it.polimi.tiw.bean.ArticleBean;
import it.polimi.tiw.bean.OrderBean;
import it.polimi.tiw.bean.ShippingPolicyBean;
import it.polimi.tiw.bean.UserBean;
import it.polimi.tiw.dao.ArticleDAO;
import it.polimi.tiw.dao.ShipmentPolicyDAO;
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

        Optional<UserBean> user = getUserData(req);
        if (!user.isPresent()) {
            resp.sendRedirect(getServletContext().getContextPath() + LOGIN_PAGE_PATH);
            return;
        }

        try {
            ServletContext servletContext = getServletContext();
            final WebContext ctx = new WebContext(req, resp, servletContext, req.getLocale());
            ctx.setVariable(CART_CONTEXT_VAR, buildCartModel(extractArticlesInfo(req.getSession())));
            templateEngine.process(CART_PAGE_PATH, ctx, resp.getWriter());
        } catch (Exception e) {
            log.error("Something went wrong when creating cart. Cause is {}", ExceptionUtils.getStackTrace(e));
            resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR,
                    "Something went wrong when trying to show the cart");
        }

    }

    private Map<String, List<ArticleBean>> extractArticlesInfo(HttpSession session) {

        return (Map<String, List<ArticleBean>>) session.getAttribute(CART_SESSION_VAR);
    }

    private Map<String, OrderBean> buildCartModel(Map<String, List<ArticleBean>> articles) {

        Map<String, OrderBean> orderBeanMap = new HashMap<>();
        articles.forEach((seller, articleList) -> {
            OrderBean orderBean = new OrderBean();
            orderBean.setArticleBeans(articleList);
            orderBean.setSellerId(seller);
            orderBean.setPriceShipment(
                    Float.toString(extractShipmentPrice(seller, computeTotalArticles(articleList).toString())));
            orderBean.setPriceArticles(computePriceArticles(seller, articleList));
            orderBeanMap.put(seller, orderBean);
        });
        return orderBeanMap;
    }

    private String computePriceArticles(String sellerId, List<ArticleBean> articleBeanList) {

        float total = 0F;
        for (ArticleBean articleBean : articleBeanList)
            total += Float.parseFloat(articleBean.getQuantity()) * extractArticlePrice(articleBean.getId(), sellerId);

        return Float.toString(total);
    }

    private Float extractArticlePrice(String articleId, String sellerId) {

        ArticleDAO articleDAO = new ArticleDAO(connection);
        try {
            return articleDAO.getArticlePrice(sellerId, articleId);
        } catch (SQLException e) {
            log.error("Something went wrong when extracting price for article {} of seller {}", articleId, sellerId);
        }
        return 0F;
    }

    private Integer computeTotalArticles(List<ArticleBean> articleBeanList) {

        AtomicInteger counter = new AtomicInteger();
        articleBeanList.forEach(articleBean -> counter.getAndAdd(Integer.parseInt(articleBean.getQuantity())));
        return counter.get();
    }

    private Float extractShipmentPrice(String sellerId, String articleQty) {

        ShipmentPolicyDAO shipmentPolicyDAO = new ShipmentPolicyDAO(connection);
        try {

            Optional<ShippingPolicyBean> shippingPolicy = shipmentPolicyDAO.findPolicyByQty(sellerId,
                    Integer.parseInt(articleQty));
            return shippingPolicy.map(shippingPolicyBean -> Float.parseFloat(shippingPolicyBean.getShipCost()))
                    .orElse(0F);
        } catch (SQLException e) {
            log.error("Something went wrong when extracting shippin policy for seller {}", sellerId);
        }
        return 0F;
    }

}
