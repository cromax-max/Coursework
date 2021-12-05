package ru.netology.helper;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

import java.sql.*;
import java.util.HashMap;
import java.util.Map;

public class DbHelper {

    private static Connection conn;
    private static Statement stmt;
    static Map<String, String> sqlResult;

    public static void createConnection() {
        try {
            conn = DriverManager.getConnection("jdbc:postgresql://localhost:5432/test-db", "admin", "root");
            stmt = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static Integer getRowCount(String tableName) {
        Integer count = null;
        try {
            var resultSet = stmt.executeQuery("SELECT COUNT(*) FROM " + tableName + ";");
            resultSet.last();
            count = resultSet.getInt(1);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return count;
    }

    public static Map<String, String> getLastRow(String tableName) {
        sqlResult = new HashMap<>();
        try {
            var resultSet = stmt.executeQuery("SELECT * FROM " + tableName + ";");
            var rsMetaData = resultSet.getMetaData();
            for (int i = 1; i <= rsMetaData.getColumnCount(); i++)
                if (resultSet.last()) {
                    sqlResult.put(rsMetaData.getColumnLabel(i), resultSet.getString(i));
                }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return sqlResult;
    }

    public static void closeConnection() {
        try {
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @ToString
    @Getter
    @AllArgsConstructor
    public enum DbTable {

        ORDER_ENTITY("order_entity"),
        PAYMENT_ENTITY("payment_entity"),
        CREDIT_REQUEST_ENTITY("credit_request_entity");

        private final String title;
    }
}
