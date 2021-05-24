package it.polimi.tiw.dao;

import it.polimi.tiw.bean.PersonBean;
import it.polimi.tiw.utils.QueryExecutor;

import java.lang.reflect.InvocationTargetException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class PersonDAO {

    private final QueryExecutor queryExecutor;

    public PersonDAO(Connection connection) {
        queryExecutor = new QueryExecutor(connection);
    }

    public List<PersonBean> findPersonsByCity(String city) throws SQLException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {

        String query = "SELECT * FROM person WHERE city = :city";
        Map<String, String> queryParam = new HashMap<>();
        queryParam.put("city", city);
        return queryExecutor.select(query, queryParam, PersonBean.class);
    }

}
