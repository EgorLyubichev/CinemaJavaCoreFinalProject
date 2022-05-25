package by.lev.service;

import by.lev.ticket.Ticket;
import by.lev.user.User;

import java.util.List;

public interface TicketServiceInterface {
    List<Ticket> getFreeTicketsOfTheSession(int movieID);
    Ticket getTicket(int ticketID);
    void assignTheUserInTheTicket(int ticketID, User user);
    List<Ticket> getUserTickets(User user);
    List<Integer> getTicketNumbersOfUser(User user);
    void removeUsernameFromTicket(int ticketID);
}
