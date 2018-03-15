package com.ef.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.sql.SQLException;

import com.ef.constant.Constant;
import com.ef.intergration.Query;
import com.ef.intergration.File;

public class LogFile {

	public static void writeLogFileToDatabase(Query executeQuery, String filePath) throws SQLException, IOException {
        BufferedReader br = File.getFileBufferReader(filePath);
        String currentAccessLogLine;
        String[] logDataArray;

        int rowCount = 0; // count for batch size
        StringBuffer queryBuffer = new StringBuffer(Constant.INSERT_INTO_ACCESS_LOG_PREFIX_QUERY);
        while ((currentAccessLogLine = br.readLine()) != null) {
            rowCount++;
            logDataArray = currentAccessLogLine.split(Constant.LOG_SPLIT_REGEX);
            
            String logTime = logDataArray[0];
            String logIp = logDataArray[1];
            queryBuffer.append(String.format(Constant.INSERT_INTO_ACCESS_LOG_DATA_FORM, logTime, logIp));

            if (rowCount == Constant.UPDATE_BATCH_SIZE) { // batch insert
                rowCount = 0;
                executeQuery.update(queryBuffer.substring(0, queryBuffer.length() - 2)); // remove ', ' in last of query string
                queryBuffer = new StringBuffer(Constant.INSERT_INTO_ACCESS_LOG_PREFIX_QUERY);
            }
        }

        // insert rest rows
        executeQuery.update(queryBuffer.substring(0, queryBuffer.length() - 2)); // remove ', ' in last of query string
	}
}
