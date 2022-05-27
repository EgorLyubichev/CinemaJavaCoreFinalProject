package by.lev.ticket;

import by.lev.exceptions.TicketException;
import by.lev.movie.Movie;
import by.lev.user.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static by.lev.Constant.*;
import static by.lev.databaseConnection.AbstractConnection.*;
import static by.lev.exceptions.EnumTicketException.*;

public class TicketDao implements TicketDaoInterface<Ticket, Integer> {
    @Override
    public boolean create(Ticket ticket) throws TicketException {
        try {
            Connection connection = getConnection();
            PreparedStatement prs = connection.prepareStatement(TICKET_CREATE.getMessage());
            prs.setString(1, ticket.getUserName());
            prs.setInt(2, ticket.getMovieID());
            prs.setInt(3, ticket.getPlace());
            prs.setInt(4, ticket.getCost());
            prs.execute();
            return true;
        } catch (SQLException e) {
            throw new TicketException(TD_01, TD_01.getMessage(), e);
        } finally {
            try {
                closeConnection();
            } catch (SQLException e) {
                throw new TicketException(TD_01f, TD_01f.getMessage(), e);
            }
        }
    }

    @Override
    public Ticket read(Integer ticketID) throws TicketException {
        String userName = null;
        int movieID = 0;
        int place = 0;
        int cost = 0;
        try {
            Connection connection = getConnection();
            PreparedStatement prs = connection.prepareStatement(TICKET_READ.getMessage());
            prs.setInt(1, ticketID);
            ResultSet rs = prs.executeQuery();
            while (rs.next()) {
                userName = rs.getString("userName");
                movieID = rs.getInt("movieID");
                place = rs.getInt("place");
                cost = rs.getInt("cost");
            }
            Ticket ticket = new Ticket(ticketID, userName, movieID, place, cost);
            return ticket;
        } catch (SQLException e) {
            throw new TicketException(TD_02, TD_02.getMessage(), e);
        } finally {
            try {
                closeConnection();
            } catch (SQLException e) {
                throw new TicketException(TD_02f, TD_02f.getMessage(), e);
            }
        }
    }

    @Override
    public List<Ticket> readAll() throws TicketException {
        List<Ticket> tickets = new ArrayList<>();
        try {
            Connection connection = getConnection();
            PreparedStatement prs = connection.prepareStatement(TICKET_READ_ALL.getMessage());
            final ResultSet rs = prs.executeQuery();
            while (rs.next()) {
                int ticketID = rs.getInt("ticketID");
                String userName = rs.getString("userName");
                int movieID = rs.getInt("movieID");
                int place = rs.getInt("place");
                int cost = rs.getInt("cost");
                Ticket ticket = new Ticket(ticketID, userName, movieID, place, cost);
                tickets.add(ticket);
            }
            return tickets;
        } catch (SQLException e) {
            throw new TicketException(TD_03, TD_03.getMessage(), e);
        } finally {
            try {
                closeConnection();
            } catch (SQLException e) {
                throw new TicketException(TD_03f, TD_03f.getMessage(), e);
            }
        }
    }

    @Override
    public List<Ticket> readTicketListOfTheUser(User user) throws TicketException {
        List<Ticket> userTickets = new ArrayList<>();
        try {
            Connection connection = getConnection();
            PreparedStatement prs = connection.prepareStatement(TICKET_READ_TICKETS_OF_THE_USER.getMessage());
            prs.setString(1, user.getLogin());
            ResultSet rs = prs.executeQuery();
            while (rs.next()) {
                Ticket ticket = new Ticket();
                ticket.setTicketID(rs.getInt("ticketID"));
                ticket.setUserName(rs.getString("userName"));
                ticket.setMovieID(rs.getInt("movieID"));
                ticket.setPlace(rs.getInt("place"));
                ticket.setCost(rs.getInt("cost"));
                userTickets.add(ticket);
            }
            return userTickets;
        } catch (SQLException e) {
            throw new TicketException(TD_031, TD_031.getMessage(), e);
        } finally {
            try {
                closeConnection();
            } catch (SQLException e) {
                throw new TicketException(TD_031f, TD_031f.getMessage(), e);
            }
        }
    }

