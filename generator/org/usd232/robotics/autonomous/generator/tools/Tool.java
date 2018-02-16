package org.usd232.robotics.autonomous.generator.tools;

import java.awt.Color;
import java.awt.Graphics;
import javax.swing.ImageIcon;
import javax.swing.JToggleButton;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import org.usd232.robotics.autonomous.AutonomousStep;
import org.usd232.robotics.autonomous.generator.GameCoordinate;

public class Tool extends JToggleButton implements ChangeListener {
    private static final long    serialVersionUID = -1475960958350342720L;
    private ThreadLocal<Boolean> insideStateChanged;

    @Override
    protected void paintBorder(Graphics g) {
    }

    @Override
    public boolean isSelected() {
        return super.isSelected() && !insideStateChanged.get();
    }

    @Override
    public void stateChanged(ChangeEvent e) {
        if (isSelected()) {
            insideStateChanged.set(true);
            Tool tool = getToolbar().getSelectedTool();
            if (tool != null) {
                tool.setSelected(false);
            }
            insideStateChanged.remove();
            onEnable();
        } else {
            onDisable();
        }
    }

    public Toolbar getToolbar() {
        return (Toolbar) getParent();
    }

    public void onEnable() {
    }

    public void onDisable() {
    }

    public void onHover(GameCoordinate position, GameCoordinate change) {
    }

    public void onDrag(GameCoordinate position, GameCoordinate change) {
    }

    public void onClick(GameCoordinate position) {
    }

    public void addStep(AutonomousStep step) {
        getToolbar().getSidebar().addStep(step);
    }

    public Tool(String icon) {
        insideStateChanged = new ThreadLocal<Boolean>() {
            @Override
            protected Boolean initialValue() {
                return false;
            }
        };
        setBackground(Color.WHITE);
        setFocusPainted(false);
        setIcon(new ImageIcon(Tool.class.getResource(String
                        .format("/org/usd232/robotics/autonomous/generator/resources/ic_%s_black_24dp_2x.png", icon))));
        setSelectedIcon(new ImageIcon(Tool.class.getResource(String
                        .format("/org/usd232/robotics/autonomous/generator/resources/ic_%s_white_24dp_2x.png", icon))));
        addChangeListener(this);
    }
}
