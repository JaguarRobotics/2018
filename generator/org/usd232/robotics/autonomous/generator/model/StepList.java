package org.usd232.robotics.autonomous.generator.model;

import java.util.Collection;
import org.usd232.robotics.autonomous.AutonomousRoute;
import org.usd232.robotics.autonomous.AutonomousStep;
import org.usd232.robotics.autonomous.CustomCommandParameter;
import org.usd232.robotics.autonomous.DriveParameter;
import org.usd232.robotics.autonomous.SleepParameter;
import org.usd232.robotics.autonomous.TurnParameter;

public class StepList extends ListModelBase<AutonomousStep, AutonomousRoute, VersionList> {
    private static final long serialVersionUID = -659947995446035833L;

    @Override
    protected String toString(AutonomousStep obj) {
        if (obj.getType() == null) {
            return "null";
        }
        switch (obj.getType()) {
            case CustomCommand: {
                CustomCommandParameter param = (CustomCommandParameter) obj.getGenericParameter();
                return String.format("Custom command #%d (%s)", param.getCommandID(), param.getParameter());
            }
            case Drive: {
                DriveParameter param = (DriveParameter) obj.getGenericParameter();
                return String.format("Drive forward %.1f inches", param.getDistance() * parent.parentElement.obj2.getScale());
            }
            case Sleep: {
                SleepParameter param = (SleepParameter) obj.getGenericParameter();
                return String.format("Sleep for %.3f seconds", 0.001f * param.getMillis());
            }
            case Turn: {
                TurnParameter param = (TurnParameter) obj.getGenericParameter();
                return String.format("Turn %.1f\u00B0", param.getAngle() / Math.PI * 180);
            }
            default:
                throw new IllegalArgumentException("Step is of an unknown type");
        }
    }

    @Override
    protected Collection<AutonomousStep> load(AutonomousRoute element) {
        return element.getSteps();
    }

    @Override
    protected void onRemove(AutonomousStep element) {
        parentElement.removeStep(element);
    }

    @Override
    protected AutonomousStep create(String name) {
        AutonomousStep step = new AutonomousStep();
        parentElement.addStep(step);
        return step;
    }
}
