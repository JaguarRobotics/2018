package org.usd232.robotics.powerup;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import org.usd232.robotics.powerup.subsystems.LocationSubsystem;
import java.nio.ByteBuffer;

public class MinimapCoordsServer {
	
	private static final int PORT = 5801;
	private static double robotX = 0;
	private static double robotY = 0;
	private static double robotAngle = 0;
	private static byte[] xBytes = new byte[8];
	private static byte[] yBytes = new byte[8];
	private static byte[] angleBytes = new byte[8];
	private static LocationSubsystem location = new LocationSubsystem();
	private static InetAddress adrs;
	private static byte[] outbuf = new byte[24];
	private static DatagramPacket packet;
	private static DatagramSocket socket;
	
	public MinimapCoordsServer() throws Exception {
	    socket = new DatagramSocket();
	    byte[] inbuf = new byte[100];
	    packet = new DatagramPacket(inbuf, 100);
	    socket.receive(packet);
	    adrs = packet.getAddress();
	}
	
	public static void sendPositionInfo() throws Exception {
		robotX = location.getX();
		robotY = location.getY();
		robotAngle = location.getAngle();
		xBytes = toByteArray(robotX);
		yBytes = toByteArray(robotY);
		angleBytes = toByteArray(robotAngle);

		for(byte i = 0; i < 8; i++) {
			outbuf[i] = xBytes[i];
		}
		for(byte i = 8; i < 16; i++) {
			outbuf[i] = yBytes[i];
		}
		for(byte i = 16; i < 24; i++) {
			outbuf[i] = angleBytes[i];
		}
		
		packet = new DatagramPacket(outbuf, 24, adrs, PORT);
		socket.send(packet);
		
		sendPositionInfo();
	}

	public static byte[] toByteArray(double value) {
	    byte[] bytes = new byte[8];
	    ByteBuffer.wrap(bytes).putDouble(value);
	    return bytes;
	}
}
