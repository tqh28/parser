package com.ef.common;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class ExecuteQuery {

	// Database credentials
	private static final String USER = "root";
	private static final String PASS = "root";
	private static final String DB_URL = "jdbc:mysql://localhost/parser";
	
	private static Connection connection = null;
	
	public ExecuteQuery() throws SQLException {
		connection = DriverManager.getConnection(DB_URL, USER, PASS);
		connection.setAutoCommit(false);
	}

	public void perform(String query) throws SQLException {
		Statement statement = connection.createStatement();
		statement.executeUpdate(query);
		connection.commit();
		statement.close();
	}
}
