package com.ef.constant;

public class Constant {

	public static final String DROP_ACCESS_LOG_TABLE_QUERY = "DROP TABLE IF EXISTS access_log";

	public static final String CREATE_ACCESS_LOG_TABLE_QUERY = "CREATE TABLE access_log(id int PRIMARY KEY AUTO_INCREMENT, access_time datetime(3), ip varchar(16))";
	
	public static final String INSERT_INTO_ACCESS_LOG_PREFIX_QUERY = "INSERT INTO access_log (access_time, ip) VALUES ";

	// Batch size for batch insert
	public static final int UPDATE_BATCH_SIZE = 25000;
}
