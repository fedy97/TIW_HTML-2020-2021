
package it.polimi.tiw.dao;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import it.polimi.tiw.bean.User;
import it.polimi.tiw.utils.QueryExecutor;

public class UserDAO {

    private final QueryExecutor queryExecutor;

    public UserDAO(Connection connection) {

        queryExecutor = new QueryExecutor(connection);
    }

    public Optional<User> checkCredentials(String usrn, String pwd) throws SQLException {

        String query = "SELECT  id, email, name, surname FROM user  WHERE username = :usrn AND psw_hash =:pwd";

        Map<String, String> queryParam = new HashMap<>();
        queryParam.put("usrn", usrn);
        queryParam.put("pwd", pwd);
        try {
            List<User> matchingUsers = queryExecutor.select(query, queryParam, User.class);
            if (matchingUsers.size() == 1) return Optional.of(matchingUsers.get(0));
            else return Optional.empty();
        } catch (Exception e) {
            throw new SQLException(
                    "Something went wrong when executing the query " + query + ". Cause is:" + e.getMessage());
        }
    }
}
