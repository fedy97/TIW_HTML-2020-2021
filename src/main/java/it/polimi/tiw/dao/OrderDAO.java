
package it.polimi.tiw.dao;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import it.polimi.tiw.bean.ArticleBean;
import it.polimi.tiw.bean.OrderBean;
import it.polimi.tiw.utils.QueryExecutor;

public class OrderDAO {

    private final QueryExecutor queryExecutor;

    public OrderDAO(Connection connection) {

        queryExecutor = new QueryExecutor(connection);
    }

    public List<OrderBean> findOrderById(String orderId) throws SQLException {

        String query = "SELECT O.id, O.seller_id, O.total, O.shipment_date, O.order_date, O.user_id, S.seller_name, S.seller_rating, U.name, U.surname, U.email, U.shipment_addr "
                + "FROM ecommerce.order O LEFT JOIN ecommerce.seller S " + "on O.seller_id = S.id "
                + "LEFT JOIN ecommerce.user U " + "on O.user_id = U.id " + "where O.id = :orderId";
        Map<String, String> queryParam = new HashMap<>();
        queryParam.put("orderId", orderId);
        List<OrderBean> result = queryExecutor.select(query, queryParam, OrderBean.class);
        if (result.isEmpty()) return new ArrayList<>();
        result.get(0).setArticleBeans(findArticlesByOrderId(orderId, result.get(0).getSellerId()));
        return result;
    }

    public List<OrderBean> findOrders(String userId) throws SQLException {

        String query = "SELECT O.id, O.seller_id, O.total, O.shipment_date, O.order_date, O.user_id, S.seller_name, S.seller_rating, U.name, U.surname, U.email, U.shipment_addr "
                + "FROM ecommerce.order O LEFT JOIN ecommerce.seller S " + "on O.seller_id = S.id "
                + "LEFT JOIN ecommerce.user U " + "on O.user_id = U.id " + "where O.user_id = :userId "
                + "ORDER BY O.order_date DESC";
        Map<String, String> queryParam = new HashMap<>();
        queryParam.put("userId", userId);
        List<OrderBean> result = queryExecutor.select(query, queryParam, OrderBean.class);
        for (OrderBean orderBean : result)
            orderBean.setArticleBeans(findArticlesByOrderId(orderBean.getId(), orderBean.getSellerId()));
        return result;
    }

    private List<ArticleBean> findArticlesByOrderId(String orderId, String sellerId) throws SQLException {

        String query = "SELECT A.id, A.name, A.description, A.category, A.photo, A_O.quantity, S_A.price "
                + "FROM ecommerce.article A LEFT JOIN ecommerce.order_article A_O " + "on A.id = A_O.article_id "
                + "LEFT JOIN ecommerce.seller_article S_A " + "on A.id = S_A.article_id "
                + "where A_O.order_id = :orderId AND S_A.seller_id = :sellerId";
        Map<String, String> queryParam = new HashMap<>();
        queryParam.put("sellerId", sellerId);
        queryParam.put("orderId", orderId);
        return queryExecutor.select(query, queryParam, ArticleBean.class);
    }
}
