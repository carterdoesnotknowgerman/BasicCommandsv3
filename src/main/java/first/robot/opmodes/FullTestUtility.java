package first.robot.opmodes;

import static org.wpilib.units.Units.Seconds;

import org.wpilib.command3.Command;
import org.wpilib.command3.Trigger;
import org.wpilib.driverstation.RobotState;
import org.wpilib.opmode.OpMode;
import org.wpilib.opmode.Utility;

import first.robot.Robot;

// theoretically this could be used to do a full systems test,
// e.g. on a 2026 robot it could put the intake(s) in and out,
// move around the shooter, spin the dyerotor, test leds, &c.
// this robot only has one mechanism though, so it's pretty boring.
@Utility
public class FullTestUtility implements OpMode {
    private final Robot robot;
    private final Trigger enabled = new Trigger(RobotState::isEnabled);

    public FullTestUtility(Robot robot) {
        this.robot = robot;
        enabled.whileTrue(test());
    }

    public Command test() {
        // during the coroutine.wait, the default command for doohickey
        // (doohickey.stop) takes over to set the motor speed to 0.0
        return Command.noRequirements(coroutine -> {
            coroutine.await(robot.doohickey.go(1.0).withTimeout(Seconds.of(1)));
            coroutine.await(robot.doohickey.go(-1.0).withTimeout(Seconds.of(1)));
            coroutine.await(robot.doohickey.go(0.0).withTimeout(Seconds.of(1)));
            System.out.println("Test done");
        })
                .named("Doohickey.Oscillate");
    }

}