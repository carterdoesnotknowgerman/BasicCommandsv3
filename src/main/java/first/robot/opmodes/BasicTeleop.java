package first.robot.opmodes;

import org.wpilib.opmode.OpMode;
import org.wpilib.opmode.Teleop;

import first.robot.Robot;

@Teleop
public class BasicTeleop implements OpMode {
    // TODO: figure out how to make this respect disabled mode
    public BasicTeleop(Robot robot) {
        robot.controller
                .button(0)
                .whileTrue(robot.doohickey.go());

        robot.controller
                .button(1)
                .whileTrue(robot.doohickey.goHalfway());
    }
}
