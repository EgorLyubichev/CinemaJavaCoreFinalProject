package by.lev.controller;

import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static by.lev.controller.controllerFunctions.ControllerFunction.*;

public class Entrance {
    public void start() {
        System.out.println("- - -| CACTUS CINEMA |- - -");
        System.out.println("<1> - ВХОД");
        System.out.println("<2> - РЕГИСТРАЦИЯ");
        System.out.println("<0> - ВЫХОД");
        int value = inputCorrectValue();
        switch (value) {
            case 1:
                new AuthorizationFunctional().autorization();
                break;
            case 2:
                //такой метод
                break;
            case 3:
                //такой метод

        }
    }

    public int inputCorrectValue() {
        String valueString = null;
        boolean correctness = false;
        while (correctness == false) {
            valueString = scanString();
            Pattern pattern = Pattern.compile("[0-2]{1}");
            Matcher matcher = pattern.matcher(valueString);
            if (matcher.matches() == false) {
                System.out.println("Неверный запрос, повторите попытку.");
            } else {
                correctness = true;
            }
        }
        int result = Integer.parseInt(valueString);
        return result;
    }
}
