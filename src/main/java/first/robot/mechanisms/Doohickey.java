package first.robot.mechanisms;

import org.wpilib.command3.Command;
import org.wpilib.command3.Mechanism;
import org.wpilib.command3.Trigger;
import org.wpilib.epilogue.Logged;
import org.wpilib.util.Alert;

import com.revrobotics.AbsoluteEncoder;
import com.revrobotics.PersistMode;
import com.revrobotics.REVLibError;
import com.revrobotics.ResetMode;
import com.revrobotics.spark.FeedbackSensor;
import com.revrobotics.spark.SparkClosedLoopController;
import com.revrobotics.spark.SparkLowLevel.ControlType;
import com.revrobotics.spark.config.SparkFlexConfig;
import com.revrobotics.spark.config.AbsoluteEncoderConfig;
import com.revrobotics.spark.config.SparkBaseConfig.IdleMode;

import first.robot.motors.MotorConstants;
import first.robot.motors.SparkFlexSimCompat;

@Logged
public class Doohickey implements Mechanism {
    private static final Alert motorConfigFailed = new Alert("Doohickey.MotorConfigFailed",
            "Doohickey motor configuration failed.", Alert.Level.HIGH);

    @SuppressWarnings("unused")
    private double motorPosition = 0.0;
    @SuppressWarnings("unused")
    private double motorVelocity = 0.0;

    private SparkFlexSimCompat motorSpark;
    private AbsoluteEncoder motorEncoder;
    private SparkClosedLoopController motorController;

    public Doohickey() {
        motorSpark = new SparkFlexSimCompat(MotorConstants.doohickeyMotorParams);
        motorEncoder = motorSpark.getAbsoluteEncoder();
        motorController = motorSpark.getClosedLoopController();

        SparkFlexConfig motorConfig = new SparkFlexConfig();
        motorConfig
                .inverted(false)
                .idleMode(IdleMode.kBrake)
                .smartCurrentLimit(40, 60)
                .voltageCompensation(12.0);
        motorConfig.absoluteEncoder
                .inverted(false)
                .zeroOffset(0.0)
                .apply(AbsoluteEncoderConfig.Presets.REV_ThroughBoreEncoderV2);
        motorConfig.closedLoop
                .feedbackSensor(FeedbackSensor.kAbsoluteEncoder)
                .pid(0.1, 0.0, 0.0);

        REVLibError driveErr = motorSpark.configure(
                motorConfig,
                ResetMode.kResetSafeParameters,
                PersistMode.kPersistParameters);
        if (!driveErr.equals(REVLibError.kOk))
            motorConfigFailed.set(true);
        System.out.println(driveErr);
    }

    public void updateSim() {
        motorSpark.updateSim();

        // TODO: put these in a real periodic method
        motorPosition = motorEncoder.getPosition().get();
        motorVelocity = motorEncoder.getVelocity().get();
    }

    public boolean isReady() {
        return Math.abs(motorEncoder.getPosition().get() - motorController.getSetpoint().get()) < 0.1;
    }
    
    public Trigger isReadyTrigger() {
        return new Trigger(this::isReady);
    }

    public Command stop() {
        return runRepeatedly(motorSpark::stopMotor).named("Doohickey.Stop");
    }

    @Override
    public Command idle() {
        return stop();
    }

    public Command go(double position) {
        return runRepeatedly(() -> {
            motorController.setSetpoint(position, ControlType.kPosition);
        })
                .named("Doohickey.Go");
    }
}
