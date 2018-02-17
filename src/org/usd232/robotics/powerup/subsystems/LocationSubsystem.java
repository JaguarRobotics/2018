package org.usd232.robotics.powerup.subsystems;

import org.usd232.robotics.powerup.log.Logger;
import org.usd232.robotics.powerup.minimap.IMinimapCoordProvider;

public class LocationSubsystem extends SubsystemBase implements IMinimapCoordProvider {
    private static final Logger LOG                  = new Logger();
    private static final double MAX_TORQUE           = 343.4 / 16.0;                  // in-oz * (oz / lbs) = in-lbs
    private static final int    NUM_MOTORS           = 4;
    private static final double MAX_ANGULAR_VELOCITY = 5310.0 * 2.0 * Math.PI / 60.0; // (rev / min) * (rad / rev) *
                                                                                      // (min / s) = rad / s
    private static final double ROBOT_MASS           = 130;                           // lbs
    private static final double WHEEL_RADIUS         = 3;                             // in
    private static final double GEARING_RATIO        = 1.0 / 1.0;                     // motor : wheel
    private static final double KINETIC_FRICTION     = 0.6;
    private static final double GRAVITY              = 32.2 * 12.0;                   // (ft / s^2) * (in / ft) = in /
                                                                                      // s^2
    private double              angleOffset;
    private double              x;
    private double              y;
    private double              theta;
    private double              velocity;
    private long                lastTime;
    private double              motorPower;

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

    public void setMotorPower(double motorPower) {
        this.motorPower = motorPower;
    }

    public void reset() {
        LOG.debug("Resetting LocationSubsystem");
        angleOffset = gyro.getAngle() * Math.PI / 180 - Math.PI / 2;
        x = 0;
        y = 0;
        theta = Math.PI / 2;
        velocity = 0;
        lastTime = System.currentTimeMillis();
    }

    private double calculateForce() {
        double angularVelocity = velocity * WHEEL_RADIUS;
        double torque = MAX_TORQUE * NUM_MOTORS * GEARING_RATIO * motorPower
                        * (1.0 - angularVelocity / MAX_ANGULAR_VELOCITY);
        double forceApplied = torque / WHEEL_RADIUS;
        double forceNormal = ROBOT_MASS * GRAVITY;
        double forceFriction = forceNormal * KINETIC_FRICTION;
        return forceApplied - forceFriction;
    }

    public void updateValues() {
        theta = gyro.getAngle() * Math.PI / 180 - angleOffset;
        long time = System.currentTimeMillis();
        double dt = ((double) (time - lastTime)) / 1000.0;
        lastTime = time;
        double force = calculateForce();
        double accel = force / ROBOT_MASS;
        velocity += accel * dt;
        double ds = velocity * dt;
        double sin = Math.sin(theta);
        double cos = Math.cos(theta);
        x += ds * sin;
        y += ds * cos;
    }
}
