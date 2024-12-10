package com.vizor.test.controller;

import lombok.experimental.UtilityClass;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@UtilityClass
public class LogController {
    private static final Logger logger = LoggerFactory.getLogger(LogController.class);

    public static void logInfo(String s, Object o) {
        s = formatString(s);
        logger.info(s, o.getClass());
    }

    public static void logWarn(String s, Object o) {
        s = formatString(s);
        logger.warn(s, o.getClass());
    }

    public static void logError(String s, Object o) {
        s = formatString(s);
        logger.error(s, o.getClass());
    }


    private String formatString(String s) {
        return "{} " + s;
    }
}
