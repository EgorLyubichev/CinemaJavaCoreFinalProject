package by.lev;
import by.lev.controller.Entrance;
import by.lev.exceptions.MovieException;
import by.lev.exceptions.TicketException;

public class Main {
    public static void main(String[] args) throws MovieException, TicketException {
        new Entrance().start();



    }
}
