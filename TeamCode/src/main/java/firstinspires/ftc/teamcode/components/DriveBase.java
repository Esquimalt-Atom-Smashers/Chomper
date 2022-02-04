package firstinspires.ftc.teamcode.components;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.util.Hardware;
import com.qualcomm.robotcore.util.Range;

public class DriveBase {
    //Test
    private DcMotor leftTopMotor;
    private DcMotor leftBottomMotor;
    private DcMotor rightTopMotor;
    private DcMotor rightBottomMotor;
    private DcMotor carouselMotor;

    private RobotHardware robot = new RobotHardware();

    private final double CAROUSEL_POWER = 0.5;

    public DriveBase(HardwareMap hardwareMap) {
        robot.init(hardwareMap);
        leftTopMotor = robot.leftTopMotor;
        leftBottomMotor = robot.leftBottomMotor;
        rightTopMotor = robot.rightTopMotor;
        rightBottomMotor = robot.rightBottomMotor;
        carouselMotor = robot.carouselMotor;
    }

    public void drive(Gamepad joystick) {

        leftBottomMotor.setDirection(DcMotorSimple.Direction.REVERSE);
        leftTopMotor.setDirection(DcMotorSimple.Direction.REVERSE);
        rightTopMotor.setDirection(DcMotorSimple.Direction.FORWARD);
        rightBottomMotor.setDirection(DcMotorSimple.Direction.FORWARD);

        double drive = -joystick.left_stick_y;
        double turn = joystick.left_stick_x;
        double rx = joystick.right_stick_x;

        double frontLeftPower = Range.clip(drive + turn + rx, -1.0, 1);
        double backLeftPower = Range.clip((drive - turn + rx), -1.0, 1);
        double frontRightPower = Range.clip((drive - turn - rx), -1.0, 1);
        double backRightPower = Range.clip((drive + turn - rx), -1.0, 1);

        leftTopMotor.setPower(frontLeftPower * .80);
        leftBottomMotor.setPower(backLeftPower * .80);
        rightTopMotor.setPower(frontRightPower * .80);
        rightBottomMotor.setPower(backRightPower * .80);
    }
    public void spinCarousel() {
        carouselMotor.setDirection(DcMotorSimple.Direction.FORWARD);
        carouselMotor.setPower(CAROUSEL_POWER);

    }
    public void stopCarousel() {
        carouselMotor.setPower(0.0);
    }
}

