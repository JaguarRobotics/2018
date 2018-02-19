package org.usd232.robotics.powerup.subsystems;

import java.lang.ref.WeakReference;
import java.util.LinkedList;
import java.util.List;
import org.usd232.robotics.powerup.log.Logger;
import org.usd232.robotics.powerup.minimap.IMinimapCoordProvider;

public class LocationSubsystem extends SubsystemBase {
    public class Context implements IMinimapCoordProvider {
        private double                 x;
        private double                 y;
        private double                 theta;
        private double                 angleOffset;
        private WeakReference<Context> ref;

        private void updateValues(double ds1, double ds2, double dtheta, double theta) {
            this.theta = theta - angleOffset;
            double xPart;
            double yPart;
            if (dtheta == 0) {
                xPart = (ds1 + ds2) / 2;
                yPart = 0;
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

        public double getX() {
            return x;
        }

        public double getY() {
            return y;
        }

        public double getAngle() {
            return theta;
        }

        public double getSpeed() {
            return speed;
        }

        @Override
        protected void finalize() throws Throwable {
            contexts.remove(ref);
        }

        public Context() {
            ref = new WeakReference<Context>(this);
            contexts.add(ref);
        }
    }

    private static final Logger          LOG            = new Logger();
    private static final double          WIDTH          = 18;
    private static final double          CENTER_OF_MASS = 0.5;
    /**
     * Diameter of pulleys, used for encoder calculations. (in inches)
     */
    private static final double          DIAMETER       = 6;
    /**
     * pulses per rotation for the encoders.
     */
    private static final int             PPR            = 400 * 3;
    private double                       lastS1;
    private double                       lastS2;
    private double                       lastTheta;
    private long                         lastTime;
    private double                       speed;
    private List<WeakReference<Context>> contexts;

    public LocationSubsystem() {
        contexts = new LinkedList<WeakReference<Context>>();
        reset();
    }

    @Override
    protected void initDefaultCommand() {
    }

    public void reset() {
        LOG.warn("Resetting LocationSubsystem");
        leftDriveEncoder.setDistancePerPulse(Math.PI * DIAMETER / PPR);
        rightDriveEncoder.setDistancePerPulse(Math.PI * DIAMETER / PPR);
        leftDriveEncoder.reset();
        rightDriveEncoder.reset();
        lastTime = System.currentTimeMillis();
    }

    @SuppressWarnings("unchecked")
    public void updateValues() {
        double s1 = -leftDriveEncoder.getDistance();
        double s2 = rightDriveEncoder.getDistance();
        double theta = gyro.getAngle() * Math.PI / 180;
        double ds1 = s1 - lastS1;
        double ds2 = s2 - lastS2;
        double dtheta = theta - lastTheta;
        lastS1 = s1;
        lastS2 = s2;
        lastTheta = theta;
        for (WeakReference<Context> ref : contexts.toArray(new WeakReference[0])) {
            Context ctx = ref.get();
            if (ctx == null) {
                contexts.remove(ref);
            } else {
                ctx.updateValues(ds1, ds2, dtheta, theta);
            }
        }
        long time = System.currentTimeMillis();
        double dt = ((double) (time - lastTime)) / 1000.0;
        lastTime = time;
        double ds = (ds1 + ds2) / 2.0;
        speed = ds / dt;
    }
}
