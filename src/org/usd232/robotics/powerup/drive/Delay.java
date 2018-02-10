package org.usd232.robotics.powerup.drive;

import org.usd232.robotics.powerup.commands.CommandBase;

public class Delay extends CommandBase {
    private final long millis;
    private long       startTime;

    @Override
    protected void initialize() {
        startTime = System.currentTimeMillis();
    }

    @Override
    protected boolean isFinished() {
        return System.currentTimeMillis() >= startTime + millis;
    }

    public Delay(long millis) {
        this.millis = millis;
    }
}
