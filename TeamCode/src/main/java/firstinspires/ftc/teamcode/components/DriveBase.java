package firstinspires.ftc.teamcode.components;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.util.Range;
import firstinspires.ftc.teamcode.opmodes.BasicOpModeIterative;

public class DriveBase extends ComponentBase {
    //Test
    private DcMotor leftTopMotor;
    private DcMotor leftBottomMotor;
    private DcMotor rightTopMotor;
    private DcMotor rightBottomMotor;
    private DcMotor carouselMotor;

    private RobotHardware hardware = new RobotHardware();

    private final double CAROUSEL_POWER = 0.5;



    public DriveBase(BasicOpModeIterative robot) {
        super(robot);
        hardware.init(robot.getHardwareMap());

        leftTopMotor = hardware.leftTopMotor;
        leftBottomMotor = hardware.leftBottomMotor;
        rightTopMotor = hardware.rightTopMotor;
        rightBottomMotor = hardware.rightBottomMotor;
        carouselMotor = hardware.carouselMotor;
    }

    @Override
    public void update() {
        if (robot.isCarouselRunning()) {
            spinCarousel();
        }
        else {
            stopCarousel();
        }

        if (robot.gamepad1.x && !robot.gamepad1.b) {
            robot.setCarouselRunning(true);
        } else if (robot.gamepad1.b) {
            robot.setCarouselRunning(false);
        }

        drive();
    }

    public void drive() {
        Gamepad joystick = robot.gamepad1;

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

