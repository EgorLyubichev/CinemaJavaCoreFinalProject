package by.lev.movie;

import by.lev.exceptions.MovieException;
import by.lev.interfaces.CrudOperation;

import java.sql.Timestamp;
import java.util.List;

public interface MovieDatabaseAction<T, R, S> extends CrudOperation<Movie, Integer, String> {
    List<Timestamp> getTheDateTimeOnRequestMovieTitle(String title);
    boolean update(int movieID, Timestamp newDateTime) throws MovieException;
    int getMovieIdOnTheDateTimeRequest(Timestamp dateTime) throws MovieException;

}
