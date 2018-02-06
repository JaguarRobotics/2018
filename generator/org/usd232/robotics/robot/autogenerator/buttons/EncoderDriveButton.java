package org.usd232.robotics.robot.autogenerator.buttons;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JTextField;
import org.usd232.robotics.autonomous.AutonomousStep;
import org.usd232.robotics.autonomous.Distance;
import org.usd232.robotics.autonomous.StepType;
import org.usd232.robotics.robot.autogenerator.Main;
import org.usd232.robotics.robot.autogenerator.ui.Robot;
import org.usd232.robotics.robot.autogenerator.ui.RouteDrawer;

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
                JFrame frame = new JFrame();
                frame.setLayout(null);
                frame.setBounds(0, 0, 200, 200);
                JTextField input = new JTextField();
                input.setBounds(0, 0, 200, 50);
                frame.add(input);
                JButton applyButton = new JButton("Apply");
                applyButton.setBounds(0, 50, 200, 50);
                frame.add(applyButton);
                applyButton.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        RouteDrawer.robot.setLocation(
                                        (int) (Robot.point.getX()
                                                        + (Math.cos(Robot.angle) * Integer.parseInt(input.getText()))
                                                                        / 12 / 54 * Main.screen.getWidth()),
                                        (int) (Robot.point.getY()
                                                        + (Math.sin(Robot.angle) * Integer.parseInt(input.getText())))
                                                        / 12 / 54 * Main.screen.getHeight());
                    }
                });
                JButton okayButton = new JButton("Okay");
                okayButton.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        RouteDrawer.robot.setLocation(
                                        (int) (Robot.point.getX()
                                                        + (Math.cos(Robot.angle) * Integer.parseInt(input.getText()))
                                                                        / 12 / 54 * Main.screen.getWidth()),
                                        (int) (Robot.point.getY()
                                                        + (Math.sin(Robot.angle) * Integer.parseInt(input.getText())))
                                                        / 12 / 54 * Main.screen.getHeight());
                        Robot.point.setLocation(
                                        (int) (Robot.point.getX()
                                                        + (Math.cos(Robot.angle) * Integer.parseInt(input.getText()))
                                                                        / 12 / 54 * Main.screen.getWidth()),
                                        (int) (Robot.point.getY()
                                                        + (Math.sin(Robot.angle) * Integer.parseInt(input.getText())))
                                                        / 12 / 54 * Main.screen.getHeight());
                        frame.dispose();
                        AutonomousStep step = new AutonomousStep();
                        step.setType(StepType.EncoderDrive);
                        Distance distance = new Distance();
                        distance.setDistance(Integer.parseInt(input.getText()));
                        step.setParameter(distance);
                        RouteDrawer.autonomousRoute.addStep(step);
                    }
                });
                okayButton.setBounds(0, 100, 200, 50);
                frame.add(okayButton);
                frame.setVisible(true);
            }
        });
    }
}
