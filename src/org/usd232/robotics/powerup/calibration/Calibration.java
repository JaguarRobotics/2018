package org.usd232.robotics.powerup.calibration;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import org.usd232.robotics.powerup.log.Logger;
import edu.wpi.first.wpilibj.IterativeRobot;

/**
 * This class imports and exports calibration data.
 * 
 * @author Brian
 * @since 2018
 * @version 2018
 */
public class Calibration extends IterativeRobot {
    /**
     * The Logger
     * 
     * @since 2018
     * @version 2018
     */
    private static final Logger LOG = new Logger();
    /**
     * This is the file that we save / look for.
     * 
     * @since 2018
     * @version 2018
     */
    private static File         f;

    /**
     * Writes the Object of the CalibratorData to a file to be read and used later.
     * 
     * @param data
     *            The current CallibratorData Object right after calibration.
     * @since 2018
     * @version 2018
     */
    public static void writeToFile(CalibratorData data) {
        try {
            if (!f.exists()) {
                f.createNewFile();
                LOG.info("Created A New Calibration File");
            }
            try (FileOutputStream fo = new FileOutputStream(f); ObjectOutputStream oos = new ObjectOutputStream(fo)) {
                oos.writeObject(data);
                oos.flush();
            } catch (IOException e) {
                LOG.error(e, "Error In Calibration Trying To Write The Calibration File");
            }
        } catch (IOException e) {
            LOG.error(e, "Could Not Create A New File");
        }
    }

    /**
     * Reads the File that got saved of the CalibratorData object.
     * 
     * @return Returns the CalibratorData object that was saved in the past.
     * @throws IOException
     * @throws ClassNotFoundException
     * @since 2018
     * @version 2018
     */
    public static CalibratorData readFile() throws IOException, ClassNotFoundException {
        try (FileInputStream fi = new FileInputStream(f); ObjectInputStream ois = new ObjectInputStream(fi)) {
            return (CalibratorData) ois.readObject();
        } catch (FileNotFoundException e) {
            LOG.error(e, "Could Not Read The File");
        }
        return null;
    }

    public static void init() {
        f = new File("/home/lvuser/CalibrationSettings.txt");
    }
}