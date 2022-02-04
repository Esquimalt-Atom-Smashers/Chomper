package firstinspires.ftc.teamcode.math;

public class EncoderMath {

    public static final double PULSES_PER_MOTOR_REV = 537.7; //This is the pulses / ticks per motor revolution.
    public static final double DRIVE_GEAR_REDUCTION = 1.0; //Drive gear reduction is set to 1.0 if the wheel is directly mounted to the motor shaft. If not, calculate with following formula: (number of teeth on the large gear) / (number of teeth on the small gear)
    public static final double WHEEL_DIAMETER_INCHES = 3.77953; //The current wheel diameter which is used for the COUNTS_PER_CENTIMETRE calculation.
    public static final double PULSES_PER_CENTIMETRE = (PULSES_PER_MOTOR_REV * DRIVE_GEAR_REDUCTION) / (WHEEL_DIAMETER_INCHES * 3.1415) * 2.54; // This variable is used for getting the necessary ticks / pulses for moving a distance (in centimetres).
    public static final double DRIVE_GEAR_REDUCTION_ARM = 10.0 / 3.0;

}
