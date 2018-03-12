package com.ef.util;

public class Common {

    public static boolean validateStringIsNullOrEmpty(String arg) {
        if (arg == null || arg.equals("")) {
            return true;
        }
        
        return false;
    }
}
