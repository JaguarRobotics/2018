package org.usd232.robotics.autonomous.generator.tools;

import java.awt.Component;
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import org.usd232.robotics.autonomous.generator.FieldView;
import org.usd232.robotics.autonomous.generator.GameCoordinate;
import org.usd232.robotics.autonomous.generator.SideBar;

public class Toolbar extends Container implements MouseListener, MouseMotionListener {
    private static final long serialVersionUID = 4293111239248868095L;
    private FieldView         fieldView;
    private SideBar           sidebar;
    private boolean           mouseDown;
    private int               lastX;
    private int               lastY;

    @Override
    public void mouseClicked(MouseEvent e) {
        Tool tool = getSelectedTool();
        if (tool != null) {
            tool.onClick(GameCoordinate.fromPixels(fieldView, false, e.getX(), e.getY()));
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {
        mouseDown = true;
        lastX = e.getX();
        lastY = e.getY();
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        mouseDown = false;
    }

    @Override
    public void mouseEntered(MouseEvent e) {
    }

    @Override
    public void mouseExited(MouseEvent e) {
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        mouseMoved(e);
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        Tool tool = getSelectedTool();
        int x = e.getX();
        int y = e.getY();
        int dx = x - lastX;
        int dy = y - lastY;
        lastX = x;
        lastY = y;
        if (tool != null) {
            if (mouseDown) {
                tool.onDrag(GameCoordinate.fromPixels(fieldView, false, x, y),
                                GameCoordinate.fromPixels(fieldView, true, dx, dy));
            } else {
                tool.onHover(GameCoordinate.fromPixels(fieldView, false, x, y),
                                GameCoordinate.fromPixels(fieldView, true, dx, dy));
            }
        }
    }

    public Tool getSelectedTool() {
        for (Component component : getComponents()) {
            if (component instanceof Tool && ((Tool) component).isSelected()) {
                return (Tool) component;
            }
        }
        return null;
    }

    public FieldView getFieldView() {
        return fieldView;
    }

    public SideBar getSidebar() {
        return sidebar;
    }

    public Toolbar(FieldView fieldView, SideBar sidebar) {
        this.fieldView = fieldView;
        this.sidebar = sidebar;
        setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
        add(new MoveTool());
        add(new RotateImage());
        add(new ZoomInTool());
        add(new ZoomOutTool());
        add(new ResetView());
        add(new SleepTool());
        add(new DriveTool());
        add(new TurnTool());
        add(new CustomCommandTool());
        fieldView.addMouseListener(this);
        fieldView.addMouseMotionListener(this);
    }
}
