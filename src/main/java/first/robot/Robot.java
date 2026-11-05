package first.robot;

import org.wpilib.command3.Scheduler;
import org.wpilib.command3.button.CommandGamepad;
import org.wpilib.epilogue.Epilogue;
import org.wpilib.epilogue.Logged;
import org.wpilib.framework.OpModeRobot;
import org.wpilib.util.Color;

import first.robot.mechanisms.Doohickey;
import first.robot.mechanisms.LEDController;

@Logged
public class Robot extends OpModeRobot {
    // TODO: swerve drivebase (sim)????
    public final Doohickey doohickey = new Doohickey();
    public final LEDController ledController = new LEDController();
    public final CommandGamepad controller = new CommandGamepad(0);

    private final Scheduler scheduler = Scheduler.getDefault();

    public Robot() {
        // because i've overriden `idle`, i have to set it as the default
        doohickey.setDefaultCommand(doohickey.idle());
        ledController.setDefaultCommand(ledController.idle());
        doohickey.isReadyTrigger()
            .onTrue(ledController.fill(Color.LIME))
            .onFalse(ledController.fill(Color.RED));
    }

    @Override
    public void simulationPeriodic() {
        doohickey.updateSim();
    }

    @Override
    public void robotPeriodic() {
        // this could also be used to update utilities such as a PoseEstimator

        // make commands do the command things
        scheduler.run();
        // update logger
        Epilogue.update(this);
    }
}
