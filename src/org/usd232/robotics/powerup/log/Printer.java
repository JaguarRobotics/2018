package org.usd232.robotics.powerup.log;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
import java.util.Arrays;

public class Printer extends OutputStream {
    private static final Logger   LOG              = new Logger();
    private static final String[] EXCLUDED_CLASSES = { Printer.class.getName(), PrintStream.class.getName() };
    private OutputStream          parent;
    private LogServer             logServer;
    private LogLevel              level;

    @Override
    public void write(int b) throws IOException {
        write(new byte[] { (byte) b }, 0, 1);
    }

    @Override
    public void write(byte[] b, int off, int len) throws IOException {
        try {
            parent.write(b, off, len);
            StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();
            int i;
            for (i = stackTrace.length - 1; i >= 0
                            && Arrays.binarySearch(EXCLUDED_CLASSES, stackTrace[i].getClassName()) < 0; --i);
            logServer.write(LogServer.serialize(b, off, len, System.currentTimeMillis(), level,
                            stackTrace[i + 1].getClassName()));
        } catch (Throwable t) {
            LOG.uncaught(t);
            if (t instanceof IOException) {
                throw (IOException) t;
            }
        }
    }

    @Override
    public void write(byte[] b) throws IOException {
        write(b, 0, b.length);
    }

    static {
        LOG.catchAll(()-> {
            Arrays.sort(EXCLUDED_CLASSES);
        });
    }

    public Printer(OutputStream parent, LogServer logServer, LogLevel level) {
        this.parent = parent;
        this.logServer = logServer;
        this.level = level;
    }
}
