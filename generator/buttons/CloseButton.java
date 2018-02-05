package buttons;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;

public class CloseButton extends JButton {
    /**
     * Generated Serial Version UID
     */
    private static final long serialVersionUID = -815599103109490478L;
    public CloseButton() {
	setBackground(Color.RED);
	setText("Close");
	addActionListener(new ActionListener() {
	    @Override
	    public void actionPerformed(ActionEvent e) {
		System.exit(0);
	    }
	});
    }
}
