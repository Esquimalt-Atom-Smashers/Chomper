package firstinspires.ftc.teamcode.components;

import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;
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

    public void openAndCloseClaw(Gamepad gamepad) {

        if (gamepad.right_stick_x > 0) {
            claw.setPosition((gamepad.right_stick_x + INCREMENT_VAL));
        }
        if (gamepad.right_stick_x < 0) {
            claw.setPosition((gamepad.right_stick_x - INCREMENT_VAL));
        }
    }

    @Override
    public void update() {

    }
}
