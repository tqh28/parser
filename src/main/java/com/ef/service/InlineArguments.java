package com.ef.service;

import java.util.HashMap;
import java.util.Map;

import com.ef.common.Validation;
import com.ef.enumration.Duration;

public class InlineArguments {

    public static Map<String, String> convertArgumentsToMap(String... args) {
        Map<String, String> argumentsMap = new HashMap<String, String>();
        
        // convert arguments from String array to Map
        for (String arg: args) {
            int isCharacterPosition = arg.indexOf("=");
            if (isCharacterPosition < 0) {
                // is not existed
                break;
            } else {
                String key = arg.substring(2, isCharacterPosition); // 2 because remove -- before argument
                String value = arg.substring(isCharacterPosition + 1); // +1 is after = character
                argumentsMap.put(key, value);
            }
        }
        
        if (! validateArguments(argumentsMap)) {
            return null;
        }
        return argumentsMap;
    }
    
    private static boolean validateArguments(Map<String, String> argumentsMap) {
        // start date
        if (Validation.validateStringIsNullOrEmpty(argumentsMap.get("startDate"))) {
            System.out.println("startDate is not found!");
            return false;
        } else {
            // TODO
        }
        
        // duration 
        if (Validation.validateStringIsNullOrEmpty(argumentsMap.get("duration"))) {
            System.out.println("duration is not found!");
            return false;
        } else {
            String duration = argumentsMap.get("duration");
            if (!duration.equalsIgnoreCase(Duration.DAILY.toString())
                    && !duration.equalsIgnoreCase(Duration.HOURLY.toString())) {
                return false;
            }
        }
        
        // threshold
        if (Validation.validateStringIsNullOrEmpty(argumentsMap.get("threshold"))) {
            System.out.println("threshold is not found!");
            return false;
        } else {
            try {
                Integer.parseInt(argumentsMap.get("threshold"));
            } catch (IllegalArgumentException e) {
                System.out.println("threshold is not a Integer!");
                return false;
            }
        }
        
        // file path
        if (Validation.validateStringIsNullOrEmpty(argumentsMap.get("accesslog"))) {
            System.out.println("accesslog is not found!");
            return false;
        }
        
        return true;
    }
}
