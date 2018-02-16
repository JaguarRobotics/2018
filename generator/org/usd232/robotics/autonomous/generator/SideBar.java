package org.usd232.robotics.autonomous.generator;

import java.awt.Container;
import java.awt.Dimension;
import java.awt.GridLayout;
import javax.swing.event.ListSelectionListener;
import org.usd232.robotics.autonomous.AutonomousStep;
import org.usd232.robotics.autonomous.generator.model.LocalDatabase;
import org.usd232.robotics.autonomous.generator.model.ParameterList;
import org.usd232.robotics.autonomous.generator.model.StepList;
import org.usd232.robotics.autonomous.generator.model.VersionList;

public class SideBar extends Container {
    private static final long serialVersionUID = -8431322197209139145L;
    public final SideList     name;
    public final SideList     route;
    public final SideList     params;
    public final SideList     steps;
    private StepList          stepsList;

    public void addListSelectionListener(ListSelectionListener listener) {
        name.addListSelectionListener(listener);
        route.addListSelectionListener(listener);
        params.addListSelectionListener(listener);
        steps.addListSelectionListener(listener);
    }

    public void removeListSelectionListener(ListSelectionListener listener) {
        name.removeListSelectionListener(listener);
        route.removeListSelectionListener(listener);
        params.removeListSelectionListener(listener);
        steps.removeListSelectionListener(listener);
    }

    public void addStep(AutonomousStep step) {
        stepsList.add(null).setParameter(step.getGenericParameter());
    }

    public SideBar() {
        setPreferredSize(new Dimension(320, 400));
        setLayout(new GridLayout(4, 1, 0, 0));
        name = new SideList("Route Name:", new LocalDatabase(), true, true, null);
        add(name);
        route = new SideList("Route Versions:", new VersionList(), false, true, name);
        add(route);
        params = new SideList("Version Parameters:", new ParameterList(), true, true, route);
        add(params);
        stepsList = new StepList();
        steps = new SideList("Autonomous Steps:", stepsList, false, false, route);
        add(steps);
    }
}
