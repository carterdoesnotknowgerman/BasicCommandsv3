# BasicCommandsv3

very simple "robot" with wpilib 2027 alpha 7 and the new commands v3 and opmode frameworks.

it has one `Mechanism` (the commands v3 equivalent of a `Subsystem`), the `Doohickey`. this contains a simulated `DCMotor` as a "single-jointed arm." it is One (1) neo vortex motor geared 1:1 with a J (moment of inertia, in Kgm^2) of 0.01.
when stopped via the `stop()` command (and on idle), the motor is given an input of 0.0 volts.
it has one other command, the `go(double speed)` command. this sets the motor's input voltage to the `speed` parameter multiplied by 12.0.

the robot also has three `OpMode`s, one teleop and two auto.
the teleop `OpMode`, `BasicTeleop` allows for the `Doohickey`'s motor to be set to speeds 1.0, -1.0, 0.5, and -0.5.
the first auto `OpMode`, `DoNothingAuto` does exactly as the name suggests, nothing.
the second auto `OpMode`, `OscillateAuto` causes the `Doohickey`'s motor to be set to speed 1.0 for one second, wait for one second (letting the idle command set the speed/voltage back to 0.0), set it to speed -1.0 for one second, wait another second, and then do that all over again forever until the robot is disabled.

the `OpMode`s don't actually have to be specified anywhere in `Robot`, they just have to be annotated with `@Autonomous`, `@Teleop`, or `@Utility`.