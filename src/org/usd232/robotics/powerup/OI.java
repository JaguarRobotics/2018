package org.usd232.robotics.powerup;

import org.usd232.robotics.powerup.calibration.CalibrateCommand;
import org.usd232.robotics.powerup.drive.GearShiftHigh;
import org.usd232.robotics.powerup.drive.GearShiftLow;
// import org.usd232.robotics.powerup.lift.ManualLower;
// import org.usd232.robotics.powerup.lift.ManualRaise;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.buttons.Button;
import edu.wpi.first.wpilibj.buttons.JoystickButton;

/**
 * This class is the glue that binds the controls on the physical operator interface to the commands and command groups
 * that allow control of the robot.
 * 
 * @author Everyone
 * @since Always
 * @version 2018
 */
public class OI implements RobotMap {
    public OI() {
        Joystick0_Button11.whenPressed(new CalibrateCommand());
        Joystick1_Button11.whenPressed(new CalibrateCommand());
        Joystick0_Button3.whenPressed(new GearShiftHigh());
        Joystick1_Button3.whenPressed(new GearShiftHigh());
        Joystick0_Button2.whenPressed(new GearShiftLow());
        Joystick1_Button2.whenPressed(new GearShiftLow());
        // Joystick0_Button3.whileHeld(new ManualRaise());
        // Joystick0_Button3.whileHeld(new ManualLower());
        // Joystick1_Button3.whileHeld(new ManualRaise());
        // Joystick1_Button3.whileHeld(new ManualLower());
    }

    // The controllers we are using this year
    public final Joystick Joystick0              = new Joystick(LEFT_JOYSTICK_PORT);
    public final Joystick Joystick1              = new Joystick(RIGHT_JOYSTICK_PORT);
    public final Joystick Manipulator            = new Joystick(MANIPULATOR_JOYSTICK_PORT);
    // Buttons here are as labeled on the controllers.
    public final Button   Joystick0_Button1      = new JoystickButton(Joystick0, 1);
    public final Button   Joystick0_Button2      = new JoystickButton(Joystick0, 2);
    public final Button   Joystick0_Button3      = new JoystickButton(Joystick0, 3);
    public final Button   Joystick0_Button4      = new JoystickButton(Joystick0, 4);
    public final Button   Joystick0_Button5      = new JoystickButton(Joystick0, 5);
    public final Button   Joystick0_Button6      = new JoystickButton(Joystick0, 6);
    public final Button   Joystick0_Button7      = new JoystickButton(Joystick0, 7);
    public final Button   Joystick0_Button8      = new JoystickButton(Joystick0, 8);
    public final Button   Joystick0_Button9      = new JoystickButton(Joystick0, 9);
    public final Button   Joystick0_Button10     = new JoystickButton(Joystick0, 10);
    public final Button   Joystick0_Button11     = new JoystickButton(Joystick0, 11);
    public final Button   Joystick1_Button1      = new JoystickButton(Joystick1, 1);
    public final Button   Joystick1_Button2      = new JoystickButton(Joystick1, 2);
    public final Button   Joystick1_Button3      = new JoystickButton(Joystick1, 3);
    public final Button   Joystick1_Button4      = new JoystickButton(Joystick1, 4);
    public final Button   Joystick1_Button5      = new JoystickButton(Joystick1, 5);
    public final Button   Joystick1_Button6      = new JoystickButton(Joystick1, 6);
    public final Button   Joystick1_Button7      = new JoystickButton(Joystick1, 7);
    public final Button   Joystick1_Button8      = new JoystickButton(Joystick1, 8);
    public final Button   Joystick1_Button9      = new JoystickButton(Joystick1, 9);
    public final Button   Joystick1_Button10     = new JoystickButton(Joystick1, 10);
    public final Button   Joystick1_Button11     = new JoystickButton(Joystick1, 11);
    // Xbox Controls
    public final Button   ManipulatorXbox_A      = new JoystickButton(Manipulator, 1);
    public final Button   ManipulatorXbox_B      = new JoystickButton(Manipulator, 2);
    public final Button   ManipulatorXbox_X      = new JoystickButton(Manipulator, 3);
    public final Button   ManipulatorXbox_Y      = new JoystickButton(Manipulator, 4);
    public final Button   ManipulatorXbox_LB     = new JoystickButton(Manipulator, 5);
    public final Button   ManipulatorXbox_RB     = new JoystickButton(Manipulator, 6);
    public final Button   ManipulatorXbox_Back   = new JoystickButton(Manipulator, 7);
    public final Button   ManipulatorXbox_Start  = new JoystickButton(Manipulator, 8);
    public final Button   ManipulatorXbox_LStick = new JoystickButton(Manipulator, 9);
    public final Button   ManipulatorXbox_RStick = new JoystickButton(Manipulator, 10);
}