    @Override
    public List<Integer> readTicketNumbersOfTheUser(User user) throws TicketException {
        List<Integer> ticketNumbersList = new ArrayList<>();
        try {
            Connection connection = getConnection();
            PreparedStatement prs = connection.prepareStatement(TICKET_READ_TICKETS_ID_OF_THE_USER.getMessage());
            prs.setString(1, user.getLogin());
            ResultSet rs = prs.executeQuery();
            while (rs.next()) {
                int ticketID = rs.getInt("ticketID");
                ticketNumbersList.add(ticketID);
            }
        } catch (SQLException e) {
            throw new TicketException(TD_031, TD_031.getMessage(), e);
        } finally {
            try {
                closeConnection();
            } catch (SQLException e) {
                throw new TicketException(TD_031f, TD_031f.getMessage(), e);
            }
        }
        return ticketNumbersList;
    }

    @Override
    public boolean deleteUsernameFromTicket(int ticketID) throws TicketException {
        try {
            Connection connection = getConnection();
            PreparedStatement prs = connection.prepareStatement(
                    TICKET_REMOVE_USERNAME_FROM_THE_TICKET.getMessage());
            prs.setString(1, null);
            prs.setInt(2, ticketID);
            prs.execute();
            return true;
        } catch (SQLException e) {
            throw new TicketException(TD_05, TD_05.getMessage(), e);
        } finally {
            try {
                closeConnection();
            } catch (SQLException e) {
                throw new TicketException(TD_05f, TD_05f.getMessage(), e);
            }
        }
    }

    @Override
    public boolean setUsernameInTicket(Integer ticketID, User user) throws TicketException {
        try {
            Connection connection = getConnection();
            PreparedStatement prs = connection.prepareStatement(
                    TICKET_SET_THE_USERNAME_IN_THE_TICKET.getMessage());
            prs.setString(1, user.getLogin());
            prs.setInt(2, ticketID);
            prs.execute();
            return true;
        } catch (SQLException e) {
            throw new TicketException(TD_042, TD_042.getMessage(), e);
        } finally {
            try {
                closeConnection();
            } catch (SQLException e) {
                throw new TicketException(TD_042f, TD_042f.getMessage(), e);
            }
        }
    }

    @Override
    public boolean delete(Integer ticketID) throws TicketException {
        try {
            Connection connection = getConnection();
            PreparedStatement prs = connection.prepareStatement(TICKET_DELETE_BY_TICKET_ID.getMessage());
            prs.setInt(1, ticketID);
            prs.execute();
            return true;
        } catch (SQLException e) {
            throw new TicketException(TD_05, TD_05.getMessage(), e);
        } finally {
            try {
                closeConnection();
            } catch (SQLException e) {
                throw new TicketException(TD_05f, TD_05f.getMessage(), e);
            }
        }
    }

    @Override
    public boolean deleteByMovieId(Movie movie) throws TicketException {
        try {
            Connection connection = getConnection();
            PreparedStatement prs = connection.prepareStatement(
                    TICKET_DELETE_BY_MOVIE_ID.getMessage());
            prs.setInt(1, movie.getMovieID());
            prs.execute();
            return true;
        } catch (SQLException e) {
            throw new TicketException(TD_05, TD_05.getMessage(), e);
        } finally {
            try {
                closeConnection();
            } catch (SQLException e) {
                throw new TicketException(TD_05f, TD_05f.getMessage(), e);
            }
        }
    }

    @Override
    public List<Ticket> readTicketListFromTicketsByTheMovieIdRequest(int movieID) throws TicketException {
        List<Ticket> ticketList = new ArrayList<>();
        try {
            Connection connection = getConnection();
            PreparedStatement prs = connection.prepareStatement(READ_TICKETS_OF_THE_MOVIE.getMessage());
            prs.setInt(1, movieID);
            ResultSet rs = prs.executeQuery();
            while (rs.next()) {
                Ticket ticket = new Ticket();
                ticket.setTicketID(rs.getInt("ticketID"));
                ticket.setUserName(rs.getString("userName"));
                ticket.setPlace(rs.getInt("place"));
                ticket.setCost(rs.getInt("cost"));
                if (ticket.getUserName() == null) {
                    ticketList.add(ticket);
                }
            }
        } catch (SQLException e) {
            throw new TicketException(TD_06, TD_06.getMessage(), e);
        } finally {
            try {
                closeConnection();
            } catch (SQLException e) {
                throw new TicketException(TD_06f, TD_06f.getMessage(), e);
            }
        }
        return ticketList;
    }
}
