package firstinspires.ftc.teamcode.autonomous;

public interface Type<T> extends Parseable {

    T convert(String code);

}
