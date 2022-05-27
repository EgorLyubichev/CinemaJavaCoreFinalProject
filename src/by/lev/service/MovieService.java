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
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MovieService implements MovieServiceInterface {
    @Override
    public void addMovie(Movie movie) {
        try {
            new MovieDao().create(movie);
        } catch (MovieException e) {
            e.printStackTrace();
        }
    }

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
        for (Timestamp timestamp : timestamps) {
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
        return titles.contains(title);
    }

    @Override
    public int getMovieIdOnTheDateTimeRequest(Timestamp timestamp) {
        int result = -1;
        try {
            result = new MovieDao().readMovieIdOnTheDateTimeRequest(timestamp);
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
    public Movie getMovie(String title) {
        Movie movie = new Movie();
        try {
            movie = new MovieDao().read(title);
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

    @Override
    public void removeMovie(int movieID) {
        try {
            new MovieDao().delete(movieID);
        } catch (MovieException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean isTheSlotOfThisDateTimeOccuped(String dateTime) {
        Boolean result = null;
        try {
            result = new MovieDao().isTheSlotOfThisDateTimeOccuped(dateTime);
        } catch (MovieException e) {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public boolean checkActualityOfTheTime(String dateTime) {
        Timestamp currentTime = Timestamp.valueOf(LocalDateTime.now());
        if (Timestamp.valueOf(dateTime).before(currentTime)) {
            return false;
        }
        return true;
    }

    @Override
    public boolean inputCorrectDateTimeFormat(String dateTime) {
        Pattern pattern = Pattern.compile("[\\d]{4}\\-[\\d]{2}\\-[\\d]{2}\\ [\\d]{2}\\:[\\d]{2}");
        Matcher matcher = pattern.matcher(dateTime);
        Pattern pattern2 = Pattern.compile("[\\d]{4}\\-[\\d]{2}\\-[\\d]{2}\\ [\\d]{2}\\:[\\d]{2}\\:[\\d]{2}");
        Matcher matcher2 = pattern2.matcher(dateTime);
        if (matcher.matches() || matcher2.matches()) {
            return true;
        } else {
            return false;
        }
    }
}
