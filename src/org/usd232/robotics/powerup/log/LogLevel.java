package org.usd232.robotics.powerup.log;

public enum LogLevel {
    FATAL(0), ERROR(1), STDERR(2), WARN(3), STDOUT(4), INFO(5), DEBUG(6), TRACE(7);
    int levelId;

    public int getLevelId() {
        return levelId;
    }

    private LogLevel(int levelId) {
        this.levelId = levelId;
    }
}
