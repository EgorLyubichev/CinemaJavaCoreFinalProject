package by.lev.ticket;

import by.lev.exceptions.TicketException;
import by.lev.interfaces.DaoOperation;
import by.lev.user.User;

import java.util.List;

public interface TicketDaoInterface<T, R>  extends DaoOperation<Ticket, Integer> {
    List<Ticket> readTicketListFromTicketsByTheMovieIdRequest(int movieID) throws TicketException;
    boolean update(int ticketID) throws TicketException;
    boolean update(Integer ticketID, User user) throws TicketException;
}
