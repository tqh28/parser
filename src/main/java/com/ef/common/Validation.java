package com.ef.common;

public class Validation {

    public static boolean validateStringIsNullOrEmpty(String arg) {
        if (arg == null || arg.equals("")) {
            return true;
        }

        return false;
    }
}
