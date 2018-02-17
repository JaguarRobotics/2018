package org.usd232.robotics.powerup.drive;

import org.usd232.robotics.powerup.commands.CommandBase;
import org.usd232.robotics.powerup.log.Logger;

public class Delay extends CommandBase {
    private static final Logger  LOG = new Logger();
    private final long millis;
    private long       startTime;

    @Override
    protected void initialize() {
        LOG.enter("initialize");
        startTime = System.currentTimeMillis();
    }

    @Override
    protected boolean isFinished() {
        return System.currentTimeMillis() >= startTime + millis;
    }

    @Override
    protected void end() {
        LOG.enter("end");
    }

    public Delay(long millis) {
        this.millis = millis;
    }
}
