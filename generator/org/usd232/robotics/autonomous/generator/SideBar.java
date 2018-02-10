package org.usd232.robotics.autonomous.generator;

import java.awt.Container;
import java.awt.Dimension;
import java.awt.GridLayout;
import javax.swing.SpringLayout;

public class SideBar extends Container {
    private static final long serialVersionUID = -8431322197209139145L;

    public SideBar() {
        setPreferredSize(new Dimension(320, 400));
        setLayout(new GridLayout(3, 1, 0, 0));
        SideList panel = new SideList();
        add(panel);
        SpringLayout sl_panel = new SpringLayout();
        SideList panel_1 = new SideList();
        add(panel_1);
        SpringLayout sl_panel_1 = new SpringLayout();
        SideList panel_2 = new SideList();
        add(panel_2);
        SpringLayout sl_panel_2 = new SpringLayout();
    }
}
