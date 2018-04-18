package org.usd232.robotics.powerup;

import org.usd232.robotics.powerup.calibration.CalibrateCommand;
import org.usd232.robotics.powerup.climb.ClimbDown;
import org.usd232.robotics.powerup.climb.ClimbUp;
import org.usd232.robotics.powerup.intake.DropCube;
import org.usd232.robotics.powerup.intake.GrabCube;
import org.usd232.robotics.powerup.intake.LowerIntake;
import org.usd232.robotics.powerup.intake.RaiseIntake;
import org.usd232.robotics.powerup.lift.GoToLevel;
import org.usd232.robotics.powerup.lift.ManualLower;
import org.usd232.robotics.powerup.lift.ManualRaise;
import org.usd232.robotics.powerup.lift.RaiseToScale;
import org.usd232.robotics.powerup.log.Logger;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.buttons.Button;
import edu.wpi.first.wpilibj.buttons.JoystickButton;
import edu.wpi.first.wpilibj.buttons.Trigger;
import edu.wpi.first.wpilibj.command.Command;

/**
 * This class is the glue that binds the controls on the physical operator interface to the commands and command groups
 * that allow control of the robot.
 * 
 * @author Brian, Zach, Cody, Max
 * @since 2018
 * @version 2018
 */
public class OI extends Trigger implements RobotMap {
    /**
     * The Logger.
     * 
     * @since 2018
     * @version 2018
     */
    private static final Logger LOG = new Logger();

    public OI() {
        LOG.catchAll(()-> {
            CalibratorXbox_A.whenPressed(new CalibrateCommand());
            ManipulatorXbox_RB.whileHeld(new ManualRaise());
            ManipulatorXbox_LB.whileHeld(new ManualLower());
            try {
                whenPovIs(Manipulator, 0, new RaiseToScale());
                whenPovIs(Manipulator, 6, new GoToLevel(()->Robot.calibratorData.getLiftSwitch()));
                whenPovIs(Manipulator, 4, new GoToLevel(()->Robot.calibratorData.getLiftBottom()));
                LOG.info("The POV controls were successfuly created");
            } catch (Exception e) {
                LOG.info("The POV controls failed to be created");
            }
            whileGreaterThan(Manipulator, 2, .1, new ClimbUp(()->Manipulator.getRawAxis(2)));
            whileGreaterThan(Manipulator, 3, .1, new ClimbDown(()->-Manipulator.getRawAxis(3)));
            ManipulatorXbox_Y.whenPressed(new RaiseIntake());
            ManipulatorXbox_A.whenPressed(new LowerIntake());
            ManipulatorXbox_X.whenPressed(new GrabCube());
            ManipulatorXbox_B.whenPressed(new DropCube());
        });
    }

    // The controllers we are using this year
    public final Joystick Joystick0              = LOG.catchAll(()->new Joystick(LEFT_JOYSTICK_PORT));
    public final Joystick Joystick1              = LOG.catchAll(()->new Joystick(RIGHT_JOYSTICK_PORT));
    public final Joystick Manipulator            = LOG.catchAll(()->new Joystick(MANIPULATOR_JOYSTICK_PORT));
    public final Joystick Calibrator             = LOG.catchAll(()->new Joystick(CALIBRATION_JOYSTICK_PORT));
    // Buttons here are as labeled on the controllers.
    public final Button   Joystick0_Button1      = LOG.catchAll(()->new JoystickButton(Joystick0, 1));
    public final Button   Joystick0_Button2      = LOG.catchAll(()->new JoystickButton(Joystick0, 2));
    public final Button   Joystick0_Button3      = LOG.catchAll(()->new JoystickButton(Joystick0, 3));
    public final Button   Joystick0_Button4      = LOG.catchAll(()->new JoystickButton(Joystick0, 4));
    public final Button   Joystick0_Button5      = LOG.catchAll(()->new JoystickButton(Joystick0, 5));
    public final Button   Joystick0_Button6      = LOG.catchAll(()->new JoystickButton(Joystick0, 6));
    public final Button   Joystick0_Button7      = LOG.catchAll(()->new JoystickButton(Joystick0, 7));
    public final Button   Joystick0_Button8      = LOG.catchAll(()->new JoystickButton(Joystick0, 8));
    public final Button   Joystick0_Button9      = LOG.catchAll(()->new JoystickButton(Joystick0, 9));
    public final Button   Joystick0_Button10     = LOG.catchAll(()->new JoystickButton(Joystick0, 10));
    public final Button   Joystick0_Button11     = LOG.catchAll(()->new JoystickButton(Joystick0, 11));
    public final Button   Joystick1_Button1      = LOG.catchAll(()->new JoystickButton(Joystick1, 1));
    public final Button   Joystick1_Button2      = LOG.catchAll(()->new JoystickButton(Joystick1, 2));
    public final Button   Joystick1_Button3      = LOG.catchAll(()->new JoystickButton(Joystick1, 3));
    public final Button   Joystick1_Button4      = LOG.catchAll(()->new JoystickButton(Joystick1, 4));
    public final Button   Joystick1_Button5      = LOG.catchAll(()->new JoystickButton(Joystick1, 5));
    public final Button   Joystick1_Button6      = LOG.catchAll(()->new JoystickButton(Joystick1, 6));
    public final Button   Joystick1_Button7      = LOG.catchAll(()->new JoystickButton(Joystick1, 7));
    public final Button   Joystick1_Button8      = LOG.catchAll(()->new JoystickButton(Joystick1, 8));
    public final Button   Joystick1_Button9      = LOG.catchAll(()->new JoystickButton(Joystick1, 9));
    public final Button   Joystick1_Button10     = LOG.catchAll(()->new JoystickButton(Joystick1, 10));
    public final Button   Joystick1_Button11     = LOG.catchAll(()->new JoystickButton(Joystick1, 11));
    // Xbox Controls
    public final Button   ManipulatorXbox_A      = LOG.catchAll(()->new JoystickButton(Manipulator, 1));
    public final Button   ManipulatorXbox_B      = LOG.catchAll(()->new JoystickButton(Manipulator, 2));
    public final Button   ManipulatorXbox_X      = LOG.catchAll(()->new JoystickButton(Manipulator, 3));
    public final Button   ManipulatorXbox_Y      = LOG.catchAll(()->new JoystickButton(Manipulator, 4));
    public final Button   ManipulatorXbox_LB     = LOG.catchAll(()->new JoystickButton(Manipulator, 5));
    public final Button   ManipulatorXbox_RB     = LOG.catchAll(()->new JoystickButton(Manipulator, 6));
    public final Button   ManipulatorXbox_Back   = LOG.catchAll(()->new JoystickButton(Manipulator, 7));
    public final Button   ManipulatorXbox_Start  = LOG.catchAll(()->new JoystickButton(Manipulator, 8));
    public final Button   ManipulatorXbox_LStick = LOG.catchAll(()->new JoystickButton(Manipulator, 9));
    public final Button   ManipulatorXbox_RStick = LOG.catchAll(()->new JoystickButton(Manipulator, 10));
    public final Button   CalibratorXbox_A       = LOG.catchAll(()->new JoystickButton(Calibrator, 1));
    public final Button   CalibratorXbox_B       = LOG.catchAll(()->new JoystickButton(Calibrator, 2));
    public final Button   CalibratorXbox_X       = LOG.catchAll(()->new JoystickButton(Calibrator, 3));
    public final Button   CalibratorXbox_Y       = LOG.catchAll(()->new JoystickButton(Calibrator, 4));
    public final Button   CalibratorXbox_LB      = LOG.catchAll(()->new JoystickButton(Calibrator, 5));
    public final Button   CalibratorXbox_RB      = LOG.catchAll(()->new JoystickButton(Calibrator, 6));
    public final Button   CalibratorXbox_Back    = LOG.catchAll(()->new JoystickButton(Calibrator, 7));
    public final Button   CalibratorXbox_Start   = LOG.catchAll(()->new JoystickButton(Calibrator, 8));
    public final Button   CalibratorXBox_LStick  = LOG.catchAll(()->new JoystickButton(Calibrator, 9));
    public final Button   CalibratorXbox_RStick  = LOG.catchAll(()->new JoystickButton(Calibrator, 10));

