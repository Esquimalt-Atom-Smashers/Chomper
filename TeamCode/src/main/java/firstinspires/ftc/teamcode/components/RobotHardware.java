package firstinspires.ftc.teamcode.components;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;

/*
        This class allows you to use all the hardware part of the robot, it
    is also used for configuring components such as, motors and servos.
    You would usually initiate this class at the start of a telOp or autonomous
    program.
*/
public class RobotHardware {

    //These are the motors for the drive-base
    public DcMotor leftTopMotor = null;
    public DcMotor leftBottomMotor = null;
    public DcMotor rightTopMotor = null;
    public DcMotor rightBottomMotor = null;

    //This is the motor for extending the arm
    public DcMotor linearExtension = null;

    //This is the arm motor
    public DcMotor armMotor = null;

    //This is the carousel motor
    public DcMotor carouselMotor = null;

    //These are the servos
    public Servo clawServo = null;
    public Servo wristServo = null;

    //Hardware map which allows you to get the hardware from the configurations.
    private HardwareMap hardwareMap = null;
    private ElapsedTime time = new ElapsedTime();

    //This is the constructor of the class
    public RobotHardware() {
        //...
    }

    //This method initializes all the hardware interfaces
    public void init(HardwareMap hardwareMap) {
        this.hardwareMap = hardwareMap;

        //Assigns the motors to their corresponding configurations
        leftTopMotor = hardwareMap.get(DcMotor.class, "frontLeft");
        leftBottomMotor = hardwareMap.get(DcMotor.class, "backLeft");
        rightTopMotor = hardwareMap.get(DcMotor.class, "frontRight");
        rightBottomMotor = hardwareMap.get(DcMotor.class, "backRight");
        carouselMotor = hardwareMap.get(DcMotor.class, "carouselMotor");
        linearExtension = hardwareMap.get(DcMotor.class, "linearExtension");
        armMotor = hardwareMap.get(DcMotor.class, "armMotor");

        clawServo = hardwareMap.get(Servo.class, "clawServo");
        wristServo = hardwareMap.get(Servo.class, "wristServo");

        //Sets the mode {RUN_WITHOUT_ENCODER} for all the motors: leftTop, leftBottom, rightTop, rightBottom, carousel
        setMotorModes(DcMotor.RunMode.RUN_WITHOUT_ENCODER, leftTopMotor, leftBottomMotor, rightTopMotor, rightBottomMotor, carouselMotor);

        //Sets the mode {RUN_USING_ENCODER} for the motor: linearExtension
        setMotorModes(DcMotor.RunMode.RUN_USING_ENCODER, linearExtension);

    }
    /*
        This method takes a list of motors and sets their mode to whatever is specified, for example,
        RunMode.RUN_WITHOUT_ENCODER or RunMode.RUN_USING_ENCODER.
    */
    public void setMotorModes(DcMotor.RunMode runMode, DcMotor... dcMotors) {
        for (DcMotor dcMotor : dcMotors) {
            dcMotor.setMode(runMode);
        }
    }

    public boolean motorsAreBusy() {
        return leftTopMotor.isBusy() && leftBottomMotor.isBusy() && rightTopMotor.isBusy() && rightBottomMotor.isBusy();
    }

    public boolean isClawClosed() {
        return clawServo.getPosition() == 0;
    }


}
