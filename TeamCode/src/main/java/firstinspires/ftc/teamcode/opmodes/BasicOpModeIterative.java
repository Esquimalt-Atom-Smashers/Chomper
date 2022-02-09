package firstinspires.ftc.teamcode.opmodes;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.util.ElapsedTime;

import firstinspires.ftc.teamcode.components.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@TeleOp(name="Basic: Iterative OpMode", group="Iterative Opmode")
public class BasicOpModeIterative extends LinearOpMode {

    private boolean carouselRunning = false;

    // Declare OpMode members.
    private final RobotHardware hardware = new RobotHardware();
    private final ElapsedTime runtime = new ElapsedTime();

    // Robot components (created in runOpMode)
    private DriveBase driveBase;
    private Wrist wrist;
    private LinearExtension linearExtension;
    private Claw claw;
    private Arm arm;

    private final List<Component> components = new ArrayList<>();

    @Override
    public void runOpMode() {
        driveBase = new DriveBase(this);
        wrist = new Wrist(this);
        linearExtension = new LinearExtension(this);
        claw = new Claw(this);
        arm = new Arm(this);

        components.addAll(Arrays.asList(
                driveBase, wrist, linearExtension, claw, arm
        ));

        telemetry.addData("Status", "Initialized");
        telemetry.update();

        waitForStart();

        runtime.reset();

        while (opModeIsActive()) {
            for (Component component : components) {
                component.update();
            }

            telemetry.addData("Current Rotation Arm: ", arm.getCurrentRotation());
            telemetry.addData("Status", "Run Time: " + runtime.toString());
            telemetry.addData("Linear extension Pos", linearExtension.getCurrentPositionCentimetres());
            telemetry.update();
        }
    }

    public boolean isCarouselRunning() {
        return carouselRunning;
    }

    public void setCarouselRunning(boolean carouselRunning) {
        this.carouselRunning = carouselRunning;
    }

    public List<Component> getComponents() {
        return components;
    }

    public HardwareMap getHardwareMap() {
        return hardwareMap;
    }

}
