package com.ef.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.sql.SQLException;

import com.ef.common.ExecuteQuery;
import com.ef.common.ReadFile;
import com.ef.constant.Constant;

public class InsertLogFileToDatabase {

	public static void execute(ExecuteQuery executeQuery, String filePath) throws SQLException, IOException {
        BufferedReader br = ReadFile.getFileBufferReader(filePath);
        String currentAccessLogLine;
        String[] logDataArray;

        int rowCount = 0; // count for batch size
        StringBuffer queryBuffer = new StringBuffer(Constant.INSERT_INTO_ACCESS_LOG_PREFIX_QUERY); //
        while ((currentAccessLogLine = br.readLine()) != null) {
            rowCount++;
            logDataArray = currentAccessLogLine.split("[|]");
            queryBuffer.append("('").append(logDataArray[0]).append("', '").append(logDataArray[1])
                    .append("'), ");

            if (rowCount == Constant.UPDATE_BATCH_SIZE) { // batch insert
                rowCount = 0;
                executeQuery.perform(queryBuffer.substring(0, queryBuffer.length() - 2));
                queryBuffer = new StringBuffer(Constant.INSERT_INTO_ACCESS_LOG_PREFIX_QUERY);
            }
        }

        // insert rest rows
        executeQuery.perform(queryBuffer.substring(0, queryBuffer.length() - 2));

        System.out.println("Insert done.");
	}
}
