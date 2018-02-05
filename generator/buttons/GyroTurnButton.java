package buttons;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;

public class GyroTurnButton extends JButton {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4066361635371770579L;

	public GyroTurnButton() {
		setup();
	}

	private void setup() {
		setBackground(Color.ORANGE);
		setText("Turn");
		addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
			}
		});
	}
}
