package org.usd232.robotics.powerup.log;

import java.io.IOException;
import java.io.PrintStream;
import java.io.UnsupportedEncodingException;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import javax.net.ServerSocketFactory;

public class LogServer implements Runnable {
    private static final Logger          LOG        = new Logger();
    private static final int             PORT_NUM   = 5800;
    private final List<Socket>           socketList = new LinkedList<Socket>();
    private static final List<LogServer> servers    = new ArrayList<LogServer>();

    static void emit(byte[] buffer) throws IOException {
        for (LogServer server : servers) {
            server.write(buffer);
        }
    }
    
    static byte[] serialize(byte[] b, int off, int len, long date, LogLevel level, String logger) throws IOException {
        byte[] loggerBytes = logger.getBytes("UTF-8");
        byte[] buffer = new byte[4 + len + 8 + 1 + 4 + loggerBytes.length];
        ByteBuffer buf = ByteBuffer.wrap(buffer);
        buf.putInt(len);
        buf.put(b, off, len);
        buf.putLong(date);
        buf.put((byte) level.getLevelId());
        buf.putInt(loggerBytes.length);
        buf.put(loggerBytes);
        return buffer;
    }

    public void write(byte[] buffer) throws IOException {
        for (Socket socket : socketList) {
            socket.getOutputStream().write(buffer);
            socket.getOutputStream().flush();
        }
    }

    @Override
    public void run() {
        try {
            System.setOut(new PrintStream(new Printer(System.out, this, LogLevel.STDOUT), false, "UTF-8"));
        } catch (UnsupportedEncodingException ex) {
            ex.printStackTrace();
            System.setOut(new PrintStream(new Printer(System.out, this, LogLevel.STDOUT), false));
        }
        try {
            System.setErr(new PrintStream(new Printer(System.err, this, LogLevel.STDERR), false, "UTF-8"));
        } catch (UnsupportedEncodingException ex) {
            ex.printStackTrace();
            System.setErr(new PrintStream(new Printer(System.err, this, LogLevel.STDERR), false));
        }
        ServerSocketFactory serverSocketFactory = ServerSocketFactory.getDefault();
        ServerSocket serverSocket = null;
        try {
            serverSocket = serverSocketFactory.createServerSocket(PORT_NUM);
        } catch (IOException ignored) {
            System.err.println("Unable to create server");
            System.exit(-1);
        }
        LOG.info("LogServer running on port %s", PORT_NUM);
        while (true) {
            Socket socket = null;
            try {
                socket = serverSocket.accept();
                socketList.add(socket);
                LOG.info("Socket at %s has successfully connected.", socket.getInetAddress());
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }
    
    public LogServer() {
        servers.add(this);
    }

    public static void main(String[] args) {
        new LogServer().run();
    }
}