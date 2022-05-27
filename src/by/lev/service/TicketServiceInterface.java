package by.lev.service;

import by.lev.movie.Movie;
import by.lev.ticket.Ticket;
import by.lev.user.User;

import java.util.List;

public interface TicketServiceInterface {
    boolean addTicket(Ticket ticket);
    List<Ticket> getFreeTicketsOfTheSession(int movieID);
    Ticket getTicket(int ticketID);
    void assignTheUserInTheTicket(int ticketID, User user);
    List<Ticket> getUserTickets(User user);
    List<Integer> getTicketNumbersOfUser(User user);
    boolean removeTicket(Movie movie);
    void removeUsernameFromTicket(int ticketID);
}
