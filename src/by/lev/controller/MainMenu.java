package by.lev.controller;

import by.lev.service.InputCorrectness;

import java.security.Provider;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static by.lev.Constant.INCORRECT_INPUT;
import static by.lev.controller.InputFunction.*;

public class MainMenu implements MainMenuInterface {
    public void start() {
        System.out.println("- - -| CACTUS CINEMA |- - -");
        System.out.println("<1> - ВХОД");
        System.out.println("<2> - РЕГИСТРАЦИЯ");
        System.out.println("<0> - ВЫХОД");
        int value = new InputCorrectness().inputCorrectValueFromZeroToTwo();
        if (value == -1){
            System.out.println(INCORRECT_INPUT.getMessage());
            start();
        }
        switch (value) {
            case 1:
                new Authorization().autorization();
                break;
            case 2:
                new Registration().createNewUser();
                start();
                break;
            case 0:
                System.exit(0);

        }
    }
}
