package org.usd232.robotics.powerup;

import java.io.IOException;
import java.io.OutputStream;

public class Printer extends OutputStream {
    private OutputStream parent;
    private LogServer logServer;
    @Override
    public void write(int b) throws IOException {
        write(new byte[] {
                 (byte) b       
        }, 0, 1);
    }
    
    @Override
    public void write(byte[] b, int off, int len) throws IOException {
        logServer.write(b, off, len);
        parent.write(b, off, len);
    }

    @Override
    public void write(byte[] b) throws IOException {
        write(b, 0, b.length);
    }

    public Printer(OutputStream parent, LogServer logServer) {
        this.parent = parent;
        this.logServer = logServer;
    }
}
