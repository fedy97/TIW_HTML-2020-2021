package it.polimi.tiw.dao;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

public class QueryExecutor {

    private final Connection con;

    public QueryExecutor(Connection connection) {
        this.con = connection;
    }

    public static void main(String[] args) throws IllegalAccessException {

        Test test = new Test();
        test.setIda1("a1");
        test.setA2("ad");
        QueryExecutor queryExecutor = new QueryExecutor(null);
        System.out.println(queryExecutor.buildUpdateQuery("table", test));
    }

    public <O> List<O> select(String query, Map<String, String> param, Class<O> clazz) throws SQLException, NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
        List<O> extractedEntities = new ArrayList<>();

        if (param.keySet().stream().filter(key -> query.contains(":" + key)).count() != param.size())
            throw new SQLException("No exact match between the provided parameters and query");

        try (PreparedStatement preparedStatement = con.prepareStatement(query);
             ResultSet result = preparedStatement.executeQuery()) {

            while (result.next()) {
                O record = clazz.getDeclaredConstructor().newInstance();
                Arrays.stream(clazz.getDeclaredFields()).forEach(
                        field -> {
                            try {
                                field.setAccessible(true);
                                field.set(record, result.getString(field.getName()));
                                field.setAccessible(false);
                            } catch (IllegalAccessException | SQLException e) {
                                e.printStackTrace();
                            }
                        }
                );
                extractedEntities.add(record);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return extractedEntities;
    }

    public <I> boolean insert(String tableName, I entity) throws SQLException, NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {

        List<Field> fields = extractObjectFields(entity);
        String query = buildInsertQuery(tableName, fields);

        try (PreparedStatement preparedStatement = con.prepareStatement(query)) {
            AtomicInteger count = new AtomicInteger();

            fields.forEach(field -> {
                try {
                    field.setAccessible(true);
                    preparedStatement.setString(count.incrementAndGet(), (String) field.get(entity));
                    field.setAccessible(false);
                } catch (IllegalAccessException | SQLException e) {
                    e.printStackTrace();
                }
            });
            return preparedStatement.executeUpdate() == 1;
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

    public <I> boolean update(String tableName, I entity) throws IllegalAccessException {

        List<Field> fields = extractObjectFields(entity);
        String query = buildUpdateQuery(tableName, entity);
        Field idField = extractIdField(entity);

        try (PreparedStatement preparedStatement = con.prepareStatement(query)) {
            AtomicInteger count = new AtomicInteger();

            fields.forEach(field -> {
                try {
                    field.setAccessible(true);
                    preparedStatement.setString(count.incrementAndGet(), (String) field.get(entity));
                    field.setAccessible(false);
                } catch (IllegalAccessException | SQLException e) {
                    e.printStackTrace();
                }
            });
            preparedStatement.setString(count.incrementAndGet(), (String) idField.get(entity));
            return preparedStatement.executeUpdate() == 1;
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

    public <I> String buildInsertQuery(String tableName, List<Field> fields) {

        StringBuffer sb = new StringBuffer("INSERT INTO ");
        sb.append(tableName).append(" (");

        fields.forEach(
                field -> sb.append(field.getName()).append(",")
        );
        sb.deleteCharAt(sb.length() - 1).append(") VALUES (");

        fields.forEach(field -> {
            sb.append("?,");
        });
        sb.deleteCharAt(sb.length() - 1).append(");");
        return sb.toString();
    }

    public <I> String buildUpdateQuery(String tableName, I newRecord) throws IllegalAccessException {

        List<Field> fields = extractObjectFields(newRecord);
        StringBuffer sb = new StringBuffer("UPDATE ");
        sb.append(tableName).append(" SET ");

        String idFieldName = extractIdField(newRecord).getName();

        fields.stream().filter(field -> !idFieldName.equals(field.getName())).forEach(
                field -> {
                    try {
                        field.setAccessible(true);
                        if (field.get(newRecord) != null) sb.append(field.getName()).append(" = ?, ");
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    }
                }
        );

        sb.deleteCharAt(sb.length() - 1).append(" WHERE ").append(idFieldName).append(" = ?;");

        return sb.toString();
    }

    private <I> Field extractIdField(I entity) {
        return Arrays.stream(entity.getClass().getDeclaredFields()).
                filter(field -> field.getName().toLowerCase().contains("id")).findFirst().orElseThrow();
    }

    private List<Field> extractObjectFields(Object o) {
        return Arrays.asList(o.getClass().getDeclaredFields());
    }

}
