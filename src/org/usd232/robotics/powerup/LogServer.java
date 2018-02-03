package org.usd232.robotics.powerup;

import java.io.IOException;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.LinkedList;
import java.util.List;
import javax.net.ServerSocketFactory;

public class LogServer implements Runnable {
    private static final int PORT_NUM   = 5800;
    private List<Socket>     socketList = new LinkedList<Socket>();

    public void write(byte[] b, int off, int len) throws IOException {
        for (Socket socket : socketList) {
            socket.getOutputStream().write(b, off, len);
            socket.getOutputStream().flush();
        }
    }

    @Override
    public void run() {
        System.setOut(new PrintStream(new Printer(System.out, this)));
        System.setErr(new PrintStream(new Printer(System.err, this)));
        ServerSocketFactory serverSocketFactory = ServerSocketFactory.getDefault();
        ServerSocket serverSocket = null;
        try {
            serverSocket = serverSocketFactory.createServerSocket(PORT_NUM);
        } catch (IOException ignored) {
            System.err.println("Unable to create server");
            System.exit(-1);
        }
        System.out.printf("LogServer running on port: %s%n", PORT_NUM);
        while (true) {
            Socket socket = null;
            try {
                socket = serverSocket.accept();
                socketList.add(socket);
                System.out.println("Socket at " + socket.getInetAddress() + " Has Successfully Connected.");
            } catch (IOException exception) {
            } finally {
                if (socket != null) {
                }
            }
        }
    }
}