package org.usd232.robotics.powerup.subsystems;

import org.usd232.robotics.powerup.log.Logger;
import org.usd232.robotics.powerup.minimap.IMinimapCoordProvider;

public class LocationSubsystem extends SubsystemBase implements IMinimapCoordProvider {
    private static final Logger LOG = new Logger();
    private double              angleOffset;
    private double              x;
    private double              y;
    private double              theta;
    private double              velocity;
    private long                lastTime;

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
        LOG.debug("Resetting LocationSubsystem");
        angleOffset = gyro.getAngle() * Math.PI / 180 - Math.PI / 2;
        x = 0;
        y = 0;
    }

    public void updateValues() {
        theta = gyro.getAngle() * Math.PI / 180 - angleOffset;
        long time = System.currentTimeMillis();
        double dt = ((double) (time - lastTime)) / 1000.0;
        lastTime = time;
        // TODO acceleration
        double ds = velocity * dt;
        double sin = Math.sin(theta);
        double cos = Math.cos(theta);
        x += ds * sin;
        y += ds * cos;
    }
}
