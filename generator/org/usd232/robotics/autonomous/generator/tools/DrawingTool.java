package org.usd232.robotics.autonomous.generator.tools;

import java.awt.event.HierarchyEvent;
import java.awt.event.HierarchyListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

public class DrawingTool extends Tool implements HierarchyListener, ListSelectionListener {
    private static final long serialVersionUID = -4579626106651887780L;

    @Override
    public void hierarchyChanged(HierarchyEvent e) {
        getToolbar().getSidebar().addListSelectionListener(this);
    }

    @Override
    public void valueChanged(ListSelectionEvent e) {
        setVisible(!getToolbar().getSidebar().route.isSelectionEmpty());
    }

    public DrawingTool(String icon) {
        super(icon);
        addHierarchyListener(this);
        setVisible(false);
    }
}
