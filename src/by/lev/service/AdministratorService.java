package by.lev.service;

import by.lev.service.inputChecks.MovieInputCheck;
import by.lev.exceptions.MovieException;
import by.lev.exceptions.TicketException;
import by.lev.movie.Movie;
import by.lev.movie.MovieDao;
import by.lev.ticket.Ticket;
import by.lev.ticket.TicketDao;

import java.sql.Timestamp;

import static by.lev.service.ServiceFunction.*;

public class AdministratorService extends ManagerService {
    Movie movie = new Movie();

    public void addNewMovie() throws MovieException {
        System.out.println("Введите название фильма...");
        String title = scanString();
        System.out.println("Введите дату и время показа фильма...");
        boolean checkDateCorrectness = false;
        while (checkDateCorrectness == false) {
            String sessionDate = scanString();
            if (sessionDate.length() == 16) {
                sessionDate = sessionDate + ":00";
            }
            MovieInputCheck mich = new MovieInputCheck();
            if (mich.inputCorrectDateTimeFormat(sessionDate) == true &&
                    mich.checkActualityOfTheTime(sessionDate) == true &&
                    mich.isTheSlotOfThisDateTimeOccuped(sessionDate) == false) {
                Movie movieToLoad = new Movie();
                movieToLoad.setTitle(title);
                movieToLoad.setDateTime(Timestamp.valueOf(sessionDate));
                MovieDao md = new MovieDao();
                md.create(movieToLoad);

                        checkDateCorrectness = true;
            }
        }
    }

    public void createMovieTickets() { //метод не доработан !!!
        System.out.println();
        Ticket ticket = new Ticket();
        int count = 1;
        for (int i = 0; i < 10; i++, count++) {
            ticket.setMovieID(8);
            ticket.setPlace(count);
            ticket.setCost(7);
            try {
                new TicketDao().create(ticket);
            } catch (TicketException e) {
                throw new RuntimeException(e);
            }
            System.out.println(count);
        }
    }
}
