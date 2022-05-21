package by.lev.controller;

import by.lev.service.UserService;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static by.lev.service.ServiceFunction.*;

public class UserMenu {
    public void showUserMenu() {
        System.out.println("- - -|  CACTUS CINEMA  |- - -");
        System.out.println("- - -| ГЛАВНАЯ СТРАНИЦА|- - -");
        System.out.println("<1> - Посмотреть предстоящие фильмы.");
        System.out.println("<2> - Купить билет.");
        System.out.println("<3> - Посмотреть купленные билеты.");
        System.out.println("<4> - Отменить билет.");
        System.out.println("<0> - Выход из программы.");

        int choice = inputCorrectValue();

        switch (choice) {
            case 1:
                new UserService().showUpcomingMovies();
                System.out.println();
                showUserMenu();
                break;
            case 2:
                new UserService.BuyingATicket().buyATicket();
                break;
            case 3:
                new UserService().showUserTickets();
                showUserMenu();
                break;
            case 4:
                new UserService().cancelTheTicket();
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
