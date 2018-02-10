package org.usd232.robotics.autonomous.generator;

import java.awt.Container;
import java.awt.Dimension;
import java.awt.GridLayout;

public class SideBar extends Container {
    private static final long serialVersionUID = -8431322197209139145L;

    public SideBar() {
        setPreferredSize(new Dimension(320, 400));
        setLayout(new GridLayout(3, 1, 0, 0));
        SideList panel = new SideList("Route");
        add(panel);
        SideList panel_1 = new SideList("Parameters");
        add(panel_1);
        SideList panel_2 = new SideList("Steps");
        add(panel_2);
    }
}
