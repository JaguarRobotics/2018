package org.usd232.robotics.autonomous.generator.model;

import java.awt.EventQueue;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.Base64;
import java.util.LinkedList;
import java.util.List;
import org.usd232.robotics.autonomous.AutonomousModel;
import net.harawata.appdirs.AppDirs;
import net.harawata.appdirs.AppDirsFactory;

public class LocalDatabase
                extends ListModelBase<Tuple2<String, AutonomousModel>, Tuple2<String, AutonomousModel>, LocalDatabase> {
    private static final long serialVersionUID = 8675801961143445185L;
    private final File        dir;
    private boolean           constructing;

    @Override
    public void onUpdate() {
        if (!constructing) {
            EventQueue.invokeLater(()-> {
                List<String> oldFiles = new LinkedList<>();
                oldFiles.addAll(Arrays.asList(dir.list()));
                for (Tuple2<String, AutonomousModel> tuple : list) {
                    String filename = Base64.getEncoder().encodeToString(tuple.obj1.getBytes());
                    oldFiles.remove(filename);
                    try (FileOutputStream stream = new FileOutputStream(new File(dir, filename))) {
                        stream.write(tuple.obj2.serializeString().getBytes());
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                }
                for (String file : oldFiles) {
                    new File(dir, file).delete();
                }
            });
        }
    }

    @Override
    protected String toString(Tuple2<String, AutonomousModel> obj) {
        return obj.obj1;
    }

    @Override
    protected Tuple2<String, AutonomousModel> create(String name) {
        File file = new File(dir, Base64.getEncoder().encodeToString(name.getBytes()));
        AutonomousModel model;
        if (file.exists()) {
            try (FileInputStream stream = new FileInputStream(file)) {
                byte[] data = new byte[(int) file.length()];
                stream.read(data);
                model = new AutonomousModel(new String(data));
            } catch (IOException ex) {
                ex.printStackTrace();
                model = new AutonomousModel();
            }
        } else {
            model = new AutonomousModel();
        }
        return new Tuple2<>(name, model);
    }

    public LocalDatabase() {
        constructing = true;
        AppDirs appdirs = AppDirsFactory.getInstance();
        dir = new File(appdirs.getUserDataDir("Autonomous Generator", "1.0", "Jaguar Robotics"));
        System.out.printf("Saving files to %s\n", dir);
        dir.mkdirs();
        for (String enc : dir.list()) {
            add(new String(Base64.getDecoder().decode(enc)));
        }
        constructing = false;
    }
}
