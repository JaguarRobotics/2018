package org.usd232.robotics.autonomous.generator;

import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.SpringLayout;

public class SideList extends Container {
    private static final long serialVersionUID = 1890289959504726076L;
    private JTextField        textField;

    public SideList() {
        setPreferredSize(new Dimension(100, 100));
        setBackground(Color.GREEN);
        SpringLayout springLayout = new SpringLayout();
        setLayout(springLayout);
        JLabel lblLabel = new JLabel("Label");
        springLayout.putConstraint(SpringLayout.NORTH, lblLabel, 10, SpringLayout.NORTH, this);
        springLayout.putConstraint(SpringLayout.WEST, lblLabel, 10, SpringLayout.WEST, this);
        add(lblLabel);
        textField = new JTextField();
        springLayout.putConstraint(SpringLayout.WEST, textField, 10, SpringLayout.WEST, this);
        add(textField);
        textField.setColumns(10);
        JButton button_1 = new JButton("+");
        springLayout.putConstraint(SpringLayout.NORTH, textField, 0, SpringLayout.NORTH, button_1);
        springLayout.putConstraint(SpringLayout.SOUTH, textField, 0, SpringLayout.SOUTH, button_1);
        springLayout.putConstraint(SpringLayout.EAST, textField, -6, SpringLayout.WEST, button_1);
        add(button_1);
        JButton button = new JButton("-");
        springLayout.putConstraint(SpringLayout.NORTH, button_1, 0, SpringLayout.NORTH, button);
        springLayout.putConstraint(SpringLayout.EAST, button_1, -6, SpringLayout.WEST, button);
        springLayout.putConstraint(SpringLayout.SOUTH, button, -10, SpringLayout.SOUTH, this);
        springLayout.putConstraint(SpringLayout.EAST, button, -10, SpringLayout.EAST, this);
        add(button);
    }
}
