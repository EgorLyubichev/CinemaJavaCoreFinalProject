package by.lev.controller.menu;

import by.lev.controller.userActions.UserController;
import by.lev.exceptions.MovieException;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static by.lev.controller.controllerFunctions.ControllerFunction.*;

public class UserMenu {
    public void showUserMenu() {
        System.out.println("- - -| CACTUS CINEMA |- - -");
        System.out.println("- - -| Основное меню:|- - -");
        System.out.println("<1> - Посмотреть предстоящие фильмы.");
        System.out.println("<2> - Купить билет.");
        System.out.println("<3> - Посмотреть купленные билеты.");
        System.out.println("<4> - Отменить билет.");
        System.out.println("<0> - Выход из программы.");

        int choice = inputCorrectValue();

        switch (choice) {
            case 1:
                new UserController().seeExcitingMovies();
                System.out.println();
                showUserMenu();
                break;
            case 2:
                new UserController.BuyingATicket().buyATicket();
                break;
            case 3:
                new UserController().showUserTickets();
                showUserMenu();
                break;
            case 4:
                new UserController().cancelTheTicket();
                showUserMenu();
                break;
            case 0:
                System.exit(0);

        }


    }

    private int inputCorrectValue() {
        String valueString = null;
        boolean correctness = false;
        while (correctness == false) {
            valueString = scanString();
            Pattern pattern = Pattern.compile("[0-4]{1}");
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
