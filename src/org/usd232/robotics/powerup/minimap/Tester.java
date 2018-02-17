package org.usd232.robotics.powerup.minimap;

public class Tester implements Runnable, IMinimapCoordProvider {
    private double x;
    private double y;
    private double angle;

    @Override
    public double getX() {
        return x;
    }

    @Override
    public double getY() {
        return y;
    }

    @Override
    public double getAngle() {
        return angle;
    }

    @Override
    public void run() {
        new MinimapCoordsServer(this).start();
        try {
            while (true) {
                x += 0.1;
                while (x > 10) {
                    x -= 10;
                }
                Thread.sleep(10);
            }
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        }
    }

    public static void main(String[] args) {
        new Tester().run();
    }
}
