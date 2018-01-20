package org.usd232.robotics.autonomous;

/**
 * Represents a single step that the autonomous route must take.
 * 
 * @author Zach Deibert
 * @since 2018
 * @version 2018
 */
public class AutonomousStep {
    /**
     * The type of step this is
     * 
     * @since 2018
     */
    private StepType                 type;
    /**
     * The parameter to this step
     * 
     * @since 2018
     */
    private IAutonomousStepParameter param;

    /**
     * Gets the type of step this is
     * 
     * @return The type of step
     * @since 2018
     */
    public StepType getType() {
        return type;
    }

    /**
     * Sets the type of step this is
     * 
     * @param type
     *            The type of step
     * @since 2018
     */
    public void setType(StepType type) {
        if (type == null) {
            throw new NullPointerException("type cannot be null");
        }
        this.type = type;
        switch (type) {
            case Sleep:
                if (!(param instanceof SleepParameter)) {
                    setParameter(new SleepParameter());
                }
                break;
            case Bezier:
                if (!(param instanceof BezierCurve)) {
                    setParameter(new BezierCurve());
                }
                break;
            case CustomCommand:
                if (!(param instanceof CustomCommandParameter)) {
                }
                break;
        }
    }

    /**
     * Gets the parameter to this step
     * 
     * @return The parameter to this step
     * @since 2018
     */
    public IAutonomousStepParameter getGenericParameter() {
        return param;
    }

    /**
     * Sets the parameter to this step
     * 
     * @param param
     *            The parameter to this step
     * @since 2018
     */
    public void setParameter(IAutonomousStepParameter param) {
        if (param == null) {
            throw new NullPointerException("param cannot be null");
        }
        if (param instanceof SleepParameter) {
            setType(StepType.Sleep);
            this.param = param;
        } else if (param instanceof BezierCurve) {
            setType(StepType.Bezier);
            this.param = param;
        } else if (param instanceof CustomCommandParameter) {
            setType(StepType.CustomCommand);
            this.param = param;
        } else {
            throw new ClassCastException("Invalid parameter type");
        }
    }
}
