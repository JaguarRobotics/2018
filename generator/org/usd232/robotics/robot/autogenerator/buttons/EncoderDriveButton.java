package org.usd232.robotics.robot.autogenerator.buttons;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;

import javax.swing.JButton;

import org.usd232.robotics.robot.autogenerator.Main;

public class EncoderDriveButton extends JButton {
	Point pointStart = null;
	Point pointEnd = null;
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
				drawLine();
			}
		});
	}

	public void drawLine() {
		pointStart = null;
		pointEnd = null;

		Main.screen.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent e) {
				pointStart = e.getPoint();
				System.out.println("Pressed");
			}

			public void mouseReleased(MouseEvent e) {
//				pointStart = null;
			}
		});
		Main.screen.addMouseMotionListener(new MouseMotionAdapter() {
			public void mouseMoved(MouseEvent e) {
				pointEnd = e.getPoint();
			}

			public void mouseDragged(MouseEvent e) {
				pointEnd = e.getPoint();
				Main.screen.repaint();
			}
		});
	}

	public void paint(Graphics g) {
		super.paint(g);
		if (pointStart != null) {
			g.setColor(Color.RED);
			g.drawLine(pointStart.x, pointStart.y, pointEnd.x, pointEnd.y);
		}
	}
};
