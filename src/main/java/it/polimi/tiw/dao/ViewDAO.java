
package it.polimi.tiw.dao;

import java.sql.Connection;
import java.sql.SQLException;

import it.polimi.tiw.utils.QueryExecutor;
import it.polimi.tiw.utils.ViewEntity;

public class ViewDAO {

    private final QueryExecutor queryExecutor;

    public ViewDAO(Connection connection) {

        queryExecutor = new QueryExecutor(connection);
    }

    public void insertView(ViewEntity view) throws SQLException {

        queryExecutor.insert("user_article", view);
    }

}
