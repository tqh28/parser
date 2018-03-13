package com.ef.intergration;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class Query {

    // Database credentials
    private static final String USER = "root";
    private static final String PASS = "root";
    private static final String DB_URL = "jdbc:mysql://localhost/parser";

    private static Connection connection = null;

    public Query() throws SQLException {
        connection = DriverManager.getConnection(DB_URL, USER, PASS);
        connection.setAutoCommit(false);
    }

    public void update(String sql) throws SQLException {
        Statement statement = connection.createStatement();
        statement.executeUpdate(sql);
        connection.commit();
        statement.close();
    }

    public Statement getStatement() throws SQLException {
        return connection.createStatement();
    }
}
