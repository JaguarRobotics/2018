package org.usd232.robotics.powerup.calibration;

import java.io.Serializable;
import org.usd232.robotics.powerup.log.Logger;

public class CalibratorData implements Serializable {
    /**
     * The Logger
     * 
     * @since 2018
     * @version 2018
     */
    private static final Logger LOG = new Logger();
    /**
     * Generated serial version uid
     */
    private static final long serialVersionUID = 5137717876753409990L;

    public static double getLiftScale() {
        return liftScale;
    }

    public static void setLiftScale(double liftScale) {
        CalibratorData.liftScale = liftScale;
    }

    public static double getLiftSwitch() {
        return liftSwitch;
    }

    public static void setLiftSwitch(double liftSwitch) {
        CalibratorData.liftSwitch = liftSwitch;
    }

    public static double getLiftBottom() {
        return liftBottom;
    }

    public static void setLiftBottom(double liftBottom) {
        CalibratorData.liftBottom = liftBottom;
    }

    private static double liftScale;
    /**
     * @return true if potentiometer measures height of scale
     * @since 2018
     * @version 2018
     */
    private static double liftSwitch;
    /**
     * @return true if potentiometer measures height of switch
     * @since 2018
     * @version 2018
     */
    private static double liftBottom;
    /**
     * @return true if potentiometer measures lowest point
     * @since 2018
     * @version 2018
     */
}
