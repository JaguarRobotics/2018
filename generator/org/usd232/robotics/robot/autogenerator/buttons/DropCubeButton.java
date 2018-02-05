package org.usd232.robotics.robot.autogenerator.buttons;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;

public class DropCubeButton extends JButton {
	/**
	 * 
	 */
	private static final long serialVersionUID = 268131391016152478L;

	public DropCubeButton() {
		setup();
	}

	private void setup() {
		setBackground(Color.GREEN);
		setText("Drop");
		addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
			}
		});
	}
}
