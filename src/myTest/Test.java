package myTest;

import by.lev.exceptions.MovieException;
import by.lev.movie.Movie;
import by.lev.movie.MovieDao;

import java.util.ArrayList;
import java.util.List;

public class Test {
    public static void main(String[] args) throws MovieException {

        String a = "Aaaa";
        String b = "BBBB";
        String c = "ccCcc";
        List<String> words = new ArrayList<>();
        words.add(a);
        words.add(b);
        words.add(c);
        List<String> wordsToLowwerCase = new ArrayList<>();
        for (String word:words) {
            wordsToLowwerCase.add(word.toLowerCase());
        }

        System.out.println(wordsToLowwerCase);
    }
}
