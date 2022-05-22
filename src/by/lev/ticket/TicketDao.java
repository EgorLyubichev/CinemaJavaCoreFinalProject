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

import static by.lev.controller.Authorization.USER_ONLINE;
import static by.lev.databaseConnection.AbstractConnection.*;
import static by.lev.exceptions.EnumTicketException.*;
import static by.lev.databaseConnection.ScriptConstant.*;

public class TicketDao implements TicketDatabaseAction<Ticket, Integer, String> {
    @Override
    public boolean create(Ticket ticket) throws TicketException {
        try {
            Connection connection = getConnection();
            PreparedStatement prs = connection.prepareStatement(
                    "INSERT INTO tickets (userName, movieID, place, cost) VALUES (?,?,?,?)"
            );
            prs.setString(1, ticket.getUserName());
            prs.setInt(2, ticket.getMovieID());
            prs.setInt(3, ticket.getPlace());
            prs.setInt(4, ticket.getCost());
            prs.execute();
            return true;
        } catch (SQLException e) {
            throw new TicketException(TD_01);
        } finally {
            try {
                closeConnection();
            } catch (SQLException e) {
                throw new TicketException(TD_01f);
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
            PreparedStatement prs = connection.prepareStatement(
                    "SELECT userName, movieID, place, cost FROM tickets WHERE ticketID=?");
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
            throw new TicketException(TD_02);
        } finally {
            try {
                closeConnection();
            } catch (SQLException e) {
                throw new TicketException(TD_02f);
            }
        }
    }

    @Override
    public List<Ticket> readAll() throws TicketException {
        List<Ticket> tickets = new ArrayList<>();
        try {
            Connection connection = getConnection();
            PreparedStatement prs = connection.prepareStatement("SELECT * FROM tickets");
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
            throw new TicketException(TD_03);
        } finally {
            try {
                closeConnection();
            } catch (SQLException e) {
                throw new TicketException(TD_03f);
            }
        }
    }


    public boolean update(Integer integer, String s) throws TicketException {
        return false;
    }

    public List<Ticket> readAll(User user) throws TicketException {
        List<Ticket> userTickets = new ArrayList<>();
        try {
            Connection connection = getConnection();
            PreparedStatement prs = connection.prepareStatement("SELECT * FROM tickets WHERE userName=?");
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
            throw new TicketException(TD_031);
        } finally {
            try {
                closeConnection();
            } catch (SQLException e) {
                throw new TicketException(TD_031f);
            }
        }
    }

    public List<Integer> getTicketNumbersOfTheUser(User user) throws TicketException {
        List<Integer> ticketNumbersList = new ArrayList<>();
        try {
            Connection connection = getConnection();
            PreparedStatement prs = connection.prepareStatement(
                    GET_TICKETS_ID_FROM_TICKETS_BY_THE_USERNAME_REQUEST.getScript());
            prs.setString(1, USER_ONLINE.getLogin());
            ResultSet rs = prs.executeQuery();
            while (rs.next()) {
                int ticketID = rs.getInt("ticketID");
                ticketNumbersList.add(ticketID);
            }
        } catch (SQLException e) {
            throw new TicketException(TD_031);//исправить!!!
        } finally {
            try {
                closeConnection();
            } catch (SQLException e) {
                throw new TicketException(TD_031f);//исправить!!!
            }
        }
        return ticketNumbersList;
    }



    public boolean update(Integer ticketID, Object newPlace) throws TicketException {
        try {
            Connection connection = getConnection();
            PreparedStatement prs = connection.prepareStatement(
                    "UPDATE tickets SET place=? WHERE ticketID=?");
            prs.setInt(1, (Integer) newPlace);
            prs.setInt(2, ticketID);
            prs.execute();
            return true;
        } catch (SQLException e) {
            throw new TicketException(TD_04);
        } finally {
            try {
                closeConnection();
            } catch (SQLException e) {
                throw new TicketException(TD_04f);
            }
        }
    }

    public boolean update(int ticketID) throws TicketException {
        try {
            Connection connection = getConnection();
            PreparedStatement prs = connection.prepareStatement(
                    UPDATE_THE_USERNAME_FIELD_INTO_THE_TICKET.getScript());
            prs.setString(1, null);
            prs.setInt(2, ticketID);
            prs.execute();
            return true;
        } catch (SQLException e) {
            throw new TicketException(TD_05);
        } finally {
            try {
                closeConnection();
            } catch (SQLException e) {
                throw new TicketException(TD_05f);
            }
        }
    }

    public boolean update(Integer ticketID, User user) throws TicketException {
        try {
            Connection connection = getConnection();
            PreparedStatement prs = connection.prepareStatement(
                    "UPDATE tickets SET userName=? WHERE ticketID=?");
            prs.setString(1, user.getLogin());
            prs.setInt(2, ticketID);
            prs.execute();
            return true;
        } catch (SQLException e) {
            throw new TicketException(TD_042);
        } finally {
            try {
                closeConnection();
            } catch (SQLException e) {
                throw new TicketException(TD_042f);
            }
        }
    }

    @Override
    public boolean delete(Integer ticketID) throws TicketException {
        try {
            Connection connection = getConnection();
            PreparedStatement prs = connection.prepareStatement(
                    "DELETE FROM tickets WHERE ticketID=?");
            prs.setInt(1, ticketID);
            prs.execute();
            return true;
        } catch (SQLException e) {
            throw new TicketException(TD_05);
        } finally {
            try {
                closeConnection();
            } catch (SQLException e) {
                throw new TicketException(TD_05f);
            }
        }
    }

    public boolean delete(Movie movie) throws TicketException {
        try {
            Connection connection = getConnection();
            PreparedStatement prs = connection.prepareStatement(
                    "DELETE FROM tickets WHERE movieID=?");
            prs.setInt(1, movie.getMovieID());
            prs.execute();
            return true;
        } catch (SQLException e) {
            throw new TicketException(TD_05);
        } finally {
            try {
                closeConnection();
            } catch (SQLException e) {
                throw new TicketException(TD_05f);
            }
        }
    }

    @Override
    public List<Ticket> readTicketListFromTicketsByTheMovieIdRequest(int movieID) throws TicketException {
        List<Ticket> ticketList = new ArrayList<>();
        try {
            Connection connection = getConnection();
            PreparedStatement prs = connection.prepareStatement(
                    GET_ALL_FIELDS_FROM_TICKETS_BY_THE_MOVIEID_REQUEST.getScript()
            );
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
            throw new TicketException(TD_06);
        } finally {
            try {
                closeConnection();
            } catch (SQLException e) {
                throw new TicketException(TD_06f);
            }
        }
        return ticketList;
    }
}
