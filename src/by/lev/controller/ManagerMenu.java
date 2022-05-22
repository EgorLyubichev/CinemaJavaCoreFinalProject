package by.lev.controller;

import by.lev.service.ManagerService;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static by.lev.service.ServiceFunction.scanString;

public class ManagerMenu extends UserMenu {

    public void showManagerMenu() {
        System.out.println("- - -|   CACTUS CINEMA    |- - -");
        System.out.println("- - -| СТРАНИЦА МЕНЕДЖЕРА |- - -");
        System.out.println("<1> - посмотреть весь список фильмов");
        System.out.println("<2> - посмотреть список предстоящих сеансов");
        System.out.println("<3> - список пользователей");
        System.out.println("<4> - купить билет для пользователя");
        System.out.println("<5> - посмотреть купленные билеты пользователя");
        System.out.println("<6> - отменить билет у пользователя");
        System.out.println("<7> - редактировать название фильма по номеру учетной записи");
        System.out.println("<8> - редактировать дату и время сеанса по номеру учетной записи");
        System.out.println("<0> - выход из программы");

        int choice = inputCorrectValueInTheManagerMenu();

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
                showManagerMenu();
                break;
            case 5:
                new ManagerService().showTheUserTickets();
                showManagerMenu();
                break;
            case 6:
                new ManagerService().cancelTheTicket();
                showManagerMenu();
                break;
            case 7:
                new ManagerService().changeMovieTitle();
                showManagerMenu();
                break;
            case 8:
                new ManagerService().changeMovieSession();
                showManagerMenu();
                break;
            case 0:
                System.exit(0);
        }
    }

    private int inputCorrectValueInTheManagerMenu() {
        String valueString = null;
        boolean correctness = false;
        while (correctness == false) {
            valueString = scanString();
            Pattern pattern = Pattern.compile("[0-8]{1}");
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



