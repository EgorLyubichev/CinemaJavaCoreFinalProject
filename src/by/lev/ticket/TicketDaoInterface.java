package by.lev.ticket;

import by.lev.exceptions.TicketException;
import by.lev.interfaces.DaoOperation;
import by.lev.movie.Movie;
import by.lev.user.User;

import java.util.List;

public interface TicketDaoInterface<T, R>  extends DaoOperation<Ticket, Integer> {
    List<Ticket> readTicketListFromTicketsByTheMovieIdRequest(int movieID) throws TicketException;
    List<Ticket> readTicketListOfTheUser(User user) throws TicketException;
    List<Integer> readTicketNumbersOfTheUser(User user) throws TicketException;
    boolean deleteUsernameFromTicket(int ticketID) throws TicketException;
    boolean setUsernameInTicket(Integer ticketID, User user) throws TicketException;
    boolean deleteByMovieId(Movie movie) throws TicketException;
}
