package first.robot.mechanisms;

import static org.wpilib.units.Units.Milliseconds;

import org.wpilib.command3.Command;
import org.wpilib.command3.Mechanism;
import org.wpilib.util.Alert;
import org.wpilib.util.Color;
import org.wpilib.util.Color8Bit;

import com.lumynlabs.devices.ConnectorXAnimate;
import com.lumynlabs.connection.usb.USBPort;
import com.lumynlabs.domain.led.Animation;
import com.lumynlabs.domain.config.ConfigBuilder;
import com.lumynlabs.domain.config.LumynDeviceConfig;
import com.lumynlabs.domain.config.NetworkType;

public class LEDController implements Mechanism {
    private final static Alert disconnected = new Alert("LEDController.Disconnected", "ConnectorX Animate disconnected.",  Alert.Level.MEDIUM);
    private ConnectorXAnimate cXAnimate = new ConnectorXAnimate();

    public LEDController() {
        cXAnimate.connect(USBPort.kUSB1); // TODO: alert
        ConfigBuilder builder = new ConfigBuilder();
        LumynDeviceConfig cfg = builder
                .forTeam("2290")
                .setNetworkType(NetworkType.USB)
                .addChannel(1, "main-channel", 120)
                .addStripZone("front", 60)
                .addStripZone("back", 60)
                .endChannel()
                .build();
        cXAnimate.ApplyConfiguration(cfg);
        cXAnimate.leds.SetAnimation(Animation.RainbowRoll)
                .ForZone("front")
                .WithColor(new Color(new Color8Bit(255, 255, 255)))
                .WithDelay(Milliseconds.of(50))
                .Reverse(false)
                .RunOnce(false);
        cXAnimate.leds.SetAnimation(Animation.Fill)
                .ForZone("back")
                .WithColor(new Color(new Color8Bit(0, 255, 0)))
                .WithDelay(Milliseconds.of(0))
                .RunOnce(false);
    }

    @Override
    public Command idle() {
        return runRepeatedly(() -> {
            disconnected.set(!cXAnimate.IsConnected());
        }).named("LEDController.Idle");
    }

    public Command fill(Color color) {
        return run(_ -> {
            cXAnimate.leds.SetAnimation(Animation.Fill)
                    .ForZone("back")
                    .WithColor(color)
                    .WithDelay(Milliseconds.of(0))
                    .RunOnce(false);
        }).named("LEDController.Fill");
    }
}