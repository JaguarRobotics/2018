package org.usd232.robotics.autonomous.generator;

import java.awt.Container;
import java.awt.Dimension;
import java.awt.GridLayout;
import org.usd232.robotics.autonomous.generator.model.LocalDatabase;
import org.usd232.robotics.autonomous.generator.model.ParameterList;
import org.usd232.robotics.autonomous.generator.model.StepList;
import org.usd232.robotics.autonomous.generator.model.VersionList;

public class SideBar extends Container {
    private static final long serialVersionUID = -8431322197209139145L;

    public SideBar() {
        setPreferredSize(new Dimension(320, 400));
        setLayout(new GridLayout(4, 1, 0, 0));
        SideList name = new SideList("Route Name:", new LocalDatabase(), true, true, null);
        add(name);
        SideList route = new SideList("Route Versions:", new VersionList(), false, true, name);
        add(route);
        SideList params = new SideList("Version Parameters:", new ParameterList(), true, true, route);
        add(params);
        SideList steps = new SideList("Autonomous Steps:", new StepList(), false, false, route);
        add(steps);
    }
}
