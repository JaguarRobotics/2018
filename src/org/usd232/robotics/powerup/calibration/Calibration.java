package org.usd232.robotics.powerup.calibration;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import edu.wpi.first.wpilibj.IterativeRobot;

public class Calibration extends IterativeRobot {
    /**
     * This function is run when the robot is first started up and should be used for any initialization code.
     */
    private static File               f;

    public static void init() {
    }

    public static void writeToFile(CalibratorData data) {
        try {
            f = new File("/home/lvuser/CalibrationSettings.txt");
            if (!f.exists()) {
                System.out.println("Detected file doesnt exist");
                f.createNewFile();
            }
            try (FileOutputStream fo = new FileOutputStream(f); ObjectOutputStream oos = new ObjectOutputStream(fo)) {
                oos.writeObject(data);
                oos.flush();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static CalibratorData readFile() throws IOException, ClassNotFoundException {
        try (FileInputStream fi = new FileInputStream(f); ObjectInputStream ois = new ObjectInputStream(fi)) {
            return (CalibratorData) ois.readObject();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }
}