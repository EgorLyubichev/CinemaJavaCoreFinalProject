package by.lev.controller;

import by.lev.service.MovieService;
import by.lev.service.TicketService;
import by.lev.service.UserService;
import by.lev.service.inputChecks.MovieInputCheck;
import by.lev.movie.Movie;
import by.lev.ticket.Ticket;
import by.lev.user.User;

import java.sql.Timestamp;
import java.util.List;

import static by.lev.controller.InputFunction.*;

public class AdministratorMenuAction extends ManagerMenuAction {
    Movie movie = new Movie();

    public void addNewMovieAccount() {
        createNewMovie();
        createMovieTickets();
        System.out.println("уч.запись для фильма '" + movie.getTitle().toUpperCase() +
                "' на дату " + movie.getDateTime().toString() + " со списком билетов успешно добавлена");
    }

    public void createNewMovie() {
        Movie movieToLoad = new Movie();
        System.out.println("Введите название фильма...");
        String title = scanString();
        if (title.length() < 1 || title.equals(" ")) {
            System.out.println("операция прервана: некорректное название фильма");
            new AdministratorMenu().showMovieOperations();
        }
        movieToLoad.setTitle(title.toUpperCase());
        System.out.println("Введите дату и время показа фильма...");
        String sessionDate = scanString();
        while (!new MovieInputCheck().inputCorrectDateTimeFormat(sessionDate)) {
            sessionDate = scanString();
        }
        if (sessionDate.length() == 16) {
            sessionDate = sessionDate + ":00";
        }
        movieToLoad.setDateTime(Timestamp.valueOf(sessionDate));
        MovieInputCheck mich = new MovieInputCheck();
        if (!mich.checkActualityOfTheTime(sessionDate)) {
            System.out.println("операция прервана: данная дата уже прошла");
            new AdministratorMenu().showMovieOperations();
        }
        if (new MovieService().isTheSlotOfThisDateTimeOccuped(sessionDate)) {
            System.out.println("операция прервана: на данную дату уже запланирован другой сеанс");
            new AdministratorMenu().showMovieOperations();
        }
        new MovieService().addMovie(movieToLoad);
        movie = new MovieService().getMovie(movieToLoad.getTitle());
    }

    public void createMovieTickets() {
        Ticket ticket = new Ticket();
        System.out.println("Введите цену билета для данного сеанса...");
        int price = scanInt();
        if (price < 0) {
            System.out.println("операция прервана: указана некорректная цена билета");
            new AdministratorMenu().showMovieOperations();
        }
        int count = 1;
        for (int i = 0; i < 10; i++, count++) {
            ticket.setMovieID(movie.getMovieID());
            ticket.setPlace(count);
            ticket.setCost(price);
            new TicketService().addTicket(ticket);
        }
    }

    public void deleteMovieAccount() {
        System.out.println("Введите № уч.записи для удаления...");
        int movieID = scanInt();
        movie = new MovieService().getMovie(movieID);
        if (movie.getTitle() == null) {
            System.out.println("операция прервана: уч.записи с данным номером не найдено");
            new AdministratorMenu().showMovieOperations();
        }
        new TicketService().removeTicket(movie.getMovieID());
        new MovieService().removeMovie(movieID);
        System.out.println("уч.запись №" + movieID + " удалена");
    }

    public void changePassword() {
        System.out.println("введите логин пользователя для смены пароля...");
        String login = scanString();
        User user = new UserService().getUser(login);
        if (user.getPassword() == null) {
            System.out.println("операция прервана: такого пользователя нет в базе");
        }
        System.out.println("введите новый пароль...");
        String newPassword = scanString();
        while (new Registration().checkTheCorrectnessOfThePasswordInput(newPassword) == false) {
            newPassword = scanString();
        }
        System.out.println("повторите пароль...");
        String repeatPassword = scanString();
        if (!newPassword.equals(repeatPassword)) {
            System.out.println("введенные пароли не совпадают");
            new AdministratorMenu().showUserOperations();
        }
        new UserService().updatePassword(login, newPassword);
        System.out.println("пароль успешно обновлен");

    }

    public void deleteTheUser() {
        System.out.println("введите логин пользователя для удаления...");
        String login = scanString();
        User user = new UserService().getUser(login);
        if (user.getPassword() == null) {
            System.out.println("операция прервана: пользователя с логином " + login + " в базе нет");
            new AdministratorMenu().showUserMenu();
        }
        new UserService().removeUser(login);
        System.out.println("пользователь " + login + " удален");
        List<Integer> userTicketNumbers = new TicketService().getTicketNumbersOfUser(user);
        String positiveResault = "данные пользователя " + login + " успешно удалены";
        if (userTicketNumbers.isEmpty()) {
            System.out.println(positiveResault);
        } else {
            for (Integer value : userTicketNumbers) {
                new TicketService().removeUsernameFromTicket(value);
            }
            System.out.println(positiveResault);
        }
    }
}
