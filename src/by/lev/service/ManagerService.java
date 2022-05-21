package by.lev.service;

import by.lev.controller.ManagerMenu;
import by.lev.exceptions.MovieException;
import by.lev.exceptions.UserException;
import by.lev.movie.Movie;
import by.lev.movie.MovieDao;
import by.lev.movie.MovieDateTimeComporator;
import by.lev.user.UserDao;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static by.lev.service.ServiceFunction.*;

public class ManagerService extends UserService {
//      <1> - посмотреть весь список фильмов");
//      <2> - посмотреть список предстоящих фильмов");
//      <3> - посмотреть список пользователей");
//<4> - купить билет для пользователя");
//<5> - просмотреть купленные билеты пользователя"
//<6> - отменить билет пользователя");

    public void showMovieList(){
        List<Movie> movieList = new ArrayList<>();
        try {
            movieList = new MovieDao().readAll();
        } catch (MovieException e) {
            throw new RuntimeException(e);
        }
        movieList.sort(new MovieDateTimeComporator());
        movieList.forEach(System.out::println);
    }

    public void showUpcomingMovies() {
        List<Movie> movieCollection = new ArrayList<>();
        try {
            movieCollection = new MovieDao().readAll();
        } catch (MovieException e) {
            e.printStackTrace();
        }
        for (Movie movie : movieCollection) {
            if (movie.getDateTime().after(Timestamp.valueOf(LocalDateTime.now()))) {
                upcomingMovies.add(movie);
            }
        }
        upcomingMovies.sort(new MovieDateTimeComporator());
        upcomingMovies.forEach(System.out::println);
    }

    public void showUserList(){
        List<String> usernames = new ArrayList<>();
        try {
            usernames = new UserDao().getUserNameList();
        } catch (UserException e) {
            throw new RuntimeException(e);
        }
        usernames.forEach(System.out::println);
    }

    public void buyATicketForUser(){
        System.out.println("введите логин пользователя...");
        String login = scanString();
        checkLoginInBase(login);

    }

    public void checkLoginInBase(String login){
        try {
            List<String> userLoginList = new UserDao().getUserNameList();
            if (!userLoginList.contains(login.toLowerCase())){
                System.out.println("Пользователь с таким логином не зарегистрирован! Операция прервана!");
                System.out.println("- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -");
                new ManagerMenu().showManagerMenu();
            }
        } catch (UserException e) {
            throw new RuntimeException(e);
        }
    }
}
