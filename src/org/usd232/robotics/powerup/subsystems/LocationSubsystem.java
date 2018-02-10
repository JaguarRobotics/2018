package org.usd232.robotics.powerup.subsystems;

public class LocationSubsystem extends SubsystemBase {
    private static final double WIDTH          = 18;
    private static final double CENTER_OF_MASS = 0.5;
    /**
     * Diameter of pulleys, used for encoder calculations. (in inches)
     */
    private static final double DIAMETER       = 6;
    /**
     * pulses per rotation for the encoders.
     */
    private static final int    PPR            = 400 * 3;
    private double              angleOffset;
    private double              lastS1;
    private double              lastS2;
    private double              lastTheta;
    private double              x;
    private double              y;
    private double              theta;

    public LocationSubsystem() {
        reset();
    }

    @Override
    protected void initDefaultCommand() {
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public double getAngle() {
        return theta;
    }

    public void reset() {
        leftDriveEncoder.setDistancePerPulse(Math.PI * DIAMETER / PPR);
        rightDriveEncoder.setDistancePerPulse(Math.PI * DIAMETER / PPR);
        leftDriveEncoder.reset();
        rightDriveEncoder.reset();
        angleOffset = gyro.getAngle() * Math.PI / 180 - Math.PI / 2;
        x = 0;
        y = 0;
        lastS1 = 0;
        lastS2 = 0;
        lastTheta = Math.PI / 2;
    }

    public void updateValues() {
        double s1 = -leftDriveEncoder.getDistance();
        double s2 = rightDriveEncoder.getDistance();
        theta = gyro.getAngle() * Math.PI / 180 - angleOffset;
        double ds1 = s1 - lastS1;
        double ds2 = s2 - lastS2;
        double dtheta = theta - lastTheta;
        lastS1 = s1;
        lastS2 = s2;
        lastTheta = theta;
        double xPart;
        double yPart;
        if (dtheta == 0) {
            xPart = 0;
            yPart = (ds1 + ds2) / 2;
        } else {
            double coefficient = ((ds1 + ds2) / dtheta - WIDTH) / 2 + WIDTH * CENTER_OF_MASS;
            xPart = -coefficient * Math.sin(dtheta);
            yPart = coefficient * (1 - Math.cos(dtheta));
        }
        double sin = Math.sin(theta);
        double cos = Math.cos(theta);
        x += xPart * cos + yPart * sin;
        y += xPart * sin + yPart * cos;
    }

    /**
     * Gets the angle based on where the encoders have gone.
     */
    public void getEncoderAngle() {
    }
}
