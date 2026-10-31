package first.robot.opmodes;

import org.wpilib.opmode.OpMode;
import org.wpilib.opmode.Teleop;

import first.robot.Robot;

@Teleop
public class BasicTeleop implements OpMode {
    public BasicTeleop(Robot robot) {
        robot.controller
                .button(0)
                .onTrue(robot.doohickey.go(1.0));

        robot.controller
                .button(1)
                .onTrue(robot.doohickey.go(-1.0));

        robot.controller
                .button(2)
                .onTrue(robot.doohickey.go(0.5));

        robot.controller
                .button(3)
                .onTrue(robot.doohickey.go(-0.5));
    }
}
