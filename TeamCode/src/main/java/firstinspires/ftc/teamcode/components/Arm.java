package firstinspires.ftc.teamcode.components;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.util.ElapsedTime;

import firstinspires.ftc.teamcode.math.EncoderMath;
import firstinspires.ftc.teamcode.opmodes.BasicOpModeIterative;

public class Arm extends ComponentBase {

    private DcMotor arm;
    private ElapsedTime elapsedTime = new ElapsedTime();
    private RobotHardware hardware = new RobotHardware();

    private final double MAX_ROTATION = 5.0; //Max amount of times the motor can do a full rotation
    private final double MINIMUM_ROTATION = 0.0; //Prevents the motor from trying to spin backwards when it can't.
    private static double currentRotation = 0.0;

    public Arm(BasicOpModeIterative robot) {
        super(robot);
        hardware.init(robot.hardwareMap);
        arm = hardware.armMotor;
        arm.setDirection(DcMotorSimple.Direction.FORWARD);
        arm.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
    }

    private void rotate(double deg, double timeout) {
        int newTargetRotation;
        double projectedRotation;

        newTargetRotation = arm.getCurrentPosition() + (int) ( (deg / 360) * EncoderMath.PULSES_PER_MOTOR_REV * EncoderMath.DRIVE_GEAR_REDUCTION_ARM);

        projectedRotation = getNumOfRotationsFromPulses(newTargetRotation);

        if (projectedRotation > MAX_ROTATION || projectedRotation < MINIMUM_ROTATION) {
            return;
        }

        currentRotation = newTargetRotation / EncoderMath.PULSES_PER_MOTOR_REV;

        arm.setTargetPosition(newTargetRotation);
        arm.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        while (elapsedTime.seconds() < timeout && arm.isBusy()) {
            // Do nothing
        }

        arm.setPower(0);

    }

    private double getNumOfRotationsFromPulses(double pulses) {
        return pulses / EncoderMath.PULSES_PER_MOTOR_REV;
    }

    public double getCurrentRotation() {
        return currentRotation;
    }

    @Override
    public void update() {
        Gamepad gamepad = robot.gamepad2;
        if (gamepad.x) {
            rotate(360, 1.0);
        }
        if (gamepad.b) {
            rotate(360, 1.0);
        }
    }
}
