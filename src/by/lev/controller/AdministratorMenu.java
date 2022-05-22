package by.lev.controller;

import by.lev.service.AdministratorService;
import by.lev.service.ManagerService;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static by.lev.service.ServiceFunction.scanString;

public class AdministratorMenu {
    public void showAdminMenu() {
        System.out.println("- - -|      CACTUS CINEMA      |- - -");
        System.out.println("- - -| СТРАНИЦА АДМИНИСТРАТОРА |- - -");
        System.out.println("<1> - операции с фильмами");
        System.out.println("<2> - операции с билетами");
        System.out.println("<3> - операции с пользователями");
        System.out.println("<0> - выход из программы");

        int choice = inputCorrectValueInTheAdministratorMenu();

        switch (choice) {
            case 1:
                showMovieOperations();
                break;
            case 2:
                //операции с билетами
                break;
            case 3:
                //операции с пользователями
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

        int choice = inputCorrectValueInTheAdministratorMovieMenu();

        switch (choice) {
            case 1:
                new AdministratorService().showMovieList();
                System.out.println();
                showMovieOperations();
                break;
            case 2:
                new AdministratorService().showUpcomingMovies();
                System.out.println();
                showMovieOperations();
                break;
            case 3:
                new AdministratorService().changeMovieTitle();
                showMovieOperations();
                break;
            case 4:
                new AdministratorService().changeMovieSession();
                showMovieOperations();
                break;
            case 5:
                new AdministratorService().addNewMovieAccount();
                showMovieOperations();
                break;
            case 6:
                new AdministratorService().deleteMovieAccount();
                showAdminMenu();
                break;
            case 0:
               showAdminMenu();
        }
    }

    private int inputCorrectValueInTheAdministratorMenu() {
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

    private int inputCorrectValueInTheAdministratorMovieMenu() {
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
