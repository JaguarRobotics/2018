package org.usd232.robotics.robot.autogenerator.ui;

import java.awt.Color;
import java.awt.Point;
import javax.swing.JPanel;

public class Robot extends JPanel {
    public static Point       point;
    public static double      angle;
    /**
     * 
     */
    private static final long serialVersionUID = -2310119641674672158L;

    public Robot(double startX, double startY) {
        setBounds((int) startX, (int) startY, (int) (.043 * RouteDrawer.imageWidth),
                        (int) (.051 * RouteDrawer.imageHeight));
        setBackground(Color.RED);
        setVisible(true);
        switch (RouteDrawer.alliance) {
            case Blue:
                switch (RouteDrawer.allianceStation) {
                    case One:
                        break;
                    case Two:
                        break;
                    case Three:
                        break;
                }
                break;
            case Red:
                switch (RouteDrawer.allianceStation) {
                    case One:
                        break;
                    case Two:
                        break;
                    case Three:
                        break;
                }
                break;
        }
        point = new Point(0, 0);
    }

    public void changeRobotAngle() {
    }
}
// Field size 54X27