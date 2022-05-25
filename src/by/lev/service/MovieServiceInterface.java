package by.lev.service;

import by.lev.movie.Movie;

import java.sql.Timestamp;
import java.util.List;

public interface MovieServiceInterface {
    List<Movie> getAllMovies();
    List<Movie> getUpcomingMovies();
    List<Timestamp> getUpcomingTimestampsOfTheMovie(String title);
    boolean checkIfTitleListContainsTheTitle(String title);
    int getMovieIdOnTheDateTimeRequest(Timestamp timestamp);
    Movie getMovie(int movieID);
    void updateTitleOfMovie(int movieID, String title);
    void updateDateTimeOfMovie(int movieID, Timestamp newTimestamp);
}
