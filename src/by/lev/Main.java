package by.lev;
import by.lev.controller.MainMenu;
import by.lev.exceptions.MovieException;
import by.lev.exceptions.TicketException;
import by.lev.movie.MovieDao;

import java.util.List;

public class Main {
    public static void main(String[] args) throws MovieException, TicketException {
        new MainMenu().start();

    }
}
