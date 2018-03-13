package com.ef.service;

import java.sql.SQLException;
import java.util.Map;
import java.util.Set;

import com.ef.constant.Constant;
import com.ef.enumration.Duration;
import com.ef.intergration.Query;

public class Result {

    public static void writeResult(Query query, Map<String, String> arguments, Map<String, String> overThresholdIps)
            throws SQLException {
        StringBuilder ipBlockedInsertSql = new StringBuilder(Constant.INSERT_INTO_IP_BLOCKED_PREFIX_QUERY);
        String periodTime;
        String time = arguments.get("startDate");
        Set<String> ipSet = overThresholdIps.keySet();
        
        if (ipSet.isEmpty()) { // no ip over threshold
            System.out.println(Constant.NO_IP_OVER_THRESHOLD);
            return;
        }
        
        System.out.println(Constant.HAVE_IP_OVER_THRESHOLD);

        if (Duration.DAILY.toString().equalsIgnoreCase(arguments.get("duration"))) {
            periodTime = Constant.ONE_DAY;
        } else {
            periodTime = Constant.ONE_HOUR;
        }

        for (String ip : ipSet) {
            String sum = overThresholdIps.get(ip);

            // write to console
            System.out.println(ip + ": " + sum + " times.");

            // write to database
            ipBlockedInsertSql
                    .append(String.format(Constant.INSERT_INTO_IP_BLOCKED_DATA_FORM, ip, sum, periodTime, time));
        }

        query.update(ipBlockedInsertSql.substring(0, ipBlockedInsertSql.length() - 2));
    }
}
