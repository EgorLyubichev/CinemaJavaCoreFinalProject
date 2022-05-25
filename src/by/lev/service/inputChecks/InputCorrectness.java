package by.lev.service.inputChecks;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static by.lev.controller.InputFunction.scanString;

public class InputCorrectness {
    public int inputCorrectValueFromZeroToFive() {
        String input = scanString();
        if (getRightInput(input) == true) {
            int result = Integer.parseInt(input);
            return result;
        } else {
            return -1;
        }
    }

    public boolean getRightInput(String input) {
        Pattern pattern = Pattern.compile("[0-5]{1}");
        Matcher matcher = pattern.matcher(input);
        return matcher.matches();
    }
}
