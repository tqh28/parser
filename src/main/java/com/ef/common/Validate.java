package com.ef.common;

public class Validate {

    public static boolean validateStringIsNullOrEmpty(String arg) {
        if (arg == null || arg.equals("")) {
            return true;
        }
        
        return false;
    }
}
