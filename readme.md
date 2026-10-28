# BasicCommandsv3

very simple "robot" with wpilib 2027 alpha 7 and the new commands v3 and opmode frameworks.

it has one `Mechanism` (the commands v3 equivalent of a `Subsystem`), the `Doohickey`. this contains a `double` field called `number`.
when stopped via the `stop()` command (and on idle), `number` will be set to 0.0.
it has two other commands, `go()` and `goHalfway()`, which set `number` to 1.0 and 0.5 respectively, and then continue to run until cancelled, so as to not overwrite `number` with 0.0 from the deafult command.
`number` isn't necessarily meant to represent anything, but it can be imagined as a motor's velocity, as it is automatically set to 0.0 when the robot is disabled (or at least it should be).

the robot also has three `OpMode`s, one teleop and two auto.
the teleop `OpMode`, `BasicTeleop` allows for the `Doohickey`'s `number` to be set to 1.0 or 0.5 with buttons 0 and 1 on the controller on port 0, respectively.
the first auto `OpMode`, `DoNothingAuto` does exactly as the name suggests, nothing.
the second auto `OpMode`, `OscillateAuto` causes the `Doohickey`'s `number` to change between 1.0 and 0.0 every second.

the `OpMode`s don't actually have to be specified anywhere in `Robot`, they just have to be annotated with `@Autonomous`, `@Teleop`, or `@Utility`.