package myTest;

import by.lev.exceptions.MovieException;
import by.lev.movie.Movie;
import by.lev.movie.MovieDao;

import java.util.ArrayList;
import java.util.List;

public class Test {
    public static void main(String[] args) throws MovieException {
        MovieDao md = new MovieDao();
        Movie movie = new Movie();
        md.create(movie);
    }
}
