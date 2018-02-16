package org.usd232.robotics.autonomous.generator.tools;

import org.usd232.robotics.autonomous.generator.model.GeneratorModel;

public class RotateImage extends Tool {
    private static final long   serialVersionUID = -2152990834180811625L;
    private static final String ICON             = "rotate_90_degrees_ccw";

    @Override
    public void onEnable() {
        getToolbar().getFieldView().getTransformation().rotate(-Math.PI / 2, 0.5, 0.5);
        setSelected(false);
    }

    public RotateImage(GeneratorModel model) {
        super(ICON, model);
    }
}
