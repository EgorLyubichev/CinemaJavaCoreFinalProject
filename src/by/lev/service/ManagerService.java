package by.lev.service;

import by.lev.controller.ManagerMenu;
import by.lev.exceptions.MovieException;
import by.lev.exceptions.TicketException;
import by.lev.exceptions.UserException;
import by.lev.movie.Movie;
import by.lev.movie.MovieDao;
import by.lev.movie.MovieDateTimeComporator;
import by.lev.service.inputChecks.MovieInputCheck;
import by.lev.ticket.Ticket;
import by.lev.ticket.TicketDao;
import by.lev.user.User;
import by.lev.user.UserDao;
import by.lev.user.UserNameComparator;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static by.lev.service.ServiceFunction.*;

public class ManagerService extends UserService {

    public void showMovieList() {
        List<Movie> movieList = new ArrayList<>();
        try {
            movieList = new MovieDao().readAll();
        } catch (MovieException e) {
            throw new RuntimeException(e);
        }
        movieList.sort(new MovieDateTimeComporator());
        movieList.forEach(System.out::println);
    }

    public void showUpcomingMovies() {
        List<Movie> movieCollection = new ArrayList<>();
        try {
            movieCollection = new MovieDao().readAll();
        } catch (MovieException e) {
            e.printStackTrace();
        }
        for (Movie movie : movieCollection) {
            if (movie.getDateTime().after(Timestamp.valueOf(LocalDateTime.now()))) {
                upcomingMovies.add(movie);
            }
        }
        upcomingMovies.sort(new MovieDateTimeComporator());
        upcomingMovies.forEach(System.out::println);
    }

    public void showUserList() {
        List<String> usernames = new ArrayList<>();
        try {
            usernames = new UserDao().getUserNameList();
        } catch (UserException e) {
            throw new RuntimeException(e);
        }
        usernames.sort(new UserNameComparator());
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
        try {
            new TicketDao().update(ticketID, user);
            System.out.println("билет № " + ticketID + " для пользователя <" + login + "> приобретён");
            System.out.println("- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -");
        } catch (TicketException e) {
            throw new RuntimeException(e);
        }
    }

