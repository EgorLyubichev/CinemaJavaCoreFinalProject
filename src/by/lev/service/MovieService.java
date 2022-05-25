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
    @Override
    public List<Movie> getAllMovies() {
        List<Movie> movies = new ArrayList<>();
        try {
            movies = new MovieDao().readAll();
        } catch (MovieException e) {
            e.printStackTrace();
        }
        movies.sort(new MovieDateTimeComporator());
        return movies;
    }

    @Override
    public List<Movie> getUpcomingMovies() {
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
    public boolean checkIfTitleListContainsTheTitle(String title) {
        List<String> titles = new ArrayList<>();
        try {
            titles = new MovieDao().readTitles();
        } catch (MovieException e) {
            e.printStackTrace();
        }
        if (titles.contains(title)){return true;}
        return false;
    }

    @Override
    public int getMovieIdOnTheDateTimeRequest(Timestamp timestamp) {
        int result = -1;
        try {
            result =  new MovieDao().readMovieIdOnTheDateTimeRequest(timestamp);
        } catch (MovieException e) {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public Movie getMovie(int movieID) {
        Movie movie = new Movie();
        try {
            movie = new MovieDao().read(movieID);
        } catch (MovieException e) {
            e.printStackTrace();
        }
        return movie;
    }

    @Override
    public void updateTitleOfMovie(int movieID, String title) {
        try {
            new MovieDao().update(movieID, title);
        } catch (MovieException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void updateDateTimeOfMovie(int movieID, Timestamp newTimestamp) {
        try {
            new MovieDao().update(movieID, newTimestamp);
        } catch (MovieException e) {
            e.printStackTrace();
        }
    }
}
