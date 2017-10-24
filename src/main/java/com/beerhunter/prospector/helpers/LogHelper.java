package com.beerhunter.prospector.helpers;

import cpw.mods.fml.common.FMLLog;
import org.apache.logging.log4j.Level;

public class LogHelper {

    public static void log(Level logLevel, String format, Object... data) {
        FMLLog.log(logLevel, format, data);
    }

    public static void all(String format, Object... data) {
        log(Level.ALL, format, data);
    }

    public static void debug(String format, Object... data) {
        log(Level.DEBUG, format, data);
    }

    public static void error(String format, Object... data) {
        log(Level.ERROR, format, data);
    }

    public static void fatal(String format, Object... data) {
        log(Level.FATAL, format, data);
    }

    public static void info(String format, Object... data) {
        log(Level.INFO, format, data);
    }

    public static void off(String format, Object... data) {
        log(Level.OFF, format, data);
    }

    public static void trace(String format, Object... data) {
        log(Level.TRACE, format, data);
    }

    public static void warn(String format, Object... data) {
        log(Level.WARN, format, data);
    }

}