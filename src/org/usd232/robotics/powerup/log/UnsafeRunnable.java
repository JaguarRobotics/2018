package org.usd232.robotics.powerup.log;

@FunctionalInterface
public interface UnsafeRunnable {
    void run() throws Throwable;
}
