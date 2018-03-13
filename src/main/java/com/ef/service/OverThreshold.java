package com.ef.service;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;

import com.ef.constant.Constant;
import com.ef.enumration.Duration;
import com.ef.intergration.Query;

public class OverThreshold {

    public static Map<String, String> getOverThresholds(Query query, String time, String duration, String threshold) throws SQLException {
        String addTime;

        if (Duration.DAILY.toString().equalsIgnoreCase(duration)) {
            addTime = Constant.ADD_ONE_DAY;
        } else if (Duration.HOURLY.toString().equalsIgnoreCase(duration)) {
            addTime = Constant.ADD_ONE_HOUR;
        } else {
            return null;
        }

        String sql = String.format(Constant.GET_IP_OVER_THRESHOLD_QUERY, time, addTime, time, threshold);
        Statement statement = query.getStatement();
        ResultSet resultSet = statement.executeQuery(sql);

        Map<String, String> overThresholds = convertResultSetToMap(resultSet);
        
        statement.close();

        return overThresholds;
    }

    private static Map<String, String> convertResultSetToMap(ResultSet resultSet) throws SQLException {
        Map<String, String> overThresholds = new HashMap<>();
        
        while (resultSet.next()) {
            String ip = resultSet.getString("ip");
            String sum = resultSet.getString("sum");
            
            overThresholds.put(ip, sum);
        }

        return overThresholds;
    }
}
