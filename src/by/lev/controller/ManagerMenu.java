package by.lev.controller;

import by.lev.service.ManagerService;
import by.lev.service.UserService;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static by.lev.service.ServiceFunction.scanString;

public class ManagerMenu extends UserMenu {


    public void showManagerMenu() {
        System.out.println("- - -|   CACTUS CINEMA    |- - -");
        System.out.println("- - -| СТРАНИЦА МЕНЕДЖЕРА |- - -");
        System.out.println("<1> - посмотреть весь список фильмов");
        System.out.println("<2> - посмотреть список предстоящих фильмов");
        System.out.println("<3> - посмотреть список пользователей");
        System.out.println("<4> - купить билет для пользователя");
        System.out.println("<5> - просмотреть купленные билеты пользователя");
        System.out.println("<6> - отменить билет пользователя");
        System.out.println("<0> - выход из программы");

        int choice = inputCorrectValue();

        switch (choice) {
            case 1:
                new ManagerService().showMovieList();
                System.out.println();
                showManagerMenu();
                break;
            case 2:
                new ManagerService().showUpcomingMovies();
                System.out.println();
                showManagerMenu();
                break;
            case 3:
                new ManagerService().showUserList();
                showManagerMenu();
                break;
            case 4:
                new ManagerService().buyATicketForUser();

                break;
            case 5:

                break;
            case 6:

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
            Pattern pattern = Pattern.compile("[0-5]{1}");
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



