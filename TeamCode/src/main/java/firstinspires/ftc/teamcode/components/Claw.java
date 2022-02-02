package firstinspires.ftc.teamcode.components;

import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

public class Claw {

    private Servo claw;
    private RobotHardware robot = new RobotHardware();

    private static double INCREMENT_VAL = 0.05;

    public Claw(HardwareMap hardwareMap) {
        robot.init(hardwareMap);
        claw = robot.clawServo;
    }

    public void openAndCloseClaw(Gamepad gamepad) {

        if (gamepad.right_stick_x > 0) {
            claw.setPosition((gamepad.right_stick_x + INCREMENT_VAL));
        }
        if (gamepad.right_stick_x < 0) {
            claw.setPosition((gamepad.right_stick_x - INCREMENT_VAL));
        }
    }

}
