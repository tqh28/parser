package com.ef;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Map;

import com.ef.util.Common;
import com.ef.util.InlineArguments;

/**
 * Hello world!
 *
 */
public class Parser {

    static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
    private static final String DB_URL = "jdbc:mysql://localhost/parser";
    private static final String FILENAME = "/Users/macintosh/Documents/java-workspace/parser/src/main/resource/access.log";

    // Batch size for batch insert
    private static final int BATCH_SIZE = 25000;

    // Database credentials
    private static final String USER = "root";
    private static final String PASS = "root";

    public static void main(String[] args) {
        // System.out.println( "Hello World!" );
        for (String arg : args) {
            System.out.println(arg);
        }
        
        Map<String, String> arguments = InlineArguments.convertArgumentsToMap(args);
        if (arguments == null) {
            System.out.println("Illegal arguments");
        } else {
            System.out.println(arguments.get("startDate"));
            System.out.println(arguments.get("duration"));
            System.out.println(arguments.get("threshold"));
        }

        /* READY */
        Connection conn = null;
        Statement stmt = null;
        try {

            // create database connection
            Class.forName(JDBC_DRIVER);
            conn = DriverManager.getConnection(DB_URL, USER, PASS);
            conn.setAutoCommit(false);
            stmt = conn.createStatement();

            // create file buffer reader
            BufferedReader br = new BufferedReader(new FileReader(FILENAME));

            try {

                stmt.executeUpdate("DROP TABLE IF EXISTS access_log");
                conn.commit();

                stmt.executeUpdate(
                        "create table access_log(id int PRIMARY KEY AUTO_INCREMENT, access_time datetime(3), ip varchar(16))");
                conn.commit();

                String currentAccessLogLine;
                String[] logDataArray;

                int rowCount = 0; // count for batch size
                StringBuffer queryBuffer = new StringBuffer("INSERT INTO access_log (access_time, ip) VALUES "); //
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
