package by.lev.service.inputChecks;

import by.lev.databaseConnection.AbstractConnection;
import by.lev.exceptions.TicketException;
import by.lev.ticket.Ticket;
import by.lev.ticket.TicketDao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import static by.lev.exceptions.EnumTicketException.MSC_01;

public class TicketInputCheck {
    public boolean isTheTicketWithSuchMovieAndSuchPlace(Ticket ticket) {
        TicketDao td = new TicketDao();
        List<Ticket> tickets;
        try {
            tickets = td.readAll();
            for (Ticket ticket2 : tickets) {
                if (ticket2.getMovieID() == ticket.getMovieID() && ticket2.getPlace() == ticket.getPlace()) {
                    System.out.println("Such ticket has been already added!");
                    return true;
                }
            }
        } catch (TicketException e) {
            e.getMessage();
        }
        return false;
    }

    public boolean isSuchMovieIDinTheDatabase(Ticket ticket) throws TicketException {
        int movieIDFromDatabase = 0;
        try {
            Connection connection = AbstractConnection.getConnection();
            PreparedStatement prs = connection.prepareStatement("SELECT movieID FROM movie WHERE movieID=?");
            prs.setInt(1, ticket.getMovieID());
            ResultSet rs = prs.executeQuery();
            while (rs.next()) {
                movieIDFromDatabase = rs.getInt("movieID");
                if (movieIDFromDatabase != 0) {
                    return true;
                }
            }
        } catch (SQLException e) {
            throw new TicketException(MSC_01);
        } finally {
            try {
                AbstractConnection.closeConnection();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        System.out.println("There isn't the movie with such movie ID in the database!");
        return false;
    }

    public boolean checkTheCorrectnessInputValueOfThePlaceInTheTicket(Ticket ticket){
        int place = ticket.getPlace();
        if (place <= 0 || place > 10){
            System.out.println("The value of the place is not correct!");
            return false;
        }
        return true;
    }

    public boolean checkTheCorrectnessInputValueOfThePriceInTheTicket(Ticket ticket){
        int price = ticket.getPlace();
        if (price < 0){
            System.out.println("The price cannot be negative!");
            return false;
        }
        return true;
    }
}
