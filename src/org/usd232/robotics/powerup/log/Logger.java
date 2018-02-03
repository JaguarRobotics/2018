package org.usd232.robotics.powerup.log;

import java.io.IOException;
import java.io.PrintStream;
import java.util.Arrays;

public class Logger {
    private static final String[]    EXCLUDED_CLASSES = { Logger.class.getName(), Thread.class.getName() };
    private static final PrintStream REAL_STDOUT      = System.out;
    private final String             logger;

    public void log(LogLevel level, String message) {
        try {
            byte[] buf = message.getBytes("utf8");
            LogServer.emit(LogServer.serialize(buf, 0, buf.length, System.currentTimeMillis(), level, logger));
            REAL_STDOUT.printf("[%s] %s%n", level, message);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public void log(LogLevel level, String format, Object... args) {
        log(level, String.format(format, args));
    }

    public void trace(String message) {
        log(LogLevel.TRACE, message);
    }

    public void trace(String format, Object... args) {
        log(LogLevel.TRACE, format, args);
    }

    public void debug(String message) {
        log(LogLevel.DEBUG, message);
    }

    public void debug(String format, Object... args) {
        log(LogLevel.DEBUG, format, args);
    }

    public void info(String message) {
        log(LogLevel.INFO, message);
    }

    public void info(String format, Object... args) {
        log(LogLevel.INFO, format, args);
    }

    public void warn(String message) {
        log(LogLevel.WARN, message);
    }

    public void warn(String format, Object... args) {
        log(LogLevel.WARN, format, args);
    }

    public void error(String message) {
        log(LogLevel.ERROR, message);
    }

    public void error(String format, Object... args) {
        log(LogLevel.ERROR, format, args);
    }

    public void fatal(String message) {
        log(LogLevel.FATAL, message);
    }

    public void fatal(String format, Object... args) {
        log(LogLevel.FATAL, format, args);
    }

    public String getName() {
        return logger;
    }

    private static String getLogger() {
        for (StackTraceElement element : Thread.currentThread().getStackTrace()) {
            if (Arrays.binarySearch(EXCLUDED_CLASSES, element.getClassName()) < 0) {
                return element.getClassName();
            }
        }
        throw new RuntimeException("Logger cannot be the only class on the stack");
    }

    static {
        Arrays.sort(EXCLUDED_CLASSES);
    }

    public Logger(String logger) {
        this.logger = logger;
    }

    public Logger(Class<?> logger) {
        this(logger.getName());
    }

    public Logger() {
        this(getLogger());
    }
}
