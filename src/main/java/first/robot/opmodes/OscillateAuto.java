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
    private final Robot robot;
    private final Trigger enabled = new Trigger(RobotState::isEnabled);

    public OscillateAuto(Robot robot) {
        this.robot = robot;
        enabled.whileTrue(oscillate());
    }

    public Command oscillate() {
        return Command.noRequirements(coroutine -> {
            while (true) {
                coroutine.await(robot.doohickey.go(1.0).withTimeout(Seconds.of(1)));
                coroutine.await(robot.doohickey.go(-1.0).withTimeout(Seconds.of(1)));
            }
        })
                .named("Doohickey.Oscillate");
    }

}
