package by.lev.controller;

import by.lev.movie.Movie;
import by.lev.service.*;
import by.lev.ticket.Ticket;
import by.lev.user.User;

import java.sql.Timestamp;
import java.util.List;

import static by.lev.Constant.INCORRECT_INPUT;
import static by.lev.controller.InputFunction.*;

public class ManagerController extends UserController implements ManagerControllerInterface{


    public ManagerController(UserServiceInterface usServ, TicketServiceInterface tickServ, MovieServiceInterface movServ) {
        super(usServ, tickServ, movServ);
    }

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

        int inputValue = new InputCorrectness().inputCorrectValueFromZeroToEight();
        if (inputValue == -1){
            System.out.println(INCORRECT_INPUT.getMessage());
            System.out.println();
            showManagerMenu();
        }

        switch (inputValue) {
            case 1:
                showMovieList();
                System.out.println();
                showManagerMenu();
                break;
            case 2:
                showUpcomingSessions();
                System.out.println();
                showManagerMenu();
                break;
            case 3:
                showUserList();
                showManagerMenu();
                break;
            case 4:
                buyATicketForUser();
                showManagerMenu();
                break;
            case 5:
                showTheUserTickets();
                showManagerMenu();
                break;
            case 6:
                cancelTheTicket();
                showManagerMenu();
                break;
            case 7:
                changeMovieTitle();
                showManagerMenu();
                break;
            case 8:
                changeMovieSession();
                showManagerMenu();
                break;
            case 0:
                System.exit(0);
        }
    }

    public void showMovieList() {
        List<Movie> movieList = movServ.getAllMovies();
        movieList.forEach(System.out::println);
    }

    public void showUpcomingSessions() {
        List<Movie> upcomingMovies = movServ.getUpcomingMovies();
        upcomingMovies.forEach(System.out::println);
    }

    public void showUserList() {
        List<String> usernames = usServ.getLoginsOfUsers();
        usernames.forEach(System.out::println);
        System.out.println();
    }

    public void buyATicketForUser() {
        System.out.println("введите логин пользователя...");
        String login = scanString();
        checkLoginInBase(login);
        System.out.println("введите номер билета для покупки...");
        int ticketID = scanInt();
        checkIfTheTicketIsFree(ticketID);
        User user = new User();
        user.setLogin(login);
        tickServ.assignTheUserInTheTicket(ticketID, user);
        System.out.println("билет № " + ticketID + " для пользователя <" + login + "> приобретён");
        System.out.println("- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -");
    }

    public void showTheUserTickets() {
        System.out.println("введите логин пользователя...");
        String login = scanString();
        checkLoginInBase(login);
        User user = new User();
        user.setLogin(login);
        List<Ticket> userTickets = tickServ.getUserTickets(user);
        if (userTickets.isEmpty()) {
            System.out.println("у данного пользователя билетов не имеется");
            System.out.println("- - - - - - - - - - - - - - - - - - - - -");
            showManagerMenu();
        }
        for (Ticket ticket : userTickets) {
            int movieID = ticket.getMovieID();
            Movie movie = movServ.getMovie(movieID);
            StringBuilder strB = new StringBuilder("Билет № ");
            strB.append(ticket.getTicketID()).append(" | ").append(movie.getTitle()).append(" | ")
                    .append(movie.getDateTime().toString().substring(0, 16)).append(" | владелец: ")
                    .append(login).append(" | место: ").append(ticket.getPlace()).append(" | цена: ")
                    .append(ticket.getCost()).append("$");
            System.out.println(strB);
        }
    }

    public void cancelTheTicket() {
        System.out.println("введите логин пользователя...");
        String login = scanString();
        checkLoginInBase(login);
        System.out.println("введите номер билета для отмены...");
        int ticketID = scanInt();
        checkIfTheTicketIsOfTheUser(ticketID, login);
            tickServ.removeUsernameFromTicket(ticketID);
            System.out.println("билет для пользователя " + login + " №" + ticketID + " отменен");
            System.out.println("- - - - - - - - - - - - - - - - - - - - - - - - - - - -");
            System.out.println();
    }

    public void checkLoginInBase(String login) {
        List<String> userLoginList = usServ.getLoginsOfUsers();
        if (!userLoginList.contains(login)) {
            System.out.println("Операция прервана: пользователь с таким логином не зарегистрирован!");
            System.out.println("- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -");
            showManagerMenu();
        }
    }

    public void checkIfTheTicketIsFree(int ticketID) {
        Ticket ticket = tickServ.getTicket(ticketID);
        if (ticket.getUserName() != null) {
            String username = ticket.getUserName();
            System.out.println("Отказано в доступе: данный билет принадлежит пользователю <" + username + ">");
            System.out.println("- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -");
            showManagerMenu();
        }
    }

    public void checkIfTheTicketIsOfTheUser(int ticketID, String username) {
        Ticket ticket = tickServ.getTicket(ticketID);
        if (!ticket.getUserName().equals(username)) {
            System.out.println("Отказано в доступе: данный билет №" + ticketID +
                    " не принадлежит пользователю <" + username + ">");
            System.out.println("- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -");
            showManagerMenu();
        }
    }

    public void changeMovieTitle() {
        System.out.println("введите № учетной записи фильма...");
        int movieID = scanInt();
        Movie movie = movServ.getMovie(movieID);
        if (movie.getTitle() == null) {
            System.out.println("! - операция прервана: фильма с таким номером в базе не существует!");
            showManagerMenu();
        }
        System.out.println("фильм: " + movie.getTitle());
        System.out.println("введите новое название фильма...");
        String movieTitle = scanString();
        if (movieTitle.length() < 1 || movieTitle.equals(" ")) {
            System.out.println("! - операция прервана: некорректное название фильма!");
            showManagerMenu();
        }
        movServ.updateTitleOfMovie(movieID, movieTitle.toUpperCase());
        System.out.println("уч.запись №" + movieID + ": название '" + movie.getTitle() + "' успешно заменено на '" + movieTitle.toUpperCase() + "'");
        System.out.println("- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -");
    }

    public void changeMovieSession() {
        System.out.println("введите № уч. записи фильма...");
        int movieID = scanInt();
        Movie movie = movServ.getMovie(movieID);
        if (movie.getTitle() == null) {
            System.out.println("! - операция прервана: фильма с таким номером в базе не существует!");
            showManagerMenu();
        }
        System.out.println("фильм: " + movie.getTitle() + " | " + movie.getDateTime().toString().substring(0, 16));
        System.out.println("введите новые дату и время фильма...");
        String movieDateTime = scanString();
        while (!movServ.inputCorrectDateTimeFormat(movieDateTime)) {
            System.out.println("Формат даты должен быть: 'гггг-мм-дд чч:мм' или 'гггг-мм-дд чч:мм:сс'");
            movieDateTime = scanString();
        }
        Timestamp newTimestamp = Timestamp.valueOf(correctDateTime(movieDateTime));
        movServ.updateDateTimeOfMovie(movieID, newTimestamp);
        System.out.println("уч.запись №" + movieID + " | '" + movie.getTitle() + "': дата сеанса изменена на " + movieDateTime);
        System.out.println("- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -");
    }

    private static String correctDateTime(String dateTime) {
        if (dateTime.length() == 16) {
            String withCorrect = dateTime + ":00";
            return withCorrect;
        }
        return dateTime;
    }
}
