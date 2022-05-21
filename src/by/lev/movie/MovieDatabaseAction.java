package by.lev.movie;

import by.lev.exceptions.MovieException;
import by.lev.interfaces.CrudOperation;

import java.sql.Timestamp;
import java.util.List;

public interface MovieDatabaseAction<M, I extends Number> extends CrudOperation<Movie, Integer> {
    List<Timestamp> getTheDateTimeOnRequestMovieTitle(String title);
    boolean update(Movie oldMovie, Movie newMovie) throws MovieException;
    int getMovieIdOnTheDateTimeRequest(Timestamp dateTime) throws MovieException;

}
