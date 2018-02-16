package org.usd232.robotics.autonomous.generator.tools;

import java.awt.Component;
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import org.usd232.robotics.autonomous.generator.FieldView;
import org.usd232.robotics.autonomous.generator.GameCoordinate;
import org.usd232.robotics.autonomous.generator.PropertiesBar;
import org.usd232.robotics.autonomous.generator.model.GeneratorModel;

public class Toolbar extends Container implements MouseListener, MouseMotionListener {
    private static final long serialVersionUID = 4293111239248868095L;
    private FieldView         fieldView;
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

    public Toolbar(GeneratorModel model, FieldView fieldView, PropertiesBar props) {
        this.fieldView = fieldView;
        setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
        add(new MoveTool(model));
        add(new RotateImage(model));
        add(new ZoomInTool(model));
        add(new ZoomOutTool(model));
        add(new ResetView(model));
        add(new SleepTool(model));
        add(new DriveTool(model, props));
        add(new TurnTool(model));
        add(new CustomCommandTool(model));
        fieldView.addMouseListener(this);
        fieldView.addMouseMotionListener(this);
        fieldView.setToolbar(this);
    }
}
