
package it.polimi.tiw.dao;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import it.polimi.tiw.bean.ArticleBean;
import it.polimi.tiw.utils.QueryExecutor;

public class SellerDAO {

    private final QueryExecutor queryExecutor;

    public SellerDAO(Connection connection) {

        queryExecutor = new QueryExecutor(connection);
    }

    public Optional<ArticleBean> findArticleById(String id) throws SQLException {

        String query = "SELECT * FROM seller WHERE id=:id";
        Map<String, String> queryParam = new HashMap<>();
        queryParam.put("id", id);
        List<ArticleBean> articles = queryExecutor.select(query, queryParam, ArticleBean.class);
        if (articles.size() == 1) return Optional.of(articles.get(0));
        else
            return Optional.empty();
    }

    public List<ArticleBean> findSellerByArticle(String keyword) throws SQLException {

        String query = "SELECT * FROM article WHERE name like :keyword or description like :keyword";
        Map<String, String> queryParam = new HashMap<>();
        queryParam.put("keyword", "%" + keyword + "%");
        return queryExecutor.select(query, queryParam, ArticleBean.class);
    }

}
