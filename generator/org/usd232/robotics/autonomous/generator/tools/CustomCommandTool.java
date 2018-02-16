package org.usd232.robotics.autonomous.generator.tools;

import org.usd232.robotics.autonomous.AutonomousStep;
import org.usd232.robotics.autonomous.StepType;
import org.usd232.robotics.autonomous.generator.model.GeneratorModel;

public class CustomCommandTool extends DrawingTool {
    private static final long   serialVersionUID = 6594165557584203970L;
    private static final String ICON             = "extension";

    @Override
    public void onEnable() {
        AutonomousStep step = new AutonomousStep();
        step.setType(StepType.CustomCommand);
        addStep(step);
        setSelected(false);
    }

    public CustomCommandTool(GeneratorModel model) {
        super(ICON, model);
    }
}
