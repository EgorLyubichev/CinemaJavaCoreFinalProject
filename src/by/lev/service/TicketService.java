package by.lev.service;

import by.lev.exceptions.TicketException;
import by.lev.movie.Movie;
import by.lev.ticket.Ticket;
import by.lev.ticket.TicketDao;
import by.lev.user.User;
import java.util.ArrayList;
import java.util.List;


public class TicketService implements TicketServiceInterface {
    @Override
    public boolean addTicket(Ticket ticket) {
        try {
            new TicketDao().create(ticket);
            return true;
        } catch (TicketException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public List<Ticket> getFreeTicketsOfTheSession(int movieID) {
        List<Ticket> freeTickets = new ArrayList<>();
        try {
            freeTickets = new TicketDao().readFreeTicketsOfTheMovie(movieID);
        } catch (TicketException e) {
            e.printStackTrace();
        }
        return freeTickets;
    }

    @Override
    public Ticket getTicket(int ticketID) {
        Ticket ticket = new Ticket();
        try {
            ticket = new TicketDao().read(ticketID);
        } catch (TicketException e) {
            e.printStackTrace();
        }
        return ticket;
    }

    @Override
    public void assignTheUserInTheTicket(int ticketID, User user) {
        try {
            new TicketDao().setUsernameInTicket(ticketID, user);
        } catch (TicketException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Ticket> getUserTickets(User user) {
        List<Ticket> userTickets = new ArrayList<>();
        try {
            userTickets = new TicketDao().readTicketListOfTheUser(user);
        } catch (TicketException e) {
            e.printStackTrace();
        }
        return userTickets;
    }

    @Override
    public List<Integer> getTicketNumbersOfUser(User user) {
        List<Integer> ticketNumbersOfUser = new ArrayList<>();
        try {
            ticketNumbersOfUser = new TicketDao().readTicketNumbersOfTheUser(user);
        } catch (TicketException e) {
            e.printStackTrace();
        }
        return ticketNumbersOfUser;
    }

    @Override
    public boolean removeTicket(Movie movie) {
        try {
            new TicketDao().deleteByMovieId(movie);
            return true;
        } catch (TicketException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public void removeUsernameFromTicket(int ticketID) {
        try {
            new TicketDao().deleteUsernameFromTicket(ticketID);
        } catch (TicketException e) {
            e.printStackTrace();
        }
    }
}
