package com.ef;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class ConnectDatabase {

	// static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
	private static final String DB_URL = "jdbc:mysql://localhost/parser";
	private static final String FILENAME = "C:\\Users\\huytranq2\\Desktop\\Java_MySQL_Test\\access.log";
	private static final int BATCH_SIZE = 25000;

	// Database credentials
	private static final String USER = "root";
	private static final String PASS = "root";

	public static void main(String[] args) {
		Connection conn = null;
		Statement stmt = null;
		try {
			// Class.forName(JDBC_DRIVER);
			conn = DriverManager.getConnection(DB_URL, USER, PASS);
			conn.setAutoCommit(false);
			stmt = conn.createStatement();

			BufferedReader br = new BufferedReader(new FileReader(FILENAME));

			try {
				String currentAccessLogLine;
				String[] logDataArray;

				int rowCount = 0;
				StringBuffer queryBuffer = new StringBuffer("INSERT INTO access_log (access_time, ip) VALUES ");
				while ((currentAccessLogLine = br.readLine()) != null) {
					rowCount++;
					logDataArray = currentAccessLogLine.split("[|]");
					queryBuffer.append("('").append(logDataArray[0]).append("', '").append(logDataArray[1])
							.append("'), ");

					if (rowCount == BATCH_SIZE) { // batch insert
						rowCount = 0;
						stmt.executeUpdate(queryBuffer.substring(0, queryBuffer.length() - 2));
						conn.commit();
						queryBuffer = new StringBuffer("INSERT INTO access_log (access_time, ip) VALUES ");
					}
				}

				// insert rest rows
				stmt.executeUpdate(queryBuffer.substring(0, queryBuffer.length() - 2));
				conn.commit();

				System.out.println("Insert done.");
			} catch (IOException e) {
				e.printStackTrace();
			}

			stmt.close();
			conn.close();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (stmt != null)
					stmt.close();
				if (conn != null)
					conn.close();
			} catch (SQLException se) {
				se.printStackTrace();
			}
		}
	}
}
