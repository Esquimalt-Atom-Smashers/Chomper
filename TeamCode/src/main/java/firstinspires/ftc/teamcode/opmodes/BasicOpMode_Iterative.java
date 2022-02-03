package firstinspires.ftc.teamcode.opmodes;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;

import firstinspires.ftc.teamcode.components.Claw;
import firstinspires.ftc.teamcode.components.DriveBase;
import firstinspires.ftc.teamcode.components.LinearExtension;
import firstinspires.ftc.teamcode.components.RobotHardware;
import firstinspires.ftc.teamcode.components.Wrist;

@TeleOp(name="Basic: Iterative OpMode", group="Iterative Opmode")
public class BasicOpMode_Iterative extends LinearOpMode {

    private boolean carouselIsRunning = false;

    // Declare OpMode members.
    RobotHardware robot = new RobotHardware();
    private ElapsedTime runtime = new ElapsedTime();

    @Override
    public void runOpMode() {

        DriveBase driveBase = new DriveBase(hardwareMap);
        Wrist wrist = new Wrist(hardwareMap);
        LinearExtension linearExtension = new LinearExtension(hardwareMap);
        Claw claw = new Claw(hardwareMap);

        telemetry.addData("Status", "Initialized");
        telemetry.update();

        waitForStart();

        runtime.reset();

        while (opModeIsActive()) {
            if (carouselIsRunning) {
                driveBase.spinCarousel();
            }
            if (gamepad1.x) carouselIsRunning = true;
            if (gamepad1.b) carouselIsRunning = false;

            driveBase.drive(gamepad1);
            wrist.controlWrist(gamepad2);
            claw.openAndCloseClaw(gamepad2);

            linearExtension.control(gamepad2);

            telemetry.addData("Status", "Run Time: " + runtime.toString());
            telemetry.addData("Linear extension Pos", linearExtension.getCurrentPositionCentimetres());
            telemetry.update();
        }
    }


}
