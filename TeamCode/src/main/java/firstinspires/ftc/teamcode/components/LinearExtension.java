package firstinspires.ftc.teamcode.components;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.util.ElapsedTime;

public class LinearExtension {

    private DcMotor linearExtension; //The linearExtension motor.
    private ElapsedTime elapsedTime = new ElapsedTime(); //This variable represents passed time since it has been initiated.
    private RobotHardware robot = new RobotHardware();

    private final double PULSES_PER_MOTOR_REV = 537.7; //This is the pulses / ticks per motor revolution.
    private final double DRIVE_GEAR_REDUCTION = 1.0; //Drive gear reduction is set to 1.0 if the wheel is directly mounted to the motor shaft. If not, calculate with following formula: (number of teeth on the large gear) / (number of teeth on the small gear)
    private final double WHEEL_DIAMETER_INCHES = 3.77953; //The current wheel diameter which is used for the COUNTS_PER_CENTIMETRE calculation.
    private final double PULSES_PER_CENTIMETRE = (PULSES_PER_MOTOR_REV * DRIVE_GEAR_REDUCTION) / (WHEEL_DIAMETER_INCHES * 3.1415) * 2.54; // This variable is used for getting the necessary ticks / pulses for moving a distance (in centimetres).

    private final double MAX_EXTENSION = 10.0; //The maximum distance the arm can extend (in centimeters).
    private final double EXTENSION_SPEED = .2; //The speed at which the extension motor rotates.
    private final double MINIMUM_EXTENSION = 0.0; //The minimum distance the arm can extend (in centimeters).

    private double currentPosCentimetres = 0.0; //The current position of the motor (in centimetres).

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
        linearExtension.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
    }

    /**
     * This method extends the arm to the specified distance {@code cm}.
     * If {@code cm} is out of bounds of the extension range then the method will not be called.
     * {@code timeout} is the amount of time in seconds until the motors stop running.
     * It is recommended to keep {@code timeout} under 2.0 seconds and don't extend the arm pass 3cm at a time.
     *
     * @param cm the distance the arm will extend in centimeters, will accept a negative value to extend backwards
     * @param timeout the time (in seconds) before the motors stop even if the target location has not been reached
     */

    protected void extendBy(int cm, double timeout) {
        int newTargetPos;
        double projectPos;
        //Checks if the motor should extend forwards or backwards
        if (cm < 0) {
            newTargetPos = linearExtension.getCurrentPosition() - (int) (cm * PULSES_PER_CENTIMETRE);
        }
        else {
            newTargetPos = linearExtension.getCurrentPosition() + (int) (cm * PULSES_PER_CENTIMETRE);
        }

        projectPos = newTargetPos / PULSES_PER_CENTIMETRE;

        //If the projected position is greater or less than the bounds the method will stop and not extend
        if (projectPos > MAX_EXTENSION || projectPos < MINIMUM_EXTENSION) {
            return;
        }

        currentPosCentimetres = newTargetPos / PULSES_PER_CENTIMETRE;

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
     * There is a 1 second timeout for both extensions.
     *
     * @param gamepad the controller which is getting listened for
     */

    public void control(Gamepad gamepad) {
        //If the button y is pressed, extend the arm by two centimetres.
        if (gamepad.y) {
            extendBy(2, 1.0);
        }
        //If the button b is pressed, extend the arm backwards by two centimetres.
        if (gamepad.b) {
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
        return currentPosCentimetres * PULSES_PER_CENTIMETRE;
    }


}
