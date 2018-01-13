package org.usd232.robotics.powerup.calibration;

import java.io.Serializable;

public class CalibratorData implements Serializable {
    /**
     * Generated serial version uid
     */
    private static final long serialVersionUID = 5137717876753409990L;
    
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
    private double liftScale;
    /**
     * @return true if potentiometer measures height of scale
     * @since 2018 
     * @version 2018
     */
    private double liftSwitch;
    /**
     * @return true if potentiometer measures height of switch
     * @since 2018
     * @version 2018
     */
    private double liftBottom;
    /**
     * @return true if potentiometer measures lowest point
     * @since 2018
     * @version 2018
     */
    
    
}