    public void showTheUserTickets() {
        System.out.println("введите логин пользователя...");
        String login = scanString();
        checkLoginInBase(login);
        User user = new User();
        user.setLogin(login);
        List<Ticket> userTickets;
        try {
            userTickets = new TicketDao().readAll(user);
        } catch (TicketException e) {
            throw new RuntimeException(e);
        }
        if (userTickets.isEmpty()) {
            System.out.println("у данного пользователя билетов не имеется");
            System.out.println("- - - - - - - - - - - - - - - - - - - - -");
            new ManagerMenu().showManagerMenu();
        }
        Movie movie;
        for (Ticket ticket : userTickets) {
            int movieID = ticket.getMovieID();
            try {
                movie = new MovieDao().read(movieID);
                StringBuilder strB = new StringBuilder("Билет № ");
                strB.append(ticket.getTicketID()).append(" | ").append(movie.getTitle()).append(" | ")
                        .append(movie.getDateTime().toString().substring(0, 16)).append(" | владелец: ").append(login).append(" | место: ")
                        .append(ticket.getPlace()).append(" | цена: ").append(ticket.getCost()).append("$");
                System.out.println(strB);
                System.out.println();
            } catch (MovieException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public void cancelTheTicket() {
        System.out.println("введите логин пользователя...");
        String login = scanString();
        checkLoginInBase(login);
        System.out.println("введите номер билета для отмены...");
        int ticketID = scanInt();
        checkIfTheTicketIsOfTheUser(ticketID, login);
        try {
            new TicketDao().update(ticketID);
            System.out.println("билет для пользователя " + login + " №" + ticketID + " отменен");
            System.out.println("- - - - - - - - - - - - - - - - - - - - - - - - - - - -");
            System.out.println();
        } catch (TicketException e) {
            throw new RuntimeException(e);
        }
    }

    public void checkLoginInBase(String login) {
        try {
            List<String> userLoginList = new UserDao().getUserNameList();
            if (!userLoginList.contains(login)) {
                System.out.println("Операция прервана: пользователь с таким логином не зарегистрирован!");
                System.out.println("- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -");
                new ManagerMenu().showManagerMenu();
            }
        } catch (UserException e) {
            throw new RuntimeException(e);
        }
    }

    public void checkIfTheTicketIsFree(int ticketID) {
        try {
            Ticket ticket = new TicketDao().read(ticketID);
            if (ticket.getUserName() != null) {
                String username = ticket.getUserName();
                System.out.println("Отказано в доступе: данный билет принадлежит пользователю <" + username + ">");
                System.out.println("- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -");
                new ManagerMenu().showManagerMenu();
            }
        } catch (TicketException e) {
            throw new RuntimeException(e);
        }
    }

    public void checkIfTheTicketIsOfTheUser(int ticketID, String username) {
        try {
            Ticket ticket = new TicketDao().read(ticketID);
            if (!ticket.getUserName().equals(username)) {
                System.out.println("Отказано в доступе: данный билет №" + ticketID +
                        " не принадлежит пользователю <" + username + ">");
                System.out.println("- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -");
                new ManagerMenu().showManagerMenu();
            }
        } catch (TicketException e) {
            throw new RuntimeException(e);
        }
    }

    public void changeMovieTitle() {
        Movie movie = new Movie();
        System.out.println("введите № учетной записи фильма...");
        int movieID = scanInt();
        try {
            movie = new MovieDao().read(movieID);
            if (movie.getTitle() == null) {
                System.out.println("! - операция прервана: фильма с таким номером в базе не существует!");
                new ManagerMenu().showManagerMenu();
            }
        } catch (MovieException e) {
            throw new RuntimeException(e);
        }
        System.out.println("фильм: " + movie.getTitle());
        System.out.println("введите новое название фильма...");
        String movieTitle = scanString();
        if (movieTitle.length() < 1 || movieTitle.equals(" ")) {
            System.out.println("! - операция прервана: некорректное название фильма!");
            new ManagerMenu().showManagerMenu();
        }
        try {
            new MovieDao().update(movieID, movieTitle.toUpperCase());
            System.out.println("уч.запись №" + movieID + ": название '" + movie.getTitle() + "' успешно заменено на '" + movieTitle.toUpperCase() + "'");
            System.out.println("- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -");
        } catch (MovieException e) {
            throw new RuntimeException(e);
        }
    }

    public void changeMovieSession() {
        Movie movie = new Movie();
        System.out.println("введите № уч. записи фильма...");
        int movieID = scanInt();
        try {
            movie = new MovieDao().read(movieID);
            if (movie.getTitle() == null) {
                System.out.println("! - операция прервана: фильма с таким номером в базе не существует!");
                new ManagerMenu().showManagerMenu();
            }
        } catch (MovieException e) {
            throw new RuntimeException(e);
        }
        System.out.println("фильм: " + movie.getTitle() + " | " + movie.getDateTime().toString().substring(0,16));
        System.out.println("введите новые дату и время фильма...");
        String movieDateTime = scanString();
        while (new MovieInputCheck().inputCorrectDateTimeFormat(movieDateTime) == false) {
            movieDateTime = scanString();
        }
        Timestamp newTimestamp = Timestamp.valueOf(correctDateTime(movieDateTime));
        try {
            new MovieDao().update(movieID, newTimestamp);
            System.out.println("уч.запись №" + movieID + " | '" + movie.getTitle() + "': дата сеанса изменена на " + movieDateTime);
            System.out.println("- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -");
        } catch (MovieException e) {
            throw new RuntimeException(e);
        }
    }
    private static String correctDateTime(String dateTime){
        if (dateTime.length() == 16){
            String withCorrect = dateTime + ":00";
            return withCorrect;
        }return dateTime;
    }
}
