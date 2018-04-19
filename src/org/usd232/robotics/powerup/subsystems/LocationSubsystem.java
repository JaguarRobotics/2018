package org.usd232.robotics.powerup.subsystems;

import java.lang.ref.WeakReference;
import java.util.LinkedList;
import java.util.List;
import org.usd232.robotics.powerup.Robot;
import org.usd232.robotics.powerup.log.Logger;
import org.usd232.robotics.powerup.minimap.IMinimapCoordProvider;

/**
 * Subsystem that we use to track the current location data of the robot.
 * 
 * @author Zach, Brian
 * @since 2018
 */
public class LocationSubsystem extends SubsystemBase {
    /**
     * Stores the variables so that we can reset this and not lose all of the data.
     * 
     * @author Zach, Brian
     * @since 2018
     */
    public class Context implements IMinimapCoordProvider {
        /**
         * The X position.
         * 
         * @since 2018
         */
        private double                 x;
        /**
         * The Y position.
         * 
         * @since 2018
         */
        private double                 y;
        /**
         * The angle of the robot.
         * 
         * @since 2018
         */
        private double                 theta;
        /**
         * The angle offset.
         * 
         * @since 2018
         */
        private double                 angleOffset;
        /**
         * Reference to this object. ("this" but as a weak reference)
         * 
         * @since 2018
         */
        private WeakReference<Context> ref;

        /**
         * Updates the values of the robots location.
         * 
         * @param ds1
         *            Change in arc length one.
         * @param ds2
         *            Change in arc length two.
         * @param dtheta
         *            The change in theta.
         * @param theta
         *            The theta of the robot
         * @since 2018
         */
        private void updateValues(double ds1, double ds2, double dtheta, double theta) {
            theta -= angleOffset;
            this.theta = theta;
            double xPart;
            double yPart;
            if (dtheta == 0) {
                xPart = (ds1 + ds2) / 2;
                yPart = 0;
            } else {
                double coefficient = ((ds1 + ds2) / dtheta - WIDTH) / 2 + WIDTH * CENTER_OF_MASS;
                xPart = coefficient * Math.sin(dtheta);
                yPart = coefficient * (1 - Math.cos(dtheta));
            }
            double sin = Math.sin(theta);
            double cos = Math.cos(theta);
            x += xPart * cos + yPart * sin;
            y += xPart * sin + yPart * cos;
        }

        /**
         * Gets the X of the robot.
         * 
         * @since 2018
         */
        public double getX() {
            return x;
        }

        /**
         * Gets the Y of the robot.
         * 
         * @since 2018
         */
        public double getY() {
            return y;
        }

        /**
         * Gets the angle of the robot.
         * 
         * @since 2018
         */
        public double getAngle() {
            return theta;
        }

        /**
         * Gets the speed of the robot.
         * 
         * @since 2018
         */
        public double getSpeed() {
            return speed;
        }

        /**
         * {inheritDoc}
         */
        @Override
        protected void finalize() throws Throwable {
            contexts.remove(ref);
        }

        /**
         * Constructor
         * 
         * @since 2018
         */
        public Context() {
            ref = new WeakReference<Context>(this);
            contexts.add(ref);
            angleOffset = gyro.getAngle() * Math.PI / 180 - Math.PI / 2;
        }
    }

    /**
     * The logger of the robot.
     * 
     * @since 2018
     */
    private static final Logger          LOG            = new Logger();
    /**
     * The width of the robot.
     * 
     * @since 2018
     */
    private static final double          WIDTH          = 18;
    /**
     * The center of the mass.
     * 
     * @since 2018
     */
    private static final double          CENTER_OF_MASS = 0.5;
    /**
     * The wheel's circumference
     * 
     * @since 2018
     */
    private static double                WHEEL_CIRCUMFERENCE = 18.84;
    /**
     * Amount of ticks for a full rotation of the wheel.
     * 
     * @since 2018
     */
    private static double                TICKS_PER_REV = 750;
    /**
     * The last arc length #1.
     * 
     * @since 2018
     */
    private double                       lastS1;
    /**
     * The last arc length #2.
     * 
     * @since 2018
     */
    private double                       lastS2;
    /**
     * The last angle of the robot.
     * 
     * @since 2018
     */
    private double                       lastTheta;
    /**
     * The last time of the robot.
     * 
     * @since 2018
     */
    private long                         lastTime;
    /**
     * The speed of the robot
     * 
     * @since 2018
     */
    private double                       speed;
    /**
     * A list of the references of the robot.
     * 
     * @since 2018
     */
    private List<WeakReference<Context>> contexts;

    /**
     * Its the constructor -.-
     * 
     * @since 2018
     */
    public LocationSubsystem() {
        contexts = new LinkedList<WeakReference<Context>>();
        reset();
    }

    @Override
    protected void initDefaultCommand() {
    }

    /**
     * Resets the subsystem.
     * 
     * @since 2018
     */
    public void reset() {
        LOG.warn("Resetting LocationSubsystem");
        leftDriveEncoder.setDistancePerPulse(1);
        rightDriveEncoder.setDistancePerPulse(1);
        leftDriveEncoder.reset();
        rightDriveEncoder.reset();
        lastTime = System.currentTimeMillis();
    }

    /**
     * Updates the values of the drive subsystem.
     * 
     * @since 2018
     */
    @SuppressWarnings("unchecked")
    public void updateValues() {
        double s1 = (leftDriveEncoder.getRaw() / TICKS_PER_REV) * WHEEL_CIRCUMFERENCE;
        double s2 = (-rightDriveEncoder.getRaw() / TICKS_PER_REV) * WHEEL_CIRCUMFERENCE;
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

    public Context createContext() {
        return new Context();
    }
}
