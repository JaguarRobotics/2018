package org.usd232.robotics.robot.autogenerator.ui;

import java.awt.Component;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import buttons.CloseButton;
import buttons.DropCubeButton;
import buttons.EncoderDriveButton;
import buttons.GyroTurnButton;

public class Screen extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * Height of the toolbar
	 */
	private final int TOOLBAR_HEIGHT = 75;
	/**
	 * Amount of buttones (Temp)
	 */
	private final Object[] buttonArray = { new DropCubeButton(), new EncoderDriveButton(), new GyroTurnButton(), new CloseButton() };

	public Screen() {
		setLayout(null);
		ImageIcon backgroundImage = getImage("Field.png");

		JPanel toolbar = new JPanel();
		toolbar.setBounds(0, 0, backgroundImage.getIconWidth(), TOOLBAR_HEIGHT);
		addButtons(toolbar);
		add(toolbar);

		JPanel backgroundPanel = new JPanel();
		backgroundPanel.setBounds(0, toolbar.getHeight(), backgroundImage.getIconWidth(),
				backgroundImage.getIconHeight());
		JLabel label = new JLabel(backgroundImage);
		backgroundPanel.add(label);
		add(backgroundPanel);
		repaint();

		setBounds(0, 0, backgroundPanel.getWidth(), backgroundPanel.getHeight() + toolbar.getHeight());
		setUndecorated(true);
		setVisible(true);
	}

	private ImageIcon getImage(String url) {
		ImageIcon image;
		image = new ImageIcon(url);
		return image;
	}

	public void addButtons(JPanel toolbar) {
		for (int x = 0; x < buttonArray.length; x++) {
			int buttonWidth = toolbar.getWidth() / buttonArray.length;
			Component button = (Component) buttonArray[x];
			button.setBounds(x * (toolbar.getWidth() / buttonArray.length), 0, buttonWidth, toolbar.getHeight());
			add(button);
		}
	}

}
