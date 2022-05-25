package by.lev.service;

import by.lev.exceptions.MovieException;
import by.lev.movie.DateTimeComparator;
import by.lev.movie.Movie;
import by.lev.movie.MovieDao;
import by.lev.movie.MovieDateTimeComporator;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class MovieService implements MovieServiceInterface {
    public List<Movie> getUpcomingMovieSession() {
        List<Movie> movieList = new ArrayList<>();
        List<Movie> upcomingMovies = new ArrayList<>();
        try {
            movieList = new MovieDao().readAll();
        } catch (MovieException e) {
            e.printStackTrace();
        }
        for (Movie movie : movieList) {
            if (movie.getDateTime().after(Timestamp.valueOf(LocalDateTime.now()))) {
                upcomingMovies.add(movie);
            }
        }
        upcomingMovies.sort(new MovieDateTimeComporator());
        return upcomingMovies;
    }

    @Override
    public List<Timestamp> getUpcomingTimestampsOfTheMovie(String title) {
        List<Timestamp> timestamps = new ArrayList<>();
        List<Timestamp> upcomingTimestamps = new ArrayList<>();
        try {
            timestamps = new MovieDao().readTimestampsOfTheMovie(title);
        } catch (MovieException e) {
            e.printStackTrace();
        }
        for (Timestamp timestamp: timestamps) {
            if (timestamp.after(Timestamp.valueOf(LocalDateTime.now()))) {
                upcomingTimestamps.add(timestamp);
            }
        }
        upcomingTimestamps.sort(new DateTimeComparator());
        return upcomingTimestamps;
    }

    @Override
    public boolean checkIfTheTitleContainsInTheTitleList(String title) {
        List<String> titles = new ArrayList<>();
        try {
            titles = new MovieDao().readTitles();
        } catch (MovieException e) {
            e.printStackTrace();
        }
        if (titles.contains(title)){return true;}
        return false;
    }


}
