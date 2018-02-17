package org.usd232.robotics.powerup.calibration;

import java.io.Serializable;

public class CalibratorData implements Serializable {
    /**
     * Generated serial version uid
     */
    private static final long serialVersionUID = 5137717876753409990L;
    /**
     * Potentiometer value for the scale for the top of the lift
     * 
     * @since 2018
     * @version 2018
     */
    private static double liftScale;
    /**
     * Potentiometer value for the switch on the lift
     * 
     * @since 2018
     * @version 2018
     */
    private static double liftSwitch;
    /***
     * Potentiometer value for the bottom of the lift
     * 
     * @since 2018
     * @version 2018
     */
    private static double liftBottom;
    /***
     * Potentiometer value for the Height we need to go to climb to the top
     * 
     * @since 2018
     * @version 2018
     */
    private static double liftClimbTop;
    
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
    
    public static double getLiftClimbTop() {
        return liftClimbTop;
    }

    public static void setLiftClimbTop(double liftClimbTop) {
        CalibratorData.liftClimbTop = liftClimbTop;
    }
}
