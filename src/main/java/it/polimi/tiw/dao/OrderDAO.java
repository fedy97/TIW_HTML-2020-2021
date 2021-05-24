
package it.polimi.tiw.dao;

import it.polimi.tiw.bean.ArticleBean;
import it.polimi.tiw.bean.OrderBean;
import it.polimi.tiw.utils.QueryExecutor;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class OrderDAO {

    private final QueryExecutor queryExecutor;

    public OrderDAO(Connection connection) {

        queryExecutor = new QueryExecutor(connection);
    }

    public List<OrderBean> findOrderById(String orderId) throws SQLException {
        var query =
                "SELECT O.id, O.seller_id, O.total, O.shipment_date, O.user_id, S.seller_name, S.seller_rating, U.name, U.surname, U.email, U.shipment_addr " +
                        "FROM ecommerce.order O LEFT JOIN ecommerce.seller S " +
                        "on O.seller_id = S.id " +
                        "LEFT JOIN ecommerce.user U " +
                        "on O.user_id = U.id " +
                        "where O.id = :orderId";
        Map<String, String> queryParam = new HashMap<>();
        queryParam.put("orderId", orderId);
        List<OrderBean> result = queryExecutor.select(query, queryParam, OrderBean.class);
        if (result.isEmpty())
            return null;
        result.get(0).setArticleBeans(findArticlesByOrderId(orderId));
        return result;
    }

    private List<ArticleBean> findArticlesByOrderId(String orderId) throws SQLException {
        var query = "SELECT a_o.article_id, a_o.quantity " +
                "FROM ecommerce.order_article a_o " +
                "where a_o.order_id = :orderId";
        Map<String, String> queryParam = new HashMap<>();
        queryParam.put("orderId", orderId);
        return queryExecutor.select(query, queryParam, ArticleBean.class);
    }
}
