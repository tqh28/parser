package com.ef.constant;

public class Constant {

    public static final String LOG_SPLIT_REGEX = "[|]";

    public static final String DROP_ACCESS_LOG_TABLE_QUERY = "DROP TABLE IF EXISTS access_log";

    public static final String CREATE_ACCESS_LOG_TABLE_QUERY = "CREATE TABLE access_log(id int PRIMARY KEY AUTO_INCREMENT, access_time datetime(3), ip varchar(16))";

    public static final String INSERT_INTO_ACCESS_LOG_PREFIX_QUERY = "INSERT INTO access_log (access_time, ip) VALUES ";
    
    public static final String INSERT_INTO_IP_BLOCKED_PREFIX_QUERY = "INSERT INTO ip_blocked (ip, reason) VALUES ";

    public static final String INSERT_INTO_ACCESS_LOG_DATA_FORM = "('%s', '%s'), ";
    
    public static final String INSERT_INTO_IP_BLOCKED_DATA_FORM = "('%s', 'Made %s requests in %s from %s'), ";
    public static final String ONE_DAY = "1 DAY";
    public static final String ONE_HOUR = "1 HOUR";

    public static final String GET_IP_OVER_THRESHOLD_QUERY = "SELECT COUNT(*) sum, ip FROM access_log WHERE access_time < ADDTIME('%s', '%s') and access_time > '%s' GROUP BY ip HAVING COUNT(*) > %s";
    public static final String ADD_ONE_HOUR = "1:0:0.000";
    public static final String ADD_ONE_DAY = "24:0:0.000";

    // Batch size for batch insert
    public static final int UPDATE_BATCH_SIZE = 25000;

    public static final String NO_IP_OVER_THRESHOLD = "There are no ip over threshold";

    public static final String HAVE_IP_OVER_THRESHOLD = "Ip(s) over threshold in given period: ";

    
}
