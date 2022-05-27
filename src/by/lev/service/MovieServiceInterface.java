package by.lev.service;

import by.lev.movie.Movie;

import java.sql.Timestamp;
import java.util.List;

public interface MovieServiceInterface {
    void addMovie(Movie movie);
    List<Movie> getAllMovies();
    List<Movie> getUpcomingMovies();
    List<Timestamp> getUpcomingTimestampsOfTheMovie(String title);
    boolean checkIfTitleListContainsTheTitle(String title);
    int getMovieIdOnTheDateTimeRequest(Timestamp timestamp);
    Movie getMovie(int movieID);
    Movie getMovie(String title);
    void updateTitleOfMovie(int movieID, String title);
    void updateDateTimeOfMovie(int movieID, Timestamp newTimestamp);
    void removeMovie(int movieID);
    boolean isTheSlotOfThisDateTimeOccuped(String dateTime);
    boolean checkActualityOfTheTime(String dateTime);
    boolean inputCorrectDateTimeFormat(String dateTime);
}
