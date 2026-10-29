package first.robot;

import org.wpilib.command3.Scheduler;
import org.wpilib.command3.button.CommandGamepad;
import org.wpilib.epilogue.Epilogue;
import org.wpilib.epilogue.Logged;
import org.wpilib.framework.OpModeRobot;

import first.robot.mechanisms.Doohickey;

@Logged
public class Robot extends OpModeRobot {
    // TODO: swerve drivebase (sim)????
    // TODO: addressable leds and sim for those because pretty lights
    public final Doohickey doohickey = new Doohickey();
    public final CommandGamepad controller = new CommandGamepad(0);

    private final Scheduler scheduler = Scheduler.getDefault();

    public Robot() {
        // because i've overriden `idle`, i have to set it as the default
        doohickey.setDefaultCommand(doohickey.idle());
    }

    @Override
    public void simulationPeriodic() {
        doohickey.updateSim();
    }

    @Override
    public void robotPeriodic() {
        // this could also be used to update utilities such as a poseEstimator

        // make commands do the command things
        scheduler.run();
        // update logger
        Epilogue.update(this);
    }
}
