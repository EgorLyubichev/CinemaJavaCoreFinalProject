package by.lev.controller;

import static by.lev.Constant.INCORRECT_INPUT;
import static by.lev.controller.Authorization.USER_ONLINE;

import by.lev.movie.Movie;
import by.lev.service.InputCorrectness;
import by.lev.service.MovieService;
import by.lev.service.TicketService;
import by.lev.service.UserService;
import by.lev.ticket.Ticket;

import static by.lev.controller.InputFunction.*;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserController {
    Movie movie = new Movie();
    Ticket ticket = new Ticket();
    List<Ticket> freeTickets;
    List<Timestamp> timestampList = new ArrayList<>();

    public void showUserMenu() {
        System.out.println("- - -|  CACTUS CINEMA  |- - -");
        System.out.println("- - -| ГЛАВНАЯ СТРАНИЦА|- - -");
        System.out.println("<1> - предстоящие фильмы");
        System.out.println("<2> - купить билет");
        System.out.println("<3> - купленные билеты");
        System.out.println("<4> - отменить билет");
        System.out.println("<5> - сменить пароль");
        System.out.println("<0> - выход из программы");

        int inputValue = new InputCorrectness().inputCorrectValueFromZeroToFive();
        if (inputValue == -1){
            System.out.println(INCORRECT_INPUT.getMessage());
            System.out.println();
            showUserMenu();
        }
        switch (inputValue) {
            case 1:
                showUpcomingSessions();
                System.out.println();
                showUserMenu();
                break;
            case 2:
                buyATicket();
                break;
            case 3:
                showUserTickets();
                showUserMenu();
                break;
            case 4:
                cancelTheTicket();
                showUserMenu();
                break;
            case 5:
                changePassword();
                showUserMenu();
                break;
            case 0:
                System.exit(0);
        }
    }

    public void showUpcomingSessions() {
        List<Movie> upcomingMovies = new MovieService().getUpcomingMovies();
        for (Movie movie : upcomingMovies) {
            StringBuilder strB = new StringBuilder(movie.getDateTime().toString().substring(0, 16))
                    .append(" | ").append(movie.getTitle())
                    .append("\n- - - - - - - - - - - - - - - - - -");
            System.out.println(strB);
        }
    }

    public void buyATicket() {
        showUpcomingSessionsOfTheMovie();
        showFreeTicketsOfTheSession();
        chooseATicket();
        buyingTheTicket();
    }

    public void showUpcomingSessionsOfTheMovie() {
        System.out.println("Введите название фильма...");
        String title = scanString();
        title = title.toUpperCase().trim();
        if (new MovieService().checkIfTitleListContainsTheTitle(title)) {
            System.out.println("Предстоящие сеансы:");
            List<Timestamp> movieSessions = new MovieService().getUpcomingTimestampsOfTheMovie(title);
            if (movieSessions.isEmpty()) {
                System.out.println("\tна данный фильм сеансов пока не назначено");
            } else {
                movie.setTitle(title);
                int count = 1;
                for (int i = 0; i < movieSessions.size(); i++, count++) {
                    System.out.println("<" + count + "> - " + movieSessions.get(i).toString().substring(0, 16));
                }
            }
            timestampList = movieSessions;
        } else {
            System.out.println("операция прервана: заданного названия в списке имеющихся фильмов нет");
            showUserMenu();
        }
    }

    public void showFreeTicketsOfTheSession() {
        System.out.println("Выберите сеанс из предложенных...");
        boolean correctInput = false;
        while (!correctInput) {
            int choice = scanInt();
            if (choice > 0 && choice <= timestampList.size()) {
                movie.setDateTime(timestampList.get(choice - 1));
                correctInput = true;
            } else {
                System.out.println("Неверный запрос! Повторите попытку.");
                correctInput = false;
            }
        }
        System.out.println("Имеющиеся билеты:");
        movie.setMovieID(new MovieService().getMovieIdOnTheDateTimeRequest(movie.getDateTime()));
        freeTickets = new TicketService().getFreeTicketsOfTheSession(movie.getMovieID());
        if (freeTickets.isEmpty()) {
            System.out.println("\tк сожалению, на данный сеанс свободных билетов не осталось");
            showUserMenu();
        } else {
            for (Ticket ticket : freeTickets) {
                String dateForViewing = movie.getDateTime().toString().substring(0, 16);
                StringBuilder strB = new StringBuilder(dateForViewing);
                strB.append(" | ").append(movie.getTitle()).append(" | № билета: ").
                        append(ticket.getTicketID()).append(" | место: ").append(ticket.getPlace()).
                        append(" | ").append("цена билета: ").append(ticket.getCost()).append("$");
                System.out.println(strB);
            }
        }
    }

    public void chooseATicket() {
        List<Integer> freeTicketNumbers = new ArrayList<>();
        for (Ticket freeTicket : freeTickets) {
            freeTicketNumbers.add(freeTicket.getTicketID());
        }
        System.out.println("Выберите нужный билет... (№ билета)");
        int choice = scanInt();
        if (freeTicketNumbers.contains(choice)) {
            ticket = new TicketService().getTicket(choice);
            System.out.println("Билет " + ticket.getTicketID() + " выбран!");
        } else {
            System.out.println("Неверный ввод данных");
            chooseATicket();
        }
    }

    public void buyingTheTicket() {
        System.out.println("Стоимость билета " + ticket.getCost() + "$");
        System.out.println("<1> - оплатить билет\n<2> - вернуться в основное меню");
        int choice = scanInt();
        switch (choice) {
            case 1:
                new TicketService().assignTheUserInTheTicket(ticket.getTicketID(), USER_ONLINE);
                System.out.println("Билет оплачен!");
                System.out.println("<1> - купить новый билет\n<2> - перейти в основное меню");
                boolean inputCorrect = false;
                while (!inputCorrect) {
                    int choice2 = scanInt();
                    if (choice2 == 1) {
                        inputCorrect = true;
                        buyATicket();
                    } else if (choice2 == 2) {
                        inputCorrect = true;
                        showUserMenu();
                    } else {
                        System.out.println("Неверный ввод данных");
                    }
                }
                break;
            case 2:
                showUserMenu();
                break;
            default:
                System.out.println("Неверный ввод данных!");
                buyingTheTicket();
        }
    }

    public void showUserTickets() {
        List<Ticket> userTickets;
        Movie movie;
        userTickets = new TicketService().getUserTickets(USER_ONLINE);
        for (Ticket ticket : userTickets) {
            int movieID = ticket.getMovieID();
            movie = new MovieService().getMovie(movieID);
            StringBuilder strB = new StringBuilder("Билет № ");
            strB.append(ticket.getTicketID()).append(" | ").append(movie.getTitle()).append(" | ")
                    .append(movie.getDateTime().toString().substring(0, 16)).append(" | владелец: ")
                    .append(USER_ONLINE.getLogin()).append(" | место: ").append(ticket.getPlace())
                    .append(" | цена: ").append(ticket.getCost()).append("$");
            System.out.println(strB);
        }
    }

    public boolean checkTicketNumberInTheUserCollection(int ticketID) {
        List<Integer> userTicketIdList = new TicketService().getTicketNumbersOfUser(USER_ONLINE);
        if (userTicketIdList.contains(ticketID)) {
            return true;
        }
        return false;
    }

    public void cancelTheTicket() {
        System.out.println("Ваши билеты:");
        showUserTickets();
        System.out.println("Введите номер билета для отмены...");
        int choice = scanInt();
        if (checkTicketNumberInTheUserCollection(choice)) {
            new TicketService().removeUsernameFromTicket(choice);
            System.out.println("Ваш запрос выполнен!");
        } else {
            System.out.println("Введенного номера билета в списке ваших не имеется!");
        }
        System.out.println();
        System.out.println("Ваши билеты:");
        showUserTickets();
        System.out.println();
    }

    public void changePassword() {
        System.out.println("введите свой пароль...");
        String oldPassword = scanString();
        if (!USER_ONLINE.getPassword().equals(oldPassword)) {
            System.out.println("в операции отказано: вы ввели неверный пароль");
            showUserMenu();
        }
        System.out.println("введите новый пароль...");
        String newPassword = scanString();
        while (!new Registration().checkTheCorrectnessOfThePasswordInput(newPassword)) {
            newPassword = scanString();
        }
        System.out.println("повторите пароль...");
        String repeatPassword = scanString();
        if (!newPassword.equals(repeatPassword)) {
            System.out.println("введенные пароли не совпадают");
            showUserMenu();
        }
        new UserService().updatePassword(USER_ONLINE.getLogin(), newPassword);
        System.out.println("пароль успешно обновлен");
    }
}
