package com.ef;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Map;

import com.ef.constant.Constant;
import com.ef.intergration.Query;
import com.ef.service.InlineArguments;
import com.ef.service.LogFile;
import com.ef.service.OverThreshold;
import com.ef.service.Result;

public class Parser {

    public static void main(String[] args) throws SQLException {

        Map<String, String> arguments = InlineArguments.convertArgumentsToMap(args);
        Query query = new Query();

        // are arguments legal?
        if (arguments == null) {
            return;
        }

        // Clean database
        query.update(Constant.DROP_ACCESS_LOG_TABLE_QUERY);
        query.update(Constant.CREATE_ACCESS_LOG_TABLE_QUERY);

        // Write log file to database
        try {
            LogFile.writeLogFileToDatabase(query, arguments.get("accesslog"));
        } catch (IOException e) {
            System.out.println("File not found!");
        }

        Map<String, String> overThresholdIps = OverThreshold.getOverThresholds(query, arguments.get("startDate"),
                arguments.get("duration"), arguments.get("threshold"));

        Result.writeResult(query, arguments, overThresholdIps);

    }

}
