package firstinspires.ftc.teamcode.components;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.util.ElapsedTime;

import firstinspires.ftc.teamcode.math.EncoderMath;
import firstinspires.ftc.teamcode.opmodes.BasicOpModeIterative;

public class Arm extends ComponentBase {

    private DcMotor arm;
    private ElapsedTime elapsedTime = new ElapsedTime();
    private RobotHardware hardware = new RobotHardware();

    private final double ROTATE_SPEED = .3;
    private double currentSpeed = 0;

    public Arm(BasicOpModeIterative robot) {
        super(robot);
        hardware.init(robot.hardwareMap);
        arm = hardware.armMotor;
        arm.setDirection(DcMotorSimple.Direction.FORWARD);

    }

    private void startRotate() {
        arm.setPower(ROTATE_SPEED);
        currentSpeed = ROTATE_SPEED;
    }

    private void stopRotate() {
        arm.setPower(0);
        currentSpeed = 0;
    }

    public double getCurrentSpeed() {
        return currentSpeed;
    }

    @Override
    public void update() {
        Gamepad gamepad = robot.gamepad2;
        if (gamepad.x) {
            startRotate();
        }
        if (gamepad.b) {
            stopRotate();
        }
    }
}
