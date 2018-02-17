package org.usd232.robotics.autonomous.generator;

import java.io.File;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Vector;
import org.usd232.robotics.autonomous.generator.model.GeneratorModel;
import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.ChannelSftp.LsEntry;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;

public class SyncService extends Thread {
    private static final long DELAY_TIME = 1000;
    private GeneratorModel    model;
    private String            hostname;

    @Override
    public void run() {
        JSch jsch = new JSch();
        JSch.setConfig("StrictHostKeyChecking", "no");
        while (true) {
            boolean didConnect = false;
            try {
                Thread.sleep(DELAY_TIME);
                Session session = jsch.getSession("lvuser", hostname);
                session.connect();
                ChannelSftp sftp = (ChannelSftp) session.openChannel("sftp");
                sftp.connect();
                didConnect = true;
                System.out.printf("Connected to %s.\n", hostname);
                try {
                    sftp.stat("/home/lvuser/routes");
                } catch (Exception ex) {
                    ex.printStackTrace();
                    sftp.mkdir("/home/lvuser/routes");
                }
                sftp.cd("/home/lvuser/routes");
                int lastUpdate = -1;
                while (sftp.isConnected()) {
                    int update = model.localDatabase.updateCount;
                    if (update != lastUpdate) {
                        @SuppressWarnings("unchecked")
                        Vector<LsEntry> vector = sftp.ls("*");
                        List<String> localFiles = new LinkedList<String>();
                        localFiles.addAll(Arrays.asList(model.localDatabase.dir.list()));
                        for (LsEntry entry : vector) {
                            if (localFiles.contains(entry.getFilename())) {
                                sftp.put(new File(model.localDatabase.dir, entry.getFilename()).getAbsolutePath(),
                                                entry.getFilename());
                                localFiles.remove(entry.getFilename());
                            } else {
                                sftp.rm(entry.getFilename());
                            }
                        }
                        for (String file : localFiles) {
                            sftp.put(new File(model.localDatabase.dir, file).getAbsolutePath(), file);
                        }
                        System.out.printf("Synced to %s.\n", hostname);
                        lastUpdate = update;
                    }
                    Thread.sleep(DELAY_TIME);
                }
                sftp.disconnect();
                session.disconnect();
            } catch (JSchException ex) {
                if (ex.getCause() == null || (!(ex.getCause() instanceof UnknownHostException) && !(ex
                                .getCause() instanceof SocketException
                                && (ex.getCause().getMessage().equals("Network is unreachable (connect failed)")
                                                || ex.getCause().getMessage().equals(
                                                                "Connection timed out (Connection timed out)"))))) {
                    System.err.printf("Error in sync service for %s:", hostname);
                    ex.printStackTrace();
                }
            } catch (Exception ex) {
                System.err.printf("Error in sync service for %s:", hostname);
                ex.printStackTrace();
            }
            if (didConnect) {
                System.out.printf("Disconnected from %s.\n", hostname);
            }
        }
    }

    public static void start(GeneratorModel model, int team) {
        new SyncService(model, String.format("10.%d.%d.2", team / 100, team % 100)).start();
        new SyncService(model, "172.22.11.2").start();
        new SyncService(model, String.format("roboRIO-%d-FRC.lan", team)).start();
        new SyncService(model, String.format("roboRIO-%d-FRC.frc-field.local", team)).start();
        new SyncService(model, String.format("roboRIO-%d-FRC.local", team)).start();
    }

    public SyncService(GeneratorModel model, String hostname) {
        this.model = model;
        this.hostname = hostname;
    }
}
