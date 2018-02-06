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
		JComboBox<Alliances> alliance = new JComboBox<>();
		alliance.addItem(Alliances.Blue);
		alliance.addItem(Alliances.Red);
		alliance.setBounds(0, 0, 150, 100);
		add(alliance);
		
		JComboBox<AllianceStation> allianceStationBox = new JComboBox<>();
		allianceStationBox.setBounds(0, 100, 150, 100);
		allianceStationBox.addItem(AllianceStation.One);
		allianceStationBox.addItem(AllianceStation.Two);
		allianceStationBox.addItem(AllianceStation.Three);
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
	public static enum Alliances {
		Red, Blue
	}
	public static enum AllianceStation {
		One, Two, Three
	}
}