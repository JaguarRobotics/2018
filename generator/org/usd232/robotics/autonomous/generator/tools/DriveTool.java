package org.usd232.robotics.autonomous.generator.tools;

import org.usd232.robotics.autonomous.generator.model.GeneratorModel;

public class DriveTool extends DrawingTool {
    private static final long serialVersionUID = -7087329524983618906L;
    private static final String ICON = "linear_scale";

    public DriveTool(GeneratorModel model) {
        super(ICON, model);
    }
}
