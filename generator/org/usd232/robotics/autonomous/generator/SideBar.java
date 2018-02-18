package org.usd232.robotics.autonomous.generator;

import java.awt.Container;
import java.awt.Dimension;
import java.awt.GridLayout;
import org.usd232.robotics.autonomous.generator.model.GeneratorModel;

public class SideBar extends Container {
    private static final long serialVersionUID = -8431322197209139145L;

    public SideBar(GeneratorModel model) {
        setPreferredSize(new Dimension(320, 400));
        setLayout(new GridLayout(4, 1, 0, 0));
        add(model.localDatabaseView);
        add(model.versionListView);
        add(model.parameterListView);
        add(model.stepListView);
    }
}
