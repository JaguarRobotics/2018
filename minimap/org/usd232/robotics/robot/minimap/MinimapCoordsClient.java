package org.usd232.robotics.robot.minimap;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.nio.ByteBuffer;

public class MinimapCoordsClient {
    private static final int      PORT       = 5801;
    private double         robotX     = 0;
    private double         robotY     = 0;
    private double         robotAngle = 0;
    private DatagramSocket socket;

    public MinimapCoordsClient() throws Exception {
        InetAddress adrs = InetAddress.getByName("10.18.10.2");
        socket = new DatagramSocket();
        socket.connect(adrs, PORT);
        byte[] greeting = "Hello".getBytes();
        DatagramPacket packet = new DatagramPacket(greeting, greeting.length, adrs, PORT);
        socket.send(packet);
    }

    public void recievePositionInfo() throws Exception {
        byte[] inbuf = new byte[24];
        DatagramPacket packet = new DatagramPacket(inbuf, inbuf.length);
        socket.receive(packet);
        ByteBuffer buffer = ByteBuffer.wrap(inbuf);
        robotX = buffer.getDouble();
        robotY = buffer.getDouble();
        robotAngle = buffer.getDouble();
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
