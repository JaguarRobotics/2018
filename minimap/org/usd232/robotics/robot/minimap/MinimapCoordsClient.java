package org.usd232.robotics.robot.minimap;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import org.usd232.robotics.powerup.subsystems.LocationSubsystem;
import java.nio.ByteBuffer;
import java.util.Arrays;

public class MinimapCoordsClient {
	
	private static final int PORT = 5801;
	private static double robotX = 0;
	private static double robotY = 0;
	private static double robotAngle = 0;
	private static InetAddress adrs;
	private static byte[] inbuf = new byte[24];
	private static DatagramPacket packet;
	private static DatagramSocket socket;
	
	public MinimapCoordsClient() throws Exception {
		adrs = InetAddress.getByName("10.18.10.2");
	    socket = new DatagramSocket();
	    socket.connect(adrs, PORT);
	    byte[] greeting = new byte[128];
	    greeting = "Hello".getBytes();
	    packet = new DatagramPacket(greeting, greeting.length, adrs, PORT);
	    socket.send(packet);
	}
	
	public static void recievePositionInfo() throws Exception {
		packet = new DatagramPacket(inbuf, inbuf.length);
		socket.receive(packet);

//		for(byte i = 0; i < 8; i++) {
//			outbuf[i] = xBytes[i];
//		}
//		for(byte i = 8; i < 16; i++) {
//			outbuf[i] = yBytes[i];
//		}
//		for(byte i = 16; i < 24; i++) {
//			outbuf[i] = angleBytes[i];
//		}

		robotX = toDouble(Arrays.copyOfRange(inbuf, 0, 8));
		robotY = toDouble(Arrays.copyOfRange(inbuf, 8, 16));
		robotAngle = toDouble(Arrays.copyOfRange(inbuf, 16, 24));
		recievePositionInfo();
	}

	public static double toDouble(byte[] bytes) {
	    return ByteBuffer.wrap(bytes).getDouble();
	}
	
	public static double getX() {
		return robotX;
	}
	
	public static double getY() {
		return robotY;
	}
	
	public static double getAngle() {
		return robotAngle;
	}
}

