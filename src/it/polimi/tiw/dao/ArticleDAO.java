
package it.polimi.tiw.dao;

import java.lang.reflect.InvocationTargetException;
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

    public List<ArticleBean> findArticleByKeyword(String keyword) throws SQLException, InvocationTargetException,
            NoSuchMethodException, InstantiationException, IllegalAccessException {

        String query = "SELECT * FROM articles WHERE name like '%:keyword%' or description like '%:keyword%'";
        Map<String, String> queryParam = new HashMap<>();
        queryParam.put("keyword", keyword);
        return queryExecutor.select(query, queryParam, ArticleBean.class);
    }

}
