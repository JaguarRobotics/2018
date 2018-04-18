package org.usd232.robotics.powerup.calibration;

import java.io.Serializable;

public class CalibratorData implements Serializable {
    /**
     * Generated serialVersion UID.
     */
    private static final long serialVersionUID = -3757239211057878575L;
    /**
     * Potentiometer value for the scale for the top of the lift.
     * 
     * @since 2018
     * @version 2018
     */
    private double            liftScale;
    /**
     * Potentiometer value for the switch on the lift.
     * 
     * @since 2018
     * @version 2018
     */
    private double            liftSwitch;
    /**
     * Potentiometer value for the bottom of the lift.
     * 
     * @since 2018
     * @version 2018
     */
    private double            liftBottom;
    /**
     * The value of frictional acceleration.
     * 
     * @since 2018
     * @version 2018
     */
    private double            frictionalAcceleration;

    /**
     * Gets the scale value.
     * 
     * @return the calibrated scale value.
     */
    public double getLiftScale() {
        return liftScale;
    }

    /**
     * Sets the scale value.
     * 
     * @param liftScale
     *            the scale value to set.
     */
    public void setLiftScale(double liftScale) {
        this.liftScale = liftScale;
    }

    /**
     * Gets the switch value.
     * 
     * @return the calibrated switch value.
     */
    public double getLiftSwitch() {
        return liftSwitch;
    }

    /**
     * Sets the switch value.
     * 
     * @param liftSwitch
     *            the switch value to set.
     */
    public void setLiftSwitch(double liftSwitch) {
        this.liftSwitch = liftSwitch;
    }

    /**
     * Gets the bottom value of the scissor lift.
     * 
     * @return the calibrated bottom value.
     */
    public double getLiftBottom() {
        return liftBottom;
    }

    /**
     * Sets the bottom value.
     * 
     * @param liftBottom
     *            the bottom value to set.
     */
    public void setLiftBottom(double liftBottom) {
        this.liftBottom = liftBottom;
    }

    /**
     * Gets the calibrated frictional acceleration.
     * 
     * @return the calibrated frictional acceleration.
     */
    public double getFrictionalAcceleration() {
        return frictionalAcceleration;
    }

    /**
     * Sets the calibrated frictional acceleration.
     * 
     * @param the
     *            frictional acceleration.
     */
    public void setFrictionalAcceleration(double frictionalAcceleration) {
        this.frictionalAcceleration = frictionalAcceleration;
    }
}
