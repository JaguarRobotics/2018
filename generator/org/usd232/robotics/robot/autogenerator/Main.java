package org.usd232.robotics.robot.autogenerator;

import org.usd232.robotics.robot.autogenerator.ui.Screen;
import org.usd232.robotics.robot.autogenerator.ui.Startup;

public class Main {
	public static Screen screen;
	public static Startup startup;
    public static void main(String[] args) {
    	screen = new Screen();
    	startup = new Startup();
    	screen.add(startup);
        screen.setVisible(true);
    }
}
