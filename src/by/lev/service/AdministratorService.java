package by.lev.service;

import by.lev.controller.AdministratorMenu;
import by.lev.service.inputChecks.MovieInputCheck;
import by.lev.exceptions.MovieException;
import by.lev.exceptions.TicketException;
import by.lev.movie.Movie;
import by.lev.movie.MovieDao;
import by.lev.ticket.Ticket;
import by.lev.ticket.TicketDao;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import static by.lev.service.ServiceFunction.*;

public class AdministratorService extends ManagerService {
    Movie movie = new Movie();

    public void addNewMovieAccount(){
        try {
            createNewMovie();
        } catch (MovieException e) {
            throw new RuntimeException(e);
        }
        createMovieTickets();
        System.out.println("уч.запись для фильма '" + movie.getTitle().toUpperCase() + "' на дату " + movie.getDateTime().toString()
                + " со списком билетов успешно добавлена");
    }
    public void createNewMovie() throws MovieException {
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
        while (new MovieInputCheck().inputCorrectDateTimeFormat(sessionDate) == false) {
            sessionDate = scanString();
        }
        if (sessionDate.length() == 16) {
            sessionDate = sessionDate + ":00";
        }
        movieToLoad.setDateTime(Timestamp.valueOf(sessionDate));
        MovieInputCheck mich = new MovieInputCheck();
        if (mich.checkActualityOfTheTime(sessionDate) == false) {
            System.out.println("операция прервана: данная дата уже прошла");
            new AdministratorMenu().showMovieOperations();
        }
        if (mich.isTheSlotOfThisDateTimeOccuped(sessionDate) == true) {
            System.out.println("операция прервана: на данную дату уже запланирован другой сеанс");
            new AdministratorMenu().showMovieOperations();
        }
        MovieDao md = new MovieDao();
        md.create(movieToLoad);
        movie = md.read(movieToLoad.getTitle());
    }
    public void createMovieTickets() {
        Ticket ticket = new Ticket();
        System.out.println("Введите цену билета для данного сеанса...");
        int price = scanInt();
        if(price < 0){
            System.out.println("операция прервана: указана некорректная цена билета");
            new AdministratorMenu().showMovieOperations();
        }
        int count = 1;
        for (int i = 0; i < 10; i++, count++) {
            ticket.setMovieID(movie.getMovieID());
            ticket.setPlace(count);
            ticket.setCost(price);
            try {
                new TicketDao().create(ticket);
            } catch (TicketException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public void deleteMovieAccount(){
        System.out.println("Введите № уч.записи для удаления...");
        int movieID = scanInt();
        try {
            movie = new MovieDao().read(movieID);
        } catch (MovieException e) {
            throw new RuntimeException(e);
        }
        if (movie.getTitle() == null){
            System.out.println("операция прервана: уч.записи с данным номером не найдено");
            new AdministratorMenu().showMovieOperations();
        }
        try{
            new TicketDao().delete(movie);
        } catch (TicketException e) {
            throw new RuntimeException(e);
        }
        try {
            new MovieDao().delete(movieID);
            System.out.println("уч.запись №" + movieID + " удалена");
        } catch (MovieException e) {
            throw new RuntimeException(e);
        }
    }
}
