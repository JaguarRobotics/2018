package org.usd232.robotics.powerup.calibration;

import java.io.Serializable;

public class CalibratorData implements Serializable {
    /**
     * 
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
    private double liftSwitch;
    private double liftBottom;
    
    
}
