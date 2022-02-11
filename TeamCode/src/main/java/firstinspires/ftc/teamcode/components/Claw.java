package firstinspires.ftc.teamcode.components;

import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.Range;
import firstinspires.ftc.teamcode.opmodes.BasicOpModeIterative;

public class Claw extends ComponentBase {

    private Servo claw;
    private RobotHardware hardware = new RobotHardware();

    private static double INCREMENT_VAL = 0.05;

    public Claw(BasicOpModeIterative robot) {
        super(robot);
        hardware.init(robot.hardwareMap);
        claw = hardware.clawServo;
    }

    private void openClaw() {
        claw.setPosition(Range.clip(claw.getPosition() + INCREMENT_VAL, claw.getPosition(), 1.0));
    }

    private void closeClaw() {
        claw.setPosition(Range.clip(claw.getPosition() + INCREMENT_VAL, claw.getPosition(), 1.0));
    }

    @Override
    public void update() {
        Gamepad gamepad = robot.gamepad2;

        if (gamepad.left_stick_y > 0) {
            openClaw();
        }
        if (gamepad.left_stick_y < 0) {
            closeClaw();
        }
    }
}
