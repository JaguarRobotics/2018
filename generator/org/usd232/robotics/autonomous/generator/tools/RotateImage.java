package org.usd232.robotics.autonomous.generator.tools;

import org.usd232.robotics.autonomous.generator.FieldView;

public class RotateImage extends Tool {
    private static final long   serialVersionUID = -2152990834180811625L;
    private static final String ICON             = "rotate_90_degrees_ccw";

    public RotateImage() {
        super(ICON);
    }
    
    @Override
    public void onEnable() {
        FieldView view = getToolbar().getFieldView();
        view.setRotation(-.5);
        super.onEnable();
        setSelected(false);
    }
}