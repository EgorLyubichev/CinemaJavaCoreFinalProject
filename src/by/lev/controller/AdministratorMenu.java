package by.lev.controller;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static by.lev.controller.InputFunction.scanString;

public class AdministratorMenu extends ManagerMenu{
    public void showAdminMenu() {
        System.out.println("- - -|      CACTUS CINEMA      |- - -");
        System.out.println("- - -| СТРАНИЦА АДМИНИСТРАТОРА |- - -");
        System.out.println("<1> - операции с фильмами");
        System.out.println("<2> - операции с билетами");
        System.out.println("<3> - операции с пользователями");
        System.out.println("<0> - выход из программы");

        int choice = inputCorrectValueFromZeroToThree();

        switch (choice) {
            case 1:
                showMovieOperations();
                break;
            case 2:
                showTicketOperations();
                break;
            case 3:
                showUserOperations();
                break;
            case 0:
                System.exit(0);
        }
    }

    public void showMovieOperations() {
        System.out.println("- - -|      CACTUS CINEMA      |- - -");
        System.out.println("- - -| СТРАНИЦА АДМИНИСТРАТОРА |- - -");
        System.out.println("- - -|   ОПЕРАЦИИ С ФИЛЬМАМИ   |- - -");
        System.out.println("<1> - посмотреть весь список");
        System.out.println("<2> - посмотреть список предстоящих сеансов");
        System.out.println("<3> - редактировать название фильма по номеру учетной записи");
        System.out.println("<4> - редактировать дату и время сеанса по номеру учетной записи");
        System.out.println("<5> - добавить новый сеанс");
        System.out.println("<6> - удалить сеанс");

        System.out.println("<0> - назад");

        int choice = inputCorrectValueFromZeroToSix();

        switch (choice) {
            case 1:
                new AdministratorMenuAction().showMovieList();
                System.out.println();
                showMovieOperations();
                break;
            case 2:
                new AdministratorMenuAction().showUpcomingSessions();
                System.out.println();
                showMovieOperations();
                break;
            case 3:
                new AdministratorMenuAction().changeMovieTitle();
                showMovieOperations();
                break;
            case 4:
                new AdministratorMenuAction().changeMovieSession();
                showMovieOperations();
                break;
            case 5:
                new AdministratorMenuAction().addNewMovieAccount();
                showMovieOperations();
                break;
            case 6:
                new AdministratorMenuAction().deleteMovieAccount();
                showAdminMenu();
                break;
            case 0:
               showAdminMenu();
        }
    }

    public void showTicketOperations() {
        System.out.println("- - -|      CACTUS CINEMA      |- - -");
        System.out.println("- - -| СТРАНИЦА АДМИНИСТРАТОРА |- - -");
        System.out.println("- - -|   ОПЕРАЦИИ С БИЛЕТАМИ   |- - -");
        System.out.println("<1> - купить билет для пользователя");
        System.out.println("<2> - посмотреть билеты пользователя");
        System.out.println("<3> - отменить билет для пользователя");

        System.out.println("<0> - назад");

        int choice = inputCorrectValueFromZeroToThree();

        switch (choice) {
            case 1:
                new AdministratorMenuAction().buyATicketForUser();
                System.out.println();
                showTicketOperations();
                break;
            case 2:
                new AdministratorMenuAction().showUserTickets();
                System.out.println();
                showTicketOperations();
                break;
            case 3:
                new AdministratorMenuAction().cancelTheTicket();
                showTicketOperations();
                break;
            case 0:
                showAdminMenu();
        }
    }

    public void showUserOperations() {
        System.out.println("- - -|      CACTUS CINEMA      |- - -");
        System.out.println("- - -| СТРАНИЦА АДМИНИСТРАТОРА |- - -");
        System.out.println("- - -|ОПЕРАЦИИ С ПОЛЬЗОВАТЕЛЯМИ|- - -");
        System.out.println("<1> - список пользователей");
        System.out.println("<2> - изменить пароль для пользователя");
        System.out.println("<3> - добавить нового пользователя");
        System.out.println("<4> - удалить пользователя и все его данные");

        System.out.println("<0> - назад");

        int choice = inputCorrectValueFromZeroToFour();

        switch (choice) {
            case 1:
                new AdministratorMenuAction().showUserList();
                showUserOperations();
                break;
            case 2:
                new AdministratorMenuAction().changePassword();
                showUserOperations();
                break;
            case 3:
                new Registration().createNewUser();
                showUserOperations();
                break;
            case 4:
                new AdministratorMenuAction().deleteTheUser();
                showUserOperations();
                break;
            case 0:
                showAdminMenu();
        }
    }

    private int inputCorrectValueFromZeroToThree() {
        String valueString = null;
        boolean correctness = false;
        while (correctness == false) {
            valueString = scanString();
            Pattern pattern = Pattern.compile("[0-3]{1}");
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

    private int inputCorrectValueFromZeroToFour() {
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

    private int inputCorrectValueFromZeroToSix() {
        String valueString = null;
        boolean correctness = false;
        while (correctness == false) {
            valueString = scanString();
            Pattern pattern = Pattern.compile("[0-6]{1}");
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
