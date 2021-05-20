
package it.polimi.tiw.dao;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import it.polimi.tiw.bean.ArticleBean;
import it.polimi.tiw.utils.QueryExecutor;

public class ArticleDAO {

    private final QueryExecutor queryExecutor;

    public ArticleDAO(Connection connection) {

        queryExecutor = new QueryExecutor(connection);
    }

    public List<ArticleBean> findArticleByKeyword(String keyword) throws SQLException {

        String query = "SELECT * FROM article WHERE name like :keyword or description like :keyword";
        Map<String, String> queryParam = new HashMap<>();
        queryParam.put("keyword", "%" + keyword + "%");
        return queryExecutor.select(query, queryParam, ArticleBean.class);
    }

    public List<ArticleBean> findArticleByViews(String userId) throws SQLException {

        String query = "SELECT * FROM article a LEFT OUTER JOIN user_article u_a ON a.id=u_a.article_id  WHERE u_a.user_id = :userid LIMIT 5";
        Map<String, String> queryParam = new HashMap<>();
        queryParam.put("userid", userId);
        return queryExecutor.select(query, queryParam, ArticleBean.class);
    }

}
