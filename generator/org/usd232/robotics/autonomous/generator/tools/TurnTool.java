package org.usd232.robotics.autonomous.generator.tools;

import org.usd232.robotics.autonomous.AutonomousStep;
import org.usd232.robotics.autonomous.TurnParameter;

public class TurnTool extends DrawingTool {
    private static final long   serialVersionUID = 8763962239026604720L;
    private static final String ICON             = "rotate_left";

    @Override
    public void onEnable() {
        AutonomousStep step = new AutonomousStep();
        TurnParameter param = new TurnParameter();
        param.setAngle((float) (Math.PI / 2));
        step.setParameter(param);
        addStep(step);
        setSelected(false);
    }

    public TurnTool() {
        super(ICON);
    }
}
