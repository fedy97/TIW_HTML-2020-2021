
package it.polimi.tiw.dao;

import it.polimi.tiw.bean.UserArticleBean;
import it.polimi.tiw.bean.UserBean;
import it.polimi.tiw.utils.QueryExecutor;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.*;

public class UserDAO {

    private final QueryExecutor queryExecutor;
    private final Connection connection;

    public UserDAO(Connection connection) {
        this.connection = connection;
        queryExecutor = new QueryExecutor(connection);
    }

    public Optional<UserBean> checkCredentials(String usrn, String pwd) throws SQLException {

        String query = "SELECT  id, email, name, surname FROM user  WHERE email = :usrn AND psw_hash =:pwd";

        Map<String, Object> queryParam = new HashMap<>();
        queryParam.put("usrn", usrn);
        queryParam.put("pwd", pwd);
        try {
            List<UserBean> matchingUserBeans = queryExecutor.select(query, queryParam, UserBean.class);
            if (matchingUserBeans.size() == 1) return Optional.of(matchingUserBeans.get(0));
            else return Optional.empty();
        } catch (Exception e) {
            throw new SQLException(
                    "Something went wrong when executing the query " + query + ". Cause is:" + e.getMessage());
        }
    }

    public void addArticleViewedByUser(String userId, String articleId) throws SQLException {
        String query = "INSERT into ecommerce.user_article (user_id, article_id) VALUES (?, ?)";

        try (PreparedStatement pstatement = connection.prepareStatement(query);) {
            pstatement.setInt(1, Integer.parseInt(userId));
            pstatement.setInt(2, Integer.parseInt(articleId));
            int affectedRows = pstatement.executeUpdate();
        }
    }

    public List<UserArticleBean> getArticlesViewedByUser(String userId) throws SQLException {
        String query = "SELECT * FROM user_article WHERE user_id = :userId";
        Map<String, Object> queryParam = new HashMap<>();
        queryParam.put("userId", userId);
        try {
            List<UserArticleBean> matchingUserArticleBeans = queryExecutor.select(query, queryParam, UserArticleBean.class);
            if (matchingUserArticleBeans.size() > 0) return matchingUserArticleBeans;
            else return new ArrayList<>();
        } catch (Exception e) {
            throw new SQLException(
                    "Something went wrong when executing the query " + query + ". Cause is:" + e.getMessage());
        }
    }
}