    abstract class Scheduler extends ButtonScheduler {
        @Override
        public void start() {
            super.start();
        }
    }

    @Override
    public boolean get() {
        return false;
    }

    /**
     * When an axis is less than a certain value run a certain command.
     * 
     * @param joystick
     *            What joystick the axis is on.
     * @param axis
     *            What axis to look at.
     * @param value
     *            What value should the axis be less than to run the command.
     * @param command
     *            The command that should be ran when the axis is less than the specified value.
     */
    public void whenLessThan(Joystick joystick, int axis, double value, Command command) {
        new Scheduler() {
            private boolean pressedLast = joystick.getRawAxis(axis) < value;

            @Override
            public void execute() {
                if (joystick.getRawAxis(axis) < value) {
                    if (!pressedLast) {
                        pressedLast = true;
                        command.start();
                    }
                } else {
                    pressedLast = false;
                }
            }
        }.start();
    }

    /**
     * When an axis is greater than a certain value run a certain command.
     * 
     * @param joystick
     *            What joystick the axis is on.
     * @param axis
     *            What axis to look at.
     * @param value
     *            What value should the axis be greater than to run the command.
     * @param command
     *            The command that should be ran when the axis is greater than the specified value.
     */
    public void whenGreaterThan(Joystick joystick, int axis, double value, Command command) {
        new Scheduler() {
            private boolean pressedLast = joystick.getRawAxis(axis) > value;

            @Override
            public void execute() {
                if (joystick.getRawAxis(axis) > value) {
                    if (!pressedLast) {
                        pressedLast = true;
                        command.start();
                    }
                } else {
                    pressedLast = false;
                }
            }
        }.start();
    }

    /**
     * While an axis is greater than a certain value run a certain command.
     * 
     * @param joystick
     *            What joystick the axis is on.
     * @param axis
     *            What axis to look at.
     * @param value
     *            What value should the axis be greater than to run the command.
     * @param command
     *            The command that should be ran while the axis is greater than the specified value.
     */
    public void whileGreaterThan(Joystick joystick, int axis, double value, Command command) {
        new Scheduler() {
            private boolean pressedLast = joystick.getRawAxis(axis) < value;

            @Override
            public void execute() {
                if (joystick.getRawAxis(axis) > value) {
                    if (!pressedLast) {
                        pressedLast = true;
                        command.start();
                    }
                } else {
                    if (pressedLast) {
                        command.cancel();
                        pressedLast = false;
                    }
                }
            }
        }.start();
    }

    /**
     * When a POV is at a certain value run a certain command.
     * 
     * @param joystick
     *            What joystick the POV is on.
     * @param value
     *            What value should the POV should be to run the command.
     * @param command
     *            The command that should be ran while the POV is equal to the specified value.
     */
    public void whenPovIs(Joystick joystick, int value, Command command) {
        new Scheduler() {
            @Override
            public void execute() {
                int currentValue = (int) (((joystick.getPOV() + 22.5) % 360) / 45);
                if (joystick.getPOV() == -1) {
                } else {
                    if (currentValue == value) {
                        command.start();
                    }
                }
            }
        }.start();
    }
}
