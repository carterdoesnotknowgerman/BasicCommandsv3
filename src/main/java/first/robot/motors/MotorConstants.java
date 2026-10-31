package first.robot.motors;

import org.wpilib.hardware.bus.CANPort;
import org.wpilib.math.numbers.N1;
import org.wpilib.math.numbers.N2;
import org.wpilib.math.system.DCMotor;
import org.wpilib.math.system.LinearSystem;
import org.wpilib.math.system.Models;

import com.revrobotics.spark.SparkLowLevel.MotorType;

public final class MotorConstants {
    public record SparkParameters(
            CANPort canPort,
            int deviceId,
            MotorType type,
            DCMotor gearbox,
            LinearSystem<N2, N1, N2> system) {
    }

    private MotorConstants() {}

    public static final SparkParameters doohickeyMotorParams = new SparkParameters(
            CANPort.CAN_S0, 50,
            MotorType.kBrushless,
            DCMotor.getNeoVortex(1),
            Models.singleJointedArmFromPhysicalConstants(
                    DCMotor.getNeoVortex(1),
                    0.000014, // MOI of a standard #2 unsharpened dixon ticonderoga pencil
                    1.0));
}
