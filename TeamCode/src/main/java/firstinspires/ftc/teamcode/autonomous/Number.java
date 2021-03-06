package firstinspires.ftc.teamcode.autonomous;

import java.util.Arrays;
import java.util.List;

public class Number implements Type<Double> {

    private static final List<Character> NUMBER_CHARS = Arrays.asList('1', '2', '3', '4', '5', '6', '7', '8', '9', '0');

    @Override
    public int matches(String text) {
        int lenBeforeTrim = text.length();
        // Remove leading whitespace
        while (Character.isWhitespace(text.charAt(0))) {
            text = text.substring(1);
            if (text.isEmpty()) return -1;
        }
        // Keep track of amount of whitespace removed
        int offset = lenBeforeTrim - text.length();

        char[] chars = text.toCharArray();
        boolean onNumbers = false;

        for (int i = 0, charsLength = chars.length; i < charsLength; i++) {
            char c = chars[i];
            if (c == '.') {
                if (!onNumbers) return -1;
                onNumbers = false;
                continue;
            }
            if (NUMBER_CHARS.contains(c)) {
                onNumbers = true;
                continue;
            }
            if (onNumbers) {
                return i + matchSuffix(text.substring(i)) + offset;
            } else {
                return -1;
            }
        }

        if (onNumbers) {
            return text.length() + matchSuffix("") + offset;
        } else {
            return -1;
        }
    }

    protected int matchSuffix(String text) {
        return 0;
    }

    @Override
    public Double convert(String code) {
        return Double.parseDouble(code);
    }

}
