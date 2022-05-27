package by.lev.service;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static by.lev.controller.InputFunction.scanString;

public class InputCorrectness {

    public int inputCorrectValueFromZeroToThree() {
        String input = scanString();
        if (getRightInputFromZeroToThree(input)) {
            return Integer.parseInt(input);
        } else {
            return -1;
        }
    }
    private boolean getRightInputFromZeroToThree(String input) {
        Pattern pattern = Pattern.compile("[0-3]{1}");
        Matcher matcher = pattern.matcher(input);
        return matcher.matches();
    }


    public int inputCorrectValueFromZeroToFour() {
        String input = scanString();
        if (getRightInputFromZeroToFour(input)) {
            return Integer.parseInt(input);
        } else {
            return -1;
        }
    }
    private boolean getRightInputFromZeroToFour(String input) {
        Pattern pattern = Pattern.compile("[0-4]{1}");
        Matcher matcher = pattern.matcher(input);
        return matcher.matches();
    }


    public int inputCorrectValueFromZeroToFive() {
        String input = scanString();
        if (getRightInputFromZeroToFive(input)) {
            return Integer.parseInt(input);
        } else {
            return -1;
        }
    }
    private boolean getRightInputFromZeroToFive(String input) {
        Pattern pattern = Pattern.compile("[0-5]{1}");
        Matcher matcher = pattern.matcher(input);
        return matcher.matches();
    }


    public int inputCorrectValueFromZeroToSix() {
        String input = scanString();
        if (getRightInputFromZeroToSix(input)) {
            return Integer.parseInt(input);
        } else {
            return -1;
        }
    }
    private boolean getRightInputFromZeroToSix(String input) {
        Pattern pattern = Pattern.compile("[0-6]{1}");
        Matcher matcher = pattern.matcher(input);
        return matcher.matches();
    }

    public int inputCorrectValueFromZeroToEight() {
        String input = scanString();
        if (getRightInputFromZeroToEight(input)) {
            return Integer.parseInt(input);
        } else {
            return -1;
        }
    }
    private boolean getRightInputFromZeroToEight(String input) {
        Pattern pattern = Pattern.compile("[0-8]{1}");
        Matcher matcher = pattern.matcher(input);
        return matcher.matches();
    }
}
