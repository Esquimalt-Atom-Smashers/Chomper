package firstinspires.ftc.teamcode.components;

import firstinspires.ftc.teamcode.opmodes.BasicOpModeIterative;

public abstract class ComponentBase implements Component {

    protected final BasicOpModeIterative robot;

    public ComponentBase(BasicOpModeIterative robot) {
        this.robot = robot;
    }

    public BasicOpModeIterative getRobot() {
        return robot;
    }

}
