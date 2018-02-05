package org.usd232.robotics.robot.autogenerator;

import org.usd232.robotics.robot.autogenerator.ui.RouteDrawer;
import org.usd232.robotics.robot.autogenerator.ui.Screen;

public class Main {
	public static Screen screen;
    public static void main(String[] args) {
    	screen = new Screen();
    	screen.add(new RouteDrawer());
        screen.setVisible(true);
    }
}
