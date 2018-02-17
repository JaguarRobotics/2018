package org.usd232.robotics.autonomous.generator.tools;

import org.usd232.robotics.autonomous.AutonomousStep;
import org.usd232.robotics.autonomous.SleepParameter;
import org.usd232.robotics.autonomous.generator.model.GeneratorModel;

public class SleepTool extends DrawingTool {
    private static final long   serialVersionUID = -801551249243642901L;
    private static final String ICON             = "timer";

    @Override
    public void onEnable() {
        AutonomousStep step = new AutonomousStep();
        SleepParameter param = new SleepParameter();
        param.setMillis((short) 1000);
        step.setParameter(param);
        addStep(step);
        setSelected(false);
    }

    public SleepTool(GeneratorModel model) {
        super(ICON, model);
    }
}
