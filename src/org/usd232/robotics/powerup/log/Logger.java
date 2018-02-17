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
            REAL_STDOUT.printf("[%6s] [%32s] %s%n", level,
                            logger.length() > 32 ? logger.substring(logger.length() - 32, logger.length()) : logger,
                            message);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public void log(LogLevel level, String format, Object... args) {
        log(level, String.format(format, args));
    }

    public void log(LogLevel level, Throwable throwable, String message) {
        log(level, "%s%n%s: %s%n%s", message, throwable.getClass().getName(), throwable.getMessage(),
                        throwable.getStackTrace());
    }

    public void log(LogLevel level, Throwable throwable, String format, Object... args) {
        log(level, throwable, String.format(format, args));
    }

    public void log(LogLevel level, Throwable throwable) {
        log(level, throwable, "An Unhandled Exception has Occured");
    }

    public void trace(String message) {
        log(LogLevel.TRACE, message);
    }

    public void trace(String format, Object... args) {
        log(LogLevel.TRACE, format, args);
    }

    public void trace(Throwable throwable, String message) {
        log(LogLevel.TRACE, throwable, message);
    }

    public void trace(Throwable throwable, String format, Object... args) {
        log(LogLevel.TRACE, throwable, format, args);
    }

    public void trace(Throwable throwable) {
        log(LogLevel.TRACE, throwable);
    }
    
    public void enter(String method) {
        trace("ENTER %s", method);
    }
    
    public void leave(String method) {
        trace("LEAVE %s", method);
    }

    public void debug(String message) {
        log(LogLevel.DEBUG, message);
    }

    public void debug(String format, Object... args) {
        log(LogLevel.DEBUG, format, args);
    }

    public void debug(Throwable throwable, String message) {
        log(LogLevel.DEBUG, throwable, message);
    }

    public void debug(Throwable throwable, String format, Object... args) {
        log(LogLevel.DEBUG, throwable, format, args);
    }

    public void debug(Throwable throwable) {
        log(LogLevel.DEBUG, throwable);
    }

    public void info(String message) {
        log(LogLevel.INFO, message);
    }

    public void info(String format, Object... args) {
        log(LogLevel.INFO, format, args);
    }

    public void info(Throwable throwable, String message) {
        log(LogLevel.INFO, throwable, message);
    }

    public void info(Throwable throwable, String format, Object... args) {
        log(LogLevel.INFO, throwable, format, args);
    }

    public void info(Throwable throwable) {
        log(LogLevel.INFO, throwable);
    }

    public void warn(String message) {
        log(LogLevel.WARN, message);
    }

    public void warn(String format, Object... args) {
        log(LogLevel.WARN, format, args);
    }

    public void warn(Throwable throwable, String message) {
        log(LogLevel.WARN, throwable, message);
    }

    public void warn(Throwable throwable, String format, Object... args) {
        log(LogLevel.WARN, throwable, format, args);
    }

    public void warn(Throwable throwable) {
        log(LogLevel.WARN, throwable);
    }

    public void error(String message) {
        log(LogLevel.ERROR, message);
    }

    public void error(String format, Object... args) {
        log(LogLevel.ERROR, format, args);
    }

    public void error(Throwable throwable, String message) {
        log(LogLevel.ERROR, throwable, message);
    }

    public void error(Throwable throwable, String format, Object... args) {
        log(LogLevel.ERROR, throwable, format, args);
    }

    public void error(Throwable throwable) {
        log(LogLevel.ERROR, throwable);
    }

    public void fatal(String message) {
        log(LogLevel.FATAL, message);
    }

    public void fatal(String format, Object... args) {
        log(LogLevel.FATAL, format, args);
    }

    public void fatal(Throwable throwable, String message) {
        log(LogLevel.FATAL, throwable, message);
    }

    public void fatal(Throwable throwable, String format, Object... args) {
        log(LogLevel.FATAL, throwable, format, args);
    }

    public void fatal(Throwable throwable) {
        log(LogLevel.FATAL, throwable);
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
