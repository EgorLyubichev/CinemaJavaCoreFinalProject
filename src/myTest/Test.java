package myTest;

import by.lev.controller.userActions.AdministratorController;
import by.lev.exceptions.MovieException;
import by.lev.movie.Movie;
import by.lev.movie.MovieDAO;
import by.lev.movie.MovieDateTimeComporator;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Test {
    public static void main(String[] args) throws MovieException {
       List<Integer> integerList = new ArrayList<>();
       int a = 23;
        for (int i = 0; i < 10; i++, a++) {
            integerList.add(a);
        }
        System.out.println(integerList);
        System.out.println(integerList.contains(3));
    }
}
