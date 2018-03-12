package com.ef;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Map;

import com.ef.common.ExecuteQuery;
import com.ef.constant.Constant;
import com.ef.util.InlineArguments;
import com.ef.util.InsertLogFileToDatabase;


public class Parser {

    public static void main(String[] args) throws SQLException, IOException {
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


        ExecuteQuery executeQuery = new ExecuteQuery();

        // Clean database
        executeQuery.perform(Constant.DROP_ACCESS_LOG_TABLE_QUERY);
        executeQuery.perform(Constant.CREATE_ACCESS_LOG_TABLE_QUERY);

        // Write log file to database
        InsertLogFileToDatabase.execute(executeQuery, "C:\\Users\\huytranq2\\Desktop\\Java_MySQL_Test\\access.log");
            
    }

}
