# BasicCommandsv3

very simple "robot" with wpilib 2027 alpha 7 and the new commands v3 and opmode frameworks.

it has one `Mechanism` (the commands v3 equivalent of a `Subsystem`), the `Doohickey`. this contains a `SparkFlexSimCompat` that extends the `SparkFlex` type to also work in sim mode, using a DCMotor single-jointed arm sim. it is One (1) neo vortex motor on can bus 0 with id 50 geared 1:1 with a J (moment of inertia, in Kgm^2) of 0.000014, approximately that of a standard #2 unsharpened dixon ticonderoga pencil.
the motor has a PID loop with P set to 0.1 and I and D set to 0.0.
when stopped via the `stop()` command (and on idle), the motor is stopped.
it has one other command, the `go(double position)` command. this sets the motor controller's position setpoint to `position` (in rotations).

the robot also has four `OpMode`s, one teleop, two auto, and one utility.
the teleop `OpMode`, `BasicTeleop` allows for the `Doohickey`'s motor to be set to the positions 1.0, -1.0, 0.5, and -0.5 with the 0th, 1st, 2nd, and 3rd buttons respectively on the 0th controller.
the first auto `OpMode`, `DoNothingAuto` does exactly as the name suggests, nothing.
the second auto `OpMode`, `OscillateAuto` causes the `Doohickey`'s motor to be set to position 1.0 for one second, set it to position -1.0 for one second, and then do that all over again forever while the robot is enabled.
the utility `OpMode` is basically just the `OscillateAuto` but with only one run of the loop, and then prints out "Test done."

the `OpMode`s don't actually have to be specified anywhere in `Robot`, they just have to be annotated with `@Autonomous`, `@Teleop`, or `@Utility`.