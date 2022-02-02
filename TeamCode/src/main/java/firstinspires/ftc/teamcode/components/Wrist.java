package firstinspires.ftc.teamcode.components;

import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;
import java.lang.Math;

public class Wrist {
    private Servo wristServo;
    private RobotHardware robot = new RobotHardware();

    public static final double MAX_POSITION = 1.0;
    public static final double MIN_POSITION = 0.0;

    public static final double INCREMENT = 0.1;

    public Wrist(HardwareMap hardwareMap) {
        robot.init(hardwareMap);
        wristServo = robot.wristServo;
    }

    public void setPosition(double pos) {
        wristServo.setPosition(Math.min(Math.max(pos, MIN_POSITION), MAX_POSITION));
    }

    public double getPosition() {
        return wristServo.getPosition();
    }

    public void controlWrist(Gamepad gamepad) {
        setPosition(wristServo.getPosition() + gamepad.left_stick_y);
    }
}
