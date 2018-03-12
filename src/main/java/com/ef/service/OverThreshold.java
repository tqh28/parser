package com.ef.service;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;

import com.ef.constant.Constant;
import com.ef.enumration.Duration;
import com.ef.intergration.Query;

public class OverThreshold {

	private Query query;

	public OverThreshold(Query query) {
		this.query = query;
	}

	public Map<String, Integer> getOverThreshold(String time, String duration, String threshold) throws SQLException {
		String addTime;

		if (Duration.DAYLY.toString().equalsIgnoreCase(duration)) {
			addTime = Constant.ADD_ONE_DAY;
		} else if (Duration.HOURLY.toString().equalsIgnoreCase(duration)) {
			addTime = Constant.ADD_ONE_HOUR;
		} else {
			return null;
		}

		String sql = String.format(Constant.GET_IP_OVER_THRESHOLD_QUERY, time, addTime, time, threshold);
		ResultSet resultSet = query.get(sql);
		
//		return convertResultSetToMap(resultSet);
		
		while (resultSet.next()) {
			
			int ip = resultSet.getInt("ip");
			int sum = resultSet.getInt("sum");
			
			

			// Display values
			System.out.print("IP: " + ip);
			System.out.print(", sum: " + sum);
		}
		
		return null;

	}

	private Map<String, Integer> convertResultSetToMap(ResultSet resultSet) throws SQLException {
		while (resultSet.next()) {
			
			int ip = resultSet.getInt("ip");
			int sum = resultSet.getInt("sum");
			
			

			// Display values
			System.out.print("IP: " + ip);
			System.out.print(", sum: " + sum);
		}
		
		return null;
	}
}
