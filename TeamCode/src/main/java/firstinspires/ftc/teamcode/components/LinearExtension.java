package firstinspires.ftc.teamcode.components;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.util.ElapsedTime;

import firstinspires.ftc.teamcode.math.EncoderMath;

public class LinearExtension {

    private DcMotor linearExtension; //The linearExtension motor.
    private ElapsedTime elapsedTime = new ElapsedTime(); //This variable represents passed time since it has been initiated.
    private RobotHardware robot = new RobotHardware();

    private final double MAX_EXTENSION = 20.0; //The maximum distance the arm can extend (in centimeters).
    private final double EXTENSION_SPEED = .2; //The speed at which the extension motor rotates.
    private final double MINIMUM_EXTENSION = 0.0; //The minimum distance the arm can extend (in centimeters).

    private static double currentPosCentimetres = 0.0; //The current position of the motor (in centimetres).

    /**
     * Class constructor which maps the linearExtension motor and resets the encoder position.
     *It also sets the motor run mode to <i>RUN_USING_ENCODER</i> which is necessary for encoder calculations.
     * @param hardwareMap a hardwareMap from a teleOp mode.
     */

    public LinearExtension(HardwareMap hardwareMap) {
        robot.init(hardwareMap);
        linearExtension = robot.linearExtension;
        linearExtension.setDirection(DcMotorSimple.Direction.FORWARD);
        linearExtension.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
    }

    /**
     * This method extends the arm to the specified distance {@code cm}.
     * If {@code cm} is out of bounds of the extension range then the method will not be called.
     * {@code timeout} is the amount of time in seconds until the motors stop running.
     * It is recommended to keep {@code timeout} under 2.0 seconds and don't extend the arm pass 3 cm at a time.
     *
     * @param cm the distance the arm will extend in centimeters, will accept a negative value to extend backwards
     * @param timeout the time (in seconds) before the motors stop even if the target location has not been reached
     */

    private void extendBy(int cm, double timeout) {
        int newTargetPos;
        double projectPos;
        //Checks if the motor should extend forwards or backwards
        newTargetPos = linearExtension.getCurrentPosition() + (int) (cm * EncoderMath.PULSES_PER_CENTIMETRE);

        projectPos = newTargetPos / EncoderMath.PULSES_PER_CENTIMETRE;

        //If the projected position is greater or less than the bounds the method will stop and not extend
        if (projectPos > MAX_EXTENSION || projectPos < MINIMUM_EXTENSION) {
            return;
        }

        currentPosCentimetres = newTargetPos / EncoderMath.PULSES_PER_CENTIMETRE;

        linearExtension.setTargetPosition(newTargetPos);
        linearExtension.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        elapsedTime.reset();
        linearExtension.setPower(EXTENSION_SPEED);

        while (elapsedTime.seconds() < timeout && linearExtension.isBusy()) {

        }
        linearExtension.setPower(0);

    }

    /**
     * This method is used for controlling the extension of the arm, it calls the {@link LinearExtension#extendBy(int centimetres, double timeout)}.
     * If the gamepad button <i>Y</i> the arm is extended by 2 cm and if the gamepad button <i>B</i> is pressed the arm extends backwards for two centimeters.
     * There is a 1-second timeout for both extensions.
     *
     * @param gamepad the controller which is getting listened for
     */

    public void control(Gamepad gamepad) {
        //If the button y is pressed, extend the arm by two point-five-centimetres.
        if (gamepad.y) {
            extendBy(2, 1.0);
        }
        //If the button a is pressed, extend the arm backwards by two-point-five centimetres.
        if (gamepad.a) {
            extendBy(-2, 1.0);
        }
    }

    /**
     *This method returns the current position of the motor in centimetres.
     * @return current position of the motor in centimetres.
     */

    public double getCurrentPositionCentimetres() {
        return currentPosCentimetres;
    }

    /**
     *This method returns the current position of the motor in pulses / ticks.
     * @return current position of the motor in pulses / ticks.
     */

    public double getCurrentPositionPulses() {
        return currentPosCentimetres * EncoderMath.PULSES_PER_CENTIMETRE;
    }


}
