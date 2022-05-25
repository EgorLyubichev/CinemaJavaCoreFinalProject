package by.lev.movie;

import by.lev.exceptions.MovieException;
import by.lev.interfaces.CrudOperation;

import java.sql.Timestamp;
import java.util.List;

public interface MovieDatabaseAction<T, R, S> extends CrudOperation<Movie, Integer, String> {
    List<Timestamp> readTimestampsOfTheMovie(String title) throws MovieException;
    List<String> readTitles() throws MovieException;
    boolean update(int movieID, Timestamp newDateTime) throws MovieException;
    int readMovieIdOnTheDateTimeRequest(Timestamp dateTime) throws MovieException;

}
