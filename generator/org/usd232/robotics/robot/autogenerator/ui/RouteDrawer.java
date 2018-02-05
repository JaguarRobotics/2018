package org.usd232.robotics.robot.autogenerator.ui;

import java.awt.Component;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

import org.usd232.robotics.robot.autogenerator.Main;
import org.usd232.robotics.robot.autogenerator.buttons.CloseButton;
import org.usd232.robotics.robot.autogenerator.buttons.DropCubeButton;
import org.usd232.robotics.robot.autogenerator.buttons.EncoderDriveButton;
import org.usd232.robotics.robot.autogenerator.buttons.GyroTurnButton;

public class RouteDrawer extends JPanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * Height of the tool bar (Temporary)
	 */
	private final int TOOLBAR_HEIGHT = 75;
	/**
	 * Amount of buttons (Temporary)
	 */
	private final Object[] buttonArray = { new DropCubeButton(), new EncoderDriveButton(), new GyroTurnButton(),
			new CloseButton() };

	public RouteDrawer() {
		ImageIcon backgroundImage = getImage("Field.png");//Gets Field Image

		//Create toolbar and add buttons
		JPanel toolbar = new JPanel();
		toolbar.setBounds(0, 0, backgroundImage.getIconWidth(), TOOLBAR_HEIGHT);
		addButtons(toolbar);
		add(toolbar);
		
		//Create background panel
		JPanel backgroundPanel = new JPanel();
		backgroundPanel.setBounds(0, toolbar.getHeight(), backgroundImage.getIconWidth(),
				backgroundImage.getIconHeight());
		JLabel label = new JLabel(backgroundImage);
		backgroundPanel.add(label);
		add(backgroundPanel);
		
		//Set bounds and visibility
		setBounds(0, 0, backgroundImage.getIconWidth(), backgroundImage.getIconHeight());
		Main.screen.setBounds(0, 0, backgroundPanel.getWidth(), backgroundPanel.getHeight() + toolbar.getHeight());
		setVisible(true);
	}
	/**
	 * Gets an Image
	 * @param url the location of the image
	 * @return The Image
	 */
	private ImageIcon getImage(String url) {
		ImageIcon image;
		image = new ImageIcon(url);
		return image;
	}
	/**
	 * Adds the buttons for the commands on the toolbar
	 * @param toolbar The toolbar to add the buttons to
	 */
	private void addButtons(JPanel toolbar) {
		for (int x = 0; x < buttonArray.length; x++) {
			int buttonWidth = toolbar.getWidth() / buttonArray.length;
			Component button = (Component) buttonArray[x];
			button.setBounds(x * (toolbar.getWidth() / buttonArray.length), 0, buttonWidth, toolbar.getHeight());
			add(button);
		}
	}
}
