package by.lev.movie;

import java.util.Comparator;

public class MovieDateTimeComporator implements Comparator<Movie> {

    @Override
    public int compare(Movie o1, Movie o2) {
        return o1.getDateTime().compareTo(o2.getDateTime());
    }
}
