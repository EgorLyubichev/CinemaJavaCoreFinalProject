package by.lev;
import by.lev.controller.Menu;
import by.lev.exceptions.MovieException;
import by.lev.exceptions.TicketException;
import by.lev.movie.Movie;
import by.lev.ticket.Ticket;
import by.lev.ticket.TicketDao;

public class Main {
    public static void main(String[] args) throws MovieException, TicketException {
        new Menu().start();







    }
}
