package firstinspires.ftc.teamcode.opmodes;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;

import firstinspires.ftc.teamcode.components.RobotHardware;

public class EncoderDriveOpMode extends LinearOpMode {

    private RobotHardware robot = new RobotHardware();
    private ElapsedTime elapsedTime = new ElapsedTime();

    private final double PULSES_PER_MOTOR_REV = 537.7;
    private final double DRIVE_GEAR_REDUCTION = 1.0; //Since we are using direct drive this value is 1.0
    private final double WHEEL_DIAMETER_INCHES = 3.77953;
    private final double COUNTS_PER_INCHES = (PULSES_PER_MOTOR_REV * DRIVE_GEAR_REDUCTION) / (WHEEL_DIAMETER_INCHES * 3.1415); //Using Inches for now



    @Override
    public void runOpMode() throws InterruptedException {
        robot.init(hardwareMap);

        robot.leftTopMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        robot.leftBottomMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        robot.rightTopMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        robot.rightBottomMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        robot.leftTopMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        robot.leftBottomMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        robot.rightTopMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        robot.rightBottomMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        waitForStart();

        encoderDrive(0.2, 5, 5, 3);

    }

    public void encoderDrive(double speed, double inchesLeft, double inchesRight, double timeout) {

        int newLeftTarget;
        int newRightTarget;

        if (opModeIsActive()) {

            newLeftTarget = robot.leftTopMotor.getCurrentPosition() + (int) (inchesLeft * COUNTS_PER_INCHES);
            newRightTarget = robot.rightTopMotor.getCurrentPosition() + (int) (inchesRight * COUNTS_PER_INCHES);
            robot.leftTopMotor.setTargetPosition(newLeftTarget);
            robot.leftBottomMotor.setTargetPosition(newLeftTarget);
            robot.rightTopMotor.setTargetPosition(newRightTarget);
            robot.rightBottomMotor.setTargetPosition(newRightTarget);

            robot.leftTopMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            robot.leftBottomMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            robot.rightTopMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            robot.rightBottomMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);


            elapsedTime.reset();
            robot.leftTopMotor.setPower(Math.abs(speed));
            robot.leftBottomMotor.setPower(Math.abs(speed));
            robot.rightTopMotor.setPower(Math.abs(speed));
            robot.rightBottomMotor.setPower(Math.abs(speed));

            while (opModeIsActive() && (elapsedTime.seconds() < timeout && robot.motorsAreBusy())) {

                telemetry.addData("TargetPos: ", "LeftPos: " + newLeftTarget + " " + "RightPos: " + newRightTarget);
                telemetry.update();

            }

            robot.leftTopMotor.setPower(0);
            robot.leftBottomMotor.setPower(0);
            robot.rightTopMotor.setPower(0);
            robot.rightBottomMotor.setPower(0);


        }

    }


}
