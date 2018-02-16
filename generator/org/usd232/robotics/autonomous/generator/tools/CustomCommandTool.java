package org.usd232.robotics.autonomous.generator.tools;

import org.usd232.robotics.autonomous.generator.model.GeneratorModel;

public class CustomCommandTool extends DrawingTool {
    private static final long serialVersionUID = 6594165557584203970L;
    private static final String ICON = "extension";

    public CustomCommandTool(GeneratorModel model) {
        super(ICON, model);
    }
}
