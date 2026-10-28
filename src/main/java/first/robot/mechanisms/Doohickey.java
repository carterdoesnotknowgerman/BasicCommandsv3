package first.robot.mechanisms;

import org.wpilib.command3.Command;
import org.wpilib.command3.Mechanism;
import org.wpilib.epilogue.Logged;

@Logged
public class Doohickey implements Mechanism {
    private double number = 0.0;

    // this only exists to silence java's warning of an unused field
    public double getNumber() {
        return number;
    }

    public Command stop() {
        return run(_ -> {
            number = 0.0;
        })
                .named("Doohickey.Stop");
    }

    @Override
    public Command idle() {
        return stop();
    }

    public Command go() {
        return run(coroutine -> {
            number = 1.0;
            // this makes the command run forever until cancelled
            coroutine.park();
        })
                .named("Doohickey.Go");
    }

    public Command goHalfway() {
        return run(coroutine -> {
            number = 0.5;
            coroutine.park();
        })
                .named("Doohickey.GoHalfway");
    }
}
