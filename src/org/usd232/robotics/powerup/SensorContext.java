package org.usd232.robotics.powerup;

import java.lang.ref.WeakReference;
import java.util.LinkedList;
import java.util.List;
import org.usd232.robotics.powerup.log.Logger;

public class SensorContext implements IO {
    private static final List<WeakReference<SensorContext>> CONTEXTS = new LinkedList<>();
    private final WeakReference<SensorContext>              that;                         // aka this
    private static double                                   absoluteEncoderLeft;
    private static double                                   absoluteEncoderRight;
    private static double                                   absoluteGyro;
    private double                                          encoderLeftOffset;
    private double                                          encoderRightOffset;
    private double                                          gyroOffset;
    private double                                          encoderLeft;
    private double                                          encoderRight;
    private double                                          gyro;
    private static final Logger    LOG              = new Logger();

    public double getEncoderLeft() {
        return encoderLeft;
    }

    public double getEncoderRight() {
        return encoderRight;
    }

    public double getGyro() {
        return gyro;
    }

    private void updateInstance() {
        encoderLeft = IO.leftDriveEncoder.get() - encoderLeftOffset;
        encoderRight = IO.rightDriveEncoder.get() - encoderRightOffset;
        gyro = absoluteGyro - gyroOffset;
    }

    @SuppressWarnings("unchecked")
    static void update() {
        for (WeakReference<SensorContext> ref : CONTEXTS.toArray(new WeakReference[0])) {
            SensorContext ctx = ref.get();
            if (ctx == null) {
                CONTEXTS.remove(ref);
            } else {
                ctx.updateInstance();
            }
        }
    }

    @Override
    protected void finalize() throws Throwable {
        CONTEXTS.remove(that);
        that.clear();
    }

    public SensorContext() {
        that = new WeakReference<>(this);
        CONTEXTS.add(that);
        encoderLeftOffset = absoluteEncoderLeft;
        encoderRightOffset = absoluteEncoderRight;
        gyroOffset = absoluteGyro;
    }
}