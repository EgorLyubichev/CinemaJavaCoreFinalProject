package by.lev.controller;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static by.lev.service.ServiceFunction.*;

public class Menu {
    public void start() {
        System.out.println("- - -| CACTUS CINEMA |- - -");
        System.out.println("<1> - ВХОД");
        System.out.println("<2> - РЕГИСТРАЦИЯ");
        System.out.println("<0> - ВЫХОД");
        int value = inputCorrectValueToStart();
        switch (value) {
            case 1:
                new Authorization().autorization();
                break;
            case 2:
                new Registration().createNewUser();
                break;
            case 0:
                //такой метод

        }
    }

    public int inputCorrectValueToStart() {
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
