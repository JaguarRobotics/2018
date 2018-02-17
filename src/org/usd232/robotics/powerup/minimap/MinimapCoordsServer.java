package org.usd232.robotics.powerup.minimap;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import org.usd232.robotics.powerup.log.Logger;

public class MinimapCoordsServer extends Thread {
    private static final Logger   LOG         = new Logger();
    private static final int      PORT        = 5801;
    private static final long     SEND_PERIOD = 50;
    private static final long     TIMEOUT     = 10000;
    private IMinimapCoordProvider location;

    public MinimapCoordsServer(IMinimapCoordProvider location) {
        this.location = location;
    }

    @Override
    public void run() {
        byte[] greeting = "Hello".getBytes();
        try (DatagramSocket sock = new DatagramSocket(null)) {
            sock.setReuseAddress(true);
            sock.bind(new InetSocketAddress(PORT));
            LOG.info("Minimap server started on 0.0.0.0:%d", PORT);
            byte[] inbuf = new byte[5];
            while (true) {
                DatagramPacket packet = new DatagramPacket(inbuf, inbuf.length);
                sock.receive(packet);
                if (new String(inbuf).equals("Hello")) {
                    sock.send(new DatagramPacket(greeting, greeting.length, packet.getSocketAddress()));
                    LOG.debug("Sending minimap data to %s", packet.getAddress());
                    Thread logThread = new Thread(()-> {
                        try {
                            while (true) {
                                try {
                                    ByteBuffer buffer = ByteBuffer.allocate(24);
                                    buffer.putDouble(location.getX());
                                    buffer.putDouble(location.getY());
                                    buffer.putDouble(location.getAngle());
                                    sock.send(new DatagramPacket(buffer.array(), 24, packet.getSocketAddress()));
                                } catch (IOException ex) {
                                    ex.printStackTrace();
                                }
                                Thread.sleep(SEND_PERIOD);
                            }
                        } catch (InterruptedException ex) {
                        }
                    });
                    Thread timeThread = new Thread(()-> {
                        try {
                            Thread.sleep(TIMEOUT);
                        } catch (InterruptedException ex) {
                            LOG.debug(ex, "Timer thread interrupted");
                        }
                        logThread.interrupt();
                    });
                    logThread.start();
                    timeThread.start();
                }
                Thread.sleep(100);
            }
        } catch (IOException ex) {
            LOG.error(ex, "Unable to start minimap server");
        } catch (InterruptedException ex) {
            LOG.info("Shutting down minimap server");
        }
    }
}
