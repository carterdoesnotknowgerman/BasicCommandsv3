package first.robot.mechanisms;

import org.wpilib.command3.Command;
import org.wpilib.command3.Mechanism;
import org.wpilib.epilogue.Logged;
import org.wpilib.math.system.DCMotor;
import org.wpilib.math.system.Models;
import org.wpilib.simulation.DCMotorSim;

@Logged
public class Doohickey implements Mechanism {
    // TODO: make FLYTMotor interface
    private final DCMotor motorGearbox = DCMotor.getNeoVortex(1);
    private final DCMotorSim motorSim = new DCMotorSim(
            // same bhv as LinearSystemId.createDCMotorSystem
            Models.singleJointedArmFromPhysicalConstants(motorGearbox, 0.01, 1),
            motorGearbox);
    private double motorPosition = 0.0;
    private double motorVelocity = 0.0;
    
    public void updateSim() {
        motorSim.update(0.02); // TODO: don't hardcode this
        motorPosition = motorSim.getAngularPosition();
        motorVelocity = motorSim.getAngularVelocity();
    }

    public Command stop() {
        return run(_ -> {
            motorSim.setInputVoltage(0.0);
        })
                .named("Doohickey.Stop");
    }

    @Override
    public Command idle() {
        return stop();
    }

    public Command go(double speed) {
        return run(coroutine -> {
            motorSim.setInputVoltage(speed * 12.0);
            // this makes the command run forever until cancelled
            coroutine.park();
        })
                .named("Doohickey.Go");
    }
}
