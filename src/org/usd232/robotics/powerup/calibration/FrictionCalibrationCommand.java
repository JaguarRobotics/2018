package org.usd232.robotics.powerup.calibration;

import org.usd232.robotics.powerup.Robot;
import org.usd232.robotics.powerup.commands.CommandBase;
import org.usd232.robotics.powerup.drive.DriveTurn;
import org.usd232.robotics.powerup.log.Logger;
import org.usd232.robotics.powerup.subsystems.LocationSubsystem;
import edu.wpi.first.wpilibj.command.CommandGroup;

public class FrictionCalibrationCommand extends CommandGroup {
    /**
     * The logger.
     * 
     * @since 2018
     * @version 2018
     */
    private static final Logger       LOG             = new Logger();
    /**
     * The time in seconds to accelerate the robot.
     * 
     * @since 2018
     * @version 2018
     */
    private static final double       ACCELERATE_TIME = 5;
    /**
     * The cutoff value of the speed.
     * 
     * @since 2018
     * @version 2018
     */
    private static final double       SPEED_CUTOFF    = 0.01;
    /**
     * The amount of data samples to collect.
     * 
     * @since 2018
     * @version 2018
     */
    private static final int          MAX_SAMPLES     = 1;
    /**
     * The speed to turn the robot at.
     * 
     * @since 2018
     * @version 2018
     */
    private static final double       TURN_SPEED      = 0.6;
    /**
     * The context of the location subsystem (Where the robot is)
     * 
     * @since 2018
     * @version 2018
     */
    private LocationSubsystem.Context location;

    /**
     * Drives the robot forward for getting the amount of friction.
     * 
     * @author Zach
     * @since 2018
     * @version 2018
     */
    private class DriveCommand extends CommandBase {
        /**
         * {@inheritDoc}
         */
        @Override
        protected void initialize() {
            LOG.catchAll(()-> {
                LOG.info("Driving forward for %s seconds in order to test friction", ACCELERATE_TIME);
            });
        }

        /**
         * {@inheritDoc}
         */
        @Override
        protected void execute() {
            LOG.catchAll(()-> {
                driveSubsystem.driveTank(1, 1);
            });
        }

        /**
         * {@inheritDoc}
         */
        @Override
        protected boolean isFinished() {
            return false;
        }

        /**
         * {@inheritDoc}
         */
        @Override
        protected void end() {
            LOG.catchAll(()-> {
                LOG.info("Done driving forward.  Now testing the deceleration.");
            });
        }

        /**
         * The constructor of the drive command for frictional calibration.
         * 
         * @since 2018
         * @version 2018
         */
        public DriveCommand() {
            requires(driveSubsystem);
        }
    }

    /**
     * Measures the total acceleration after shutting the motors off
     * 
     * @author Zach
     * @since 2018
     * @version 2018
     */
    private class MeasureCommand extends CommandBase {
        /**
         * The start time of the command.
         * 
         * @since 2018
         * @version 2018
         */
        private long   startTime;
        /**
         * The speed at the time of the command.
         * 
         * @since 2018
         * @version 2018
         */
        private double startSpeed;

        /**
         * {@inheritDoc}
         */
        @Override
        protected void initialize() {
            LOG.catchAll(()-> {
                startTime = System.currentTimeMillis();
                startSpeed = location.getSpeed();
            });
        }

        /**
         * {@inheritDoc}
         */
        @Override
        protected void execute() {
            LOG.catchAll(()-> {
                driveSubsystem.driveTank(0, 0);
            });
        }

        /**
         * {@inheritDoc}
         */
        @Override
        protected boolean isFinished() {
            return LOG.catchAll(()-> {
                return location.getSpeed() < SPEED_CUTOFF;
            }, true);
        }

        /**
         * {@inheritDoc}
         */
        @Override
        protected void end() {
            LOG.catchAll(()-> {
                double time = ((double) (System.currentTimeMillis() - startTime)) / 1000.0;
                double accel = startSpeed / time;
                LOG.debug("It took %f seconds to slow down from %s in/s to 0 (frictional force of %f in/s^2).", time,
                                startSpeed, accel);
                totalAcceleration += accel;
                ++trials;
                LOG.debug("Now the average is %f in/s^2 with %d trials.", totalAcceleration / trials, trials);
            });
        }

        /**
         * Constructor for the command to measure the acceleration of the robot
         * 
         * @since 2018
         * @version 2018
         */
        public MeasureCommand() {
            requires(driveSubsystem);
            requires(locationSubsystem);
        }
    }

    /**
     * The total acceleration of the robot.
     * 
     * @since 2018
     * @version 2018
     */
    private double totalAcceleration;
    /**
     * The amount of trials completed for getting the acceleration
     * 
     * @since 2018
     * @version 2018
     */
    private int    trials;

    /**
     * {@inheritDoc}
     */
    @Override
    protected void initialize() {
        LOG.catchAll(()-> {
            totalAcceleration = 0;
            trials = 0;
        });
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void interrupted() {
        LOG.catchAll(()-> {
            if (trials == 0) {
                LOG.warn("No trials finished, so no data is being written.");
            } else {
                double accel = totalAcceleration / trials;
                LOG.info("Final frictional acceleration was determined to be %f in/s^2 after %d trials.", accel,
                                trials);
                Robot.calibratorData.setFrictionalAcceleration(accel);
                Calibration.writeToFile(Robot.calibratorData);
            }
        });
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void end() {
        LOG.catchAll(()-> {
            interrupted();
        });
    }

    /**
     * The constructor for the command to calculate the friction between the robot and the ground
     * 
     * @since 2018
     * @version 2018
     */
    public FrictionCalibrationCommand() {
        location = CommandBase.locationSubsystem.new Context();
        DriveCommand drive = new DriveCommand();
        MeasureCommand measure = new MeasureCommand();
        DriveTurn turn = new DriveTurn(t->TURN_SPEED, Math.PI);
        for (int i = 0; i < MAX_SAMPLES; ++i) {
            addSequential(drive);
            addSequential(measure);
            addSequential(turn);
        }
    }
}
