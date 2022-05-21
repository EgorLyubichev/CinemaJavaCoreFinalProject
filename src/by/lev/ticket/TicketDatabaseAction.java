package by.lev.ticket;

import by.lev.exceptions.TicketException;
import by.lev.interfaces.CrudOperation;
import by.lev.user.User;

import java.util.List;

public interface TicketDatabaseAction<T, R>  extends CrudOperation<Ticket, Integer> {
    List<Ticket> readTicketListFromTicketsByTheMovieIdRequest(int movieID) throws TicketException;
    boolean update(int ticketID) throws TicketException;
    boolean update(Integer ticketID, User user) throws TicketException;
}
