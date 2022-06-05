package by.lev.service;

import by.lev.exceptions.TicketException;
import by.lev.movie.Movie;
import by.lev.ticket.Ticket;
import by.lev.ticket.TicketDaoInterface;
import by.lev.user.User;
import java.util.ArrayList;
import java.util.List;


public class TicketService implements TicketServiceInterface {
    TicketDaoInterface<Ticket, Integer> tickDao;

    public TicketService(TicketDaoInterface<Ticket, Integer> tickDao) {
        this.tickDao = tickDao;
    }

    @Override
    public boolean addTicket(Ticket ticket) {
        try {
            tickDao.create(ticket);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public List<Ticket> getFreeTicketsOfTheSession(int movieID) {
        List<Ticket> freeTickets = new ArrayList<>();
        try {
            freeTickets = tickDao.readFreeTicketsOfTheMovie(movieID);
        } catch (TicketException e) {
            e.printStackTrace();
        }
        return freeTickets;
    }

    @Override
    public Ticket getTicket(int ticketID) {
        Ticket ticket = new Ticket();
        try {
            ticket = tickDao.read(ticketID);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ticket;
    }

    @Override
    public void assignTheUserInTheTicket(int ticketID, User user) {
        try {
            tickDao.setUsernameInTicket(ticketID, user);
        } catch (TicketException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Ticket> getUserTickets(User user) {
        List<Ticket> userTickets = new ArrayList<>();
        try {
            userTickets = tickDao.readTicketListOfTheUser(user);
        } catch (TicketException e) {
            e.printStackTrace();
        }
        return userTickets;
    }

    @Override
    public List<Integer> getTicketNumbersOfUser(User user) {
        List<Integer> ticketNumbersOfUser = new ArrayList<>();
        try {
            ticketNumbersOfUser = tickDao.readTicketNumbersOfTheUser(user);
        } catch (TicketException e) {
            e.printStackTrace();
        }
        return ticketNumbersOfUser;
    }

    @Override
    public boolean removeTicket(Movie movie) {
        try {
            tickDao.deleteByMovieId(movie);
            return true;
        } catch (TicketException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public void removeUsernameFromTicket(int ticketID) {
        try {
            tickDao.deleteUsernameFromTicket(ticketID);
        } catch (TicketException e) {
            e.printStackTrace();
        }
    }
}
