package org.usd232.robotics.powerup.log;

@FunctionalInterface
public interface UnsafeFunction<T> {
    T run() throws Throwable;
}
