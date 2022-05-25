package by.lev.service;

import by.lev.movie.Movie;

import java.sql.Timestamp;
import java.util.List;

public interface MovieServiceInterface {
    List<Movie> getUpcomingMovieSession();
    List<Timestamp> getUpcomingTimestampsOfTheMovie(String title);
    boolean checkIfTheTitleContainsInTheTitleList(String title);
}
