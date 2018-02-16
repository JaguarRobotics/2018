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
    private double     liftScale;
    /**
     * Potentiometer value for the switch on the lift
     * 
     * @since 2018
     * @version 2018
     */
    private double     liftSwitch;
    /***
     * Potentiometer value for the bottom of the lift
     * 
     * @since 2018
     * @version 2018
     */
    private double     liftBottom;
    /***
     * Potentiometer value for the Height we need to go to climb to the top
     * 
     * @since 2018
     * @version 2018
     */
    private double     liftClimbTop;

    public double getLiftScale() {
        return liftScale;
    }

    public void setLiftScale(double liftScale) {
        this.liftScale = liftScale;
    }

    public double getLiftSwitch() {
        return liftSwitch;
    }

    public void setLiftSwitch(double liftSwitch) {
        this.liftSwitch = liftSwitch;
    }

    public double getLiftBottom() {
        return liftBottom;
    }

    public void setLiftBottom(double liftBottom) {
        this.liftBottom = liftBottom;
    }

    public double getLiftClimbTop() {
        return liftClimbTop;
    }

    public void setLiftClimbTop(double liftClimbTop) {
        this.liftClimbTop = liftClimbTop;
    }
}
