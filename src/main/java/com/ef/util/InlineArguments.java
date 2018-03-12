package com.ef.util;

import java.util.HashMap;
import java.util.Map;

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
                String key = arg.substring(2, isCharacterPosition);
                String value = arg.substring(isCharacterPosition + 1);
                argumentsMap.put(key, value);
            }
        }
        
        // check does argumentsMap contain enough required arguments
        if (Common.validateStringIsNullOrEmpty(argumentsMap.get("startDate"))
                || Common.validateStringIsNullOrEmpty(argumentsMap.get("duration"))
                || Common.validateStringIsNullOrEmpty(argumentsMap.get("threshold")) ) {
            return null;
        }
        
        // check type of duration is legal
        String duration = argumentsMap.get("duration");
        if (duration.equalsIgnoreCase(Duration.DAYLY.toString())
                || duration.equalsIgnoreCase(Duration.HOURLY.toString())) {
            return null;
        }
        
        return argumentsMap;
    }
}