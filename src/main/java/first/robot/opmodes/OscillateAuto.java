package first.robot.opmodes;

import static org.wpilib.units.Units.Seconds;

import org.wpilib.command3.Command;
import org.wpilib.command3.Trigger;
import org.wpilib.driverstation.RobotState;
import org.wpilib.opmode.Autonomous;
import org.wpilib.opmode.OpMode;

import first.robot.Robot;

@Autonomous
public class OscillateAuto implements OpMode {
    private final Trigger enabled = new Trigger(RobotState::isEnabled);

    public OscillateAuto(Robot robot) {
        enabled.whileTrue(oscillate(robot));
    }

    public Command oscillate(Robot robot) {
        // during the coroutine.wait, the default command for doohickey
        // (doohickey.stop) takes over to set `number` back to 0.
        return Command.noRequirements(coroutine -> {
            while (true) {
                coroutine.await(robot.doohickey.go().withTimeout(Seconds.of(1)));
                coroutine.wait(Seconds.of(1));
            }
        })
                .named("Doohickey.Oscillate");
    }

}
