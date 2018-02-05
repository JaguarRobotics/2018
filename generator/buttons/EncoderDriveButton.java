package buttons;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;

public class EncoderDriveButton extends JButton {
	/**
	 * Generated Serial Version UID
	 */
	private static final long serialVersionUID = -1352835109315952975L;

	public EncoderDriveButton() {
		setup();
	}

	private void setup() {
		setBackground(Color.BLUE);
		setText("Straight");
		addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
			}
		});
	}
}
