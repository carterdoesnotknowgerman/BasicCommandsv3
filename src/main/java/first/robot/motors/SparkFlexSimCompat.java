package first.robot.motors;

import org.wpilib.epilogue.Logged;
import org.wpilib.framework.RobotBase;
import org.wpilib.math.system.DCMotor;
import org.wpilib.math.util.Units;
import org.wpilib.simulation.DCMotorSim;
import org.wpilib.util.Alert;

import com.revrobotics.sim.SparkFlexSim;
import com.revrobotics.spark.SparkFlex;

import first.robot.motors.MotorConstants.SparkParameters;

@Logged
public class SparkFlexSimCompat extends SparkFlex {
    private final static Alert updateSimOutsideSim = new Alert("SparkFlex.UpdateSimOutsideSim", "SparkFlex.UpdateSim was called outside of simulator.", Alert.Level.MEDIUM);
    private final DCMotor gearbox;
    private final DCMotorSim motorSim;
    private final SparkFlexSim sparkSim;

    public SparkFlexSimCompat(SparkParameters parameters) {
        super(parameters.canPort(), parameters.deviceId(), parameters.type());
        gearbox = parameters.gearbox();
        motorSim = new DCMotorSim(parameters.system(), gearbox);
        sparkSim = new SparkFlexSim(this, gearbox);
    }

    public void updateSim() {
        if (RobotBase.isSimulation()) {
            motorSim.setInput(getAppliedOutput().get() * 12.0);
            motorSim.update(0.02);
            sparkSim.iterate(
                Units.radiansPerSecondToRotationsPerMinute(motorSim.getAngularVelocity()),
                12.0,
                0.02);
        } else {
            updateSimOutsideSim.set(true);
        }
    }
}
