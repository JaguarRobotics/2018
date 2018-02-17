package org.usd232.robotics.autonomous.generator.tools;

import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import org.usd232.robotics.autonomous.generator.model.GeneratorModel;

public class DrawingTool extends Tool implements ListSelectionListener {
    private static final long serialVersionUID = -4579626106651887780L;

    @Override
    public void valueChanged(ListSelectionEvent e) {
        setVisible(!model.versionListView.isSelectionEmpty());
    }

    public DrawingTool(String icon, GeneratorModel model) {
        super(icon, model);
        setVisible(false);
        model.addListSelectionListener(this);
    }
}
