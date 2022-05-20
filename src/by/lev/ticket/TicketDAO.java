package by.lev.ticket;

import by.lev.Interfaces.CRUDoperation;
import by.lev.exceptions.TicketException;
import by.lev.user.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static by.lev.controller.AuthorizationFunctional.USER_MAIN;
import static by.lev.databaseConnection.AbstractConnection.*;
import static by.lev.exceptions.EnumTicketException.*;

public class TicketDAO implements CRUDoperation<Ticket, Integer> {
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
            throw new TicketException(TD_001);
        } finally {
            try {
                closeConnection();
            } catch (SQLException e) {
                throw new TicketException(TD_001f);
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
            throw new TicketException(TD_002);
        } finally {
            try {
                closeConnection();
            } catch (SQLException e) {
                throw new TicketException(TD_002f);
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
            throw new TicketException(TD_003);
        } finally {
            try {
                closeConnection();
            } catch (SQLException e) {
                throw new TicketException(TD_003f);
            }
        }
    }

    public List<Ticket> readAll(User user) throws TicketException {
        List<Ticket> userTickets = new ArrayList<>();
        try {
            Connection connection = getConnection();
            PreparedStatement prs = connection.prepareStatement("SELECT * FROM tickets WHERE userName=?");
            prs.setString(1, USER_MAIN.getLogin());
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
            throw new TicketException(TD_0031);
        } finally {
            try {
                closeConnection();
            } catch (SQLException e) {
                throw new TicketException(TD_0031f);
            }
        }
    }

    @Override
    public boolean update(Integer ticketID, Integer newPlace) throws TicketException {
        try {
            Connection connection = getConnection();
            PreparedStatement prs = connection.prepareStatement(
                    "UPDATE tickets SET place=? WHERE ticketID=?");
            prs.setInt(1, newPlace);
            prs.setInt(2, ticketID);
            prs.execute();
            return true;
        } catch (SQLException e) {
            throw new TicketException(TD_004);
        } finally {
            try {
                closeConnection();
            } catch (SQLException e) {
                throw new TicketException(TD_004f);
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
            throw new TicketException(TD_0041);
        } finally {
            try {
                closeConnection();
            } catch (SQLException e) {
                throw new TicketException(TD_0041f);
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
            throw new TicketException(TD_005);
        } finally {
            try {
                closeConnection();
            } catch (SQLException e) {
                throw new TicketException(TD_005f);
            }
        }
    }
}
