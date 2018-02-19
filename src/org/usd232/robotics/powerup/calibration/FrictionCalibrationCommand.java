package org.usd232.robotics.powerup.calibration;

import org.usd232.robotics.powerup.Robot;
import org.usd232.robotics.powerup.RobotMap;
import org.usd232.robotics.powerup.commands.CommandBase;
import org.usd232.robotics.powerup.drive.DriveTurn;
import org.usd232.robotics.powerup.log.Logger;
import org.usd232.robotics.powerup.subsystems.LocationSubsystem;
import edu.wpi.first.wpilibj.command.CommandGroup;

public class FrictionCalibrationCommand extends CommandGroup {
    private static final Logger       LOG             = new Logger();
    private static final double       ACCELERATE_TIME = 5;
    private static final double       SPEED_CUTOFF    = 0.01;
    private static final int          MAX_SAMPLES     = 1;
    private static final double       TURN_SPEED      = 0.6;
    private LocationSubsystem.Context location;

    private class DriveCommand extends CommandBase {
        @Override
        protected void initialize() {
            LOG.catchAll(()-> {
                LOG.info("Driving forward for %s seconds in order to test friction", ACCELERATE_TIME);
            });
        }

        @Override
        protected void execute() {
            LOG.catchAll(()-> {
                driveSubsystem.driveTank(1, 1);
            });
        }

        @Override
        protected boolean isFinished() {
            return false;
        }

        @Override
        protected void end() {
            LOG.catchAll(()-> {
                LOG.info("Done driving forward.  Now testing the deceleration.");
            });
        }

        public DriveCommand() {
            super(ACCELERATE_TIME);
            requires(driveSubsystem);
        }
    }

    private class MeasureCommand extends CommandBase {
        private long   startTime;
        private double startSpeed;

        @Override
        protected void initialize() {
            LOG.catchAll(()-> {
                startTime = System.currentTimeMillis();
                startSpeed = location.getSpeed();
            });
        }

        @Override
        protected void execute() {
            LOG.catchAll(()-> {
                driveSubsystem.driveTank(0, 0);
            });
        }

        @Override
        protected boolean isFinished() {
            return LOG.catchAll(()-> {
                return location.getSpeed() < SPEED_CUTOFF;
            }, true);
        }

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

        public MeasureCommand() {
            requires(driveSubsystem);
            requires(locationSubsystem);
        }
    }

    private double totalAcceleration;
    private int    trials;

    @Override
    protected void initialize() {
        LOG.catchAll(()-> {
            totalAcceleration = 0;
            trials = 0;
            if (!Robot.calibrationSetter.getSelected().equals(RobotMap.CalibrationMode.Calibrating)) {
                cancel();
            }
        });
    }

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

    @Override
    protected void end() {
        LOG.catchAll(()-> {
            interrupted();
        });
    }

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
