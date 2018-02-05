package org.usd232.robotics.robot.autogenerator.ui;

import java.awt.Color;

import javax.swing.JPanel;

public class Robot extends JPanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = -2310119641674672158L;

	public Robot(double startX, double startY) {
		setBounds((int) startX, (int) startY, (int) (.043 * RouteDrawer.imageWidth),
				(int) (.051 * RouteDrawer.imageHeight));
		setBackground(Color.RED);
		setVisible(true);
	}
}
// Field size 54X27