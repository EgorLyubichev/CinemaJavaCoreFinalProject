package by.lev.movie;

import by.lev.exceptions.MovieException;
import by.lev.interfaces.DaoOperation;
import by.lev.ticket.Ticket;

import java.sql.Connection;
import java.sql.Timestamp;
import java.util.List;

public interface MovieDaoInterface<T, R> extends DaoOperation<Movie, Integer> {
    Movie read(String title) throws MovieException;

    boolean update(Integer movieID, String movieTitle) throws MovieException;
    List<Timestamp> readTimestampsOfTheMovie(String title) throws MovieException;
    List<String> readTitles() throws MovieException;
    boolean update(int movieID, Timestamp newDateTime) throws MovieException;
    int readMovieIdOnTheDateTimeRequest(Timestamp dateTime) throws MovieException;
    boolean isTheSlotOfThisDateTimeOccuped(String dateTime) throws MovieException;

}
