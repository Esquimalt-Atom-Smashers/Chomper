package firstinspires.ftc.teamcode.opmodes;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.util.ElapsedTime;

@TeleOp(name="Basic: Test Code OpMode", group="Iterative Opmode")
public class TestCodeOpMode extends LinearOpMode {

    // Declare OpMode members.
    private ElapsedTime runtime = new ElapsedTime();




    @Override
    public void runOpMode() {
        telemetry.addData("Status", "Initialized");

        telemetry.update();

        waitForStart();
        runtime.reset();

        // run until the end of the match (driver presses STOP)
        while (opModeIsActive()) {
            //getJoyStickValue(gamepad1);
            telemetry.addData("value A", gamepad1.a);
            telemetry.update();
        }
    }
    /*
    public void getJoyStickValue(Gamepad gamepad) {
        telemetry.addData("Left_Joystick_Y value: ", gamepad.left_stick_y);
        telemetry.addData("Left_Joystick_X value: ", gamepad.left_stick_x);
        telemetry.addData("Right_Joystick_Y value: ", gamepad.right_stick_y);
        telemetry.addData("Right_Joystick_X value: ", gamepad.right_stick_x);
        telemetry.addData("Button_A valuee: ", gamepad1.a);
        telemetry.addData("Elliot smells", 0);
        telemetry.update();
    }

     */




}
