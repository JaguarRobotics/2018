package org.usd232.robotics.robot.minimap;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.nio.ByteBuffer;

public class MinimapCoordsClient extends Thread {
    private static final int      TEAM_NUMBER     = 1810;
    private static final String[] ROBOT_ADDRESSES = { String.format("10.%d.%d.2", TEAM_NUMBER / 100, TEAM_NUMBER % 100),
                    "172.22.11.2", String.format("roboRIO-%d-FRC.lan", TEAM_NUMBER),
                    String.format("roboRIO-%d-FRC.frc-field.local", TEAM_NUMBER),
                    String.format("roboRIO-%d-FRC.local", TEAM_NUMBER) };
    private static final int      PORT            = 5801;
    private static final long     CONNECT_TIMEOUT = 10000;
    private static final long     TIMEOUT         = 10000;
    private double                robotX          = 0;
    private double                robotY          = 0;
    private double                robotAngle      = 0;
    private DatagramSocket        socket;

    public MinimapCoordsClient() throws IOException {
        connect();
        Thread connectThread = new Thread(()-> {
            try {
                while (true) {
                    Thread.sleep(TIMEOUT);
                    try {
                        System.out.println("Reconnecting...");
                        connect();
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                }
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }
        });
        connectThread.start();
    }

    private void connect() throws IOException {
        if (socket != null) {
            socket.close();
            socket = null;
        }
        Thread[] threads = new Thread[ROBOT_ADDRESSES.length];
        DatagramSocket[] socks = new DatagramSocket[ROBOT_ADDRESSES.length];
        byte[] greeting = "Hello".getBytes();
        Thread updater = new Thread(()-> {
            try {
                while (true) {
                    for (DatagramSocket sock : socks) {
                        if (sock != null) {
                            try {
                                sock.send(new DatagramPacket(greeting, greeting.length));
                            } catch (IOException ex) {
                                ex.printStackTrace();
                            }
                        }
                    }
                    Thread.sleep(CONNECT_TIMEOUT);
                }
            } catch (InterruptedException ex) {
            }
        });
        for (int i = 0; i < ROBOT_ADDRESSES.length; ++i) {
            InetAddress addr;
            try {
                addr = InetAddress.getByName(ROBOT_ADDRESSES[i]);
            } catch (UnknownHostException ex) {
                continue;
            }
            socks[i] = new DatagramSocket();
            socks[i].connect(addr, PORT);
            socks[i].send(new DatagramPacket(greeting, greeting.length));
            int j = i;
            threads[i] = new Thread(()-> {
                byte[] inbuf = new byte[5];
                DatagramPacket pkt = new DatagramPacket(inbuf, inbuf.length);
                try {
                    while (true) {
                        try {
                            socks[j].receive(pkt);
                            if (new String(inbuf).equals("Hello")) {
                                updater.interrupt();
                                for (int k = 0; k < ROBOT_ADDRESSES.length; ++k) {
                                    if (j != k && threads[k] != null) {
                                        threads[k].interrupt();
                                        socks[k].close();
                                    }
                                }
                            }
                            System.out.printf("Connected to %s.\n", addr);
                            socket = socks[j];
                            return;
                        } catch (IOException ex) {
                            if (!(ex instanceof SocketException && ex.getMessage().equals("Socket closed"))) {
                                ex.printStackTrace();
                            }
                        }
                        Thread.sleep(100);
                    }
                } catch (InterruptedException ex) {
                }
            });
        }
        updater.start();
        for (Thread thread : threads) {
            if (thread != null) {
                thread.start();
            }
        }
        try {
            updater.join();
            for (Thread thread : threads) {
                if (thread != null) {
                    thread.join();
                }
            }
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public void run() {
        byte[] inbuf = new byte[24];
        DatagramPacket packet = new DatagramPacket(inbuf, inbuf.length);
        try {
            while (true) {
                if (socket != null) {
                    socket.receive(packet);
                    ByteBuffer buffer = ByteBuffer.wrap(inbuf);
                    robotX = buffer.getDouble() / 12.0;
                    robotY = buffer.getDouble() / 12.0;
                    robotAngle = buffer.getDouble() / 12.0;
                }
                Thread.sleep(100);
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        }
    }

    public double getX() {
        return robotX;
    }

    public double getY() {
        return robotY;
    }

    public double getAngle() {
        return robotAngle;
    }
}
