package org.usd232.robotics.robot.autogenerator.ui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JPanel;

import org.usd232.robotics.robot.autogenerator.Main;

public class Startup extends JPanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public Startup() {
		Main.screen.setLayout(null);
		setLayout(null);
		setBounds(0, 0, 150,300);
		Main.screen.setTitle("Autonomous Chooser 2018");
		Main.screen.setBounds(0, 0, 150, 300);
		//Create Menus and button and add options
		JComboBox<alliances> alliance = new JComboBox<>();
		alliance.addItem(alliances.Blue);
		alliance.addItem(alliances.Red);
		alliance.setBounds(0, 0, 150, 100);
		add(alliance);
		
		JComboBox<allianceStation> allianceStationBox = new JComboBox<>();
		allianceStationBox.setBounds(0, 100, 150, 100);
		allianceStationBox.addItem(allianceStation.One);
		allianceStationBox.addItem(allianceStation.Two);
		allianceStationBox.addItem(allianceStation.Three);
		add(allianceStationBox);
		
		JButton go = new JButton("Go");
		go.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Main.screen.setVisible(false);
				Main.screen.remove(Main.startup);
				Main.screen.add(new RouteDrawer(alliance.getSelectedItem(), allianceStationBox.getSelectedItem()));
			}
		});
		go.setBounds(0, 200, 150, 100);
		add(go);
		
		//Set bounds and visibility
		setVisible(true);
	}
	private enum alliances {
		Red, Blue
	}
	private enum allianceStation {
		One, Two, Three
	}
}