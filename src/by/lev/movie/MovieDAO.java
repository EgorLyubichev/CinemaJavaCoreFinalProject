package by.lev.movie;

import by.lev.Interfaces.MovieCRUDoperation;
import by.lev.exceptions.MovieException;
import by.lev.ticket.Ticket;

import static by.lev.databaseConnection.AbstractConnection.*;
import static by.lev.exceptions.EnumMovieException.*;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;


public class MovieDAO implements MovieCRUDoperation<Movie, Integer> {
    @Override
    public boolean create(Movie movie) throws MovieException {
        try {
            Connection connection = getConnection();
            PreparedStatement prs = connection.prepareStatement(
                    "INSERT INTO movie (movieTitle, dateTime) VALUES (?,?)");
            prs.setString(1, movie.getTitle());
            prs.setTimestamp(2, movie.getDateTime());
            prs.execute();
            return true;
        } catch (SQLException e) {
            throw new MovieException(MD_001);
        } finally {
            try {
                closeConnection();
            } catch (
                    SQLException e) {
                throw new MovieException(MD_001f);
            }
        }
    }

    @Override
    public Movie read(Integer movieID) throws MovieException {
        Movie movie = new Movie();
        try {
            Connection connection = getConnection();
            PreparedStatement prs = connection.prepareStatement(
                    "SELECT * FROM movie WHERE movieID=?");
            prs.setInt(1, movieID);
            ResultSet rs = prs.executeQuery();
            while (rs.next()) {
                movie.setMovieID(rs.getInt("movieID"));
                movie.setTitle(rs.getString("movieTitle"));
                movie.setDateTime(rs.getTimestamp("dateTime"));
                movie.setTicketList(getMovieTickets(connection, movieID));
            }
            return movie;
        } catch (SQLException e) {
            throw new MovieException(MD_002);
        } finally {
            try {
                closeConnection();
            } catch (SQLException e) {
                throw new MovieException(MD_002f);
            }
        }
    }

    public Movie read(String title) throws MovieException {
        Movie movie = new Movie();
        try {
            Connection connection = getConnection();
            PreparedStatement prs = connection.prepareStatement(
                    "SELECT * FROM movie WHERE movieTitle=?");
            prs.setString(1, title);
            ResultSet rs = prs.executeQuery();
            while (rs.next()) {
                movie.setMovieID(rs.getInt("movieID"));
                movie.setTitle(rs.getString("movieTitle"));
                movie.setDateTime(rs.getTimestamp("dateTime"));
                movie.setTicketList(getMovieTickets(connection, movie.getMovieID()));
            }
            return movie;
        } catch (SQLException e) {
            throw new MovieException(MD_002);
        } finally {
            try {
                closeConnection();
            } catch (SQLException e) {
                throw new MovieException(MD_002f);
            }
        }
    }

    @Override
    public List<Movie> readAll() throws MovieException {
        List<Movie> movies = new ArrayList<>();
        try {
            Connection connection = getConnection();
            PreparedStatement prs = connection.prepareStatement("SELECT * FROM movie");
            final ResultSet rs = prs.executeQuery();
            while (rs.next()) {
                Movie movie = new Movie();
                movie.setMovieID(rs.getInt("movieID"));
                movie.setTitle(rs.getString("movieTitle"));
                movie.setDateTime(rs.getTimestamp("dateTime"));
                movie.setTicketList(getMovieTickets(connection, movie.getMovieID()));
                movies.add(movie);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            //throw new MovieException(MD_003);
        } finally {
            try {
                closeConnection();
            } catch (SQLException e) {
                throw new MovieException(MD_003f);
            }
        }
        return movies;
    }

//    private List<Ticket> getTicketListOfTheMovie(int movieID) throws TicketException {
//        TicketDAO td = new TicketDAO();
//        List<Ticket> tickets = td.readAll();
//        List<Ticket> filterTickets = new ArrayList<>();
//        for (Ticket ticket : tickets) {
//            if (ticket.getMovieID() == movieID) {
//                filterTickets.add(ticket);
//            }
//
//        }
//        return filterTickets;
//    }


    @Override
    public boolean update(Movie oldMovie, Movie newMovie) throws MovieException {
        try {
            Connection connection = getConnection();
            PreparedStatement prs = connection.prepareStatement(
                    "UPDATE movie SET dateTime=? WHERE movieID=?");
            prs.setTimestamp(1, newMovie.getDateTime());
            prs.setInt(2, oldMovie.getMovieID());
            prs.execute();
            return true;
        } catch (SQLException e) {
            throw new MovieException(MD_004);
        } finally {
            try {
                closeConnection();
            } catch (SQLException e) {
                throw new MovieException(MD_004f);
            }
        }
    }

    @Override
    public boolean delete(Integer movieID) throws MovieException {
        try {
            Connection connection = getConnection();
            PreparedStatement prs = connection.prepareStatement("DELETE FROM movie WHERE movieID=?");
            prs.setInt(1, movieID);
            prs.execute();
            return true;
        } catch (SQLException e) {
            throw new MovieException(MD_005);
        } finally {
            try {
                closeConnection();
            } catch (SQLException e) {
                throw new MovieException(MD_005f);
            }
        }
    }

    public List<Ticket> getMovieTickets(Connection connection, int movieID) {
        List<Ticket> tickets = new ArrayList<>();
        try {

            PreparedStatement prs = connection.prepareStatement("SELECT * FROM tickets WHERE movieID=?");
            prs.setInt(1, movieID);
            ResultSet rs = prs.executeQuery();
            while (rs.next()) {
                Ticket ticket = new Ticket();
                ticket.setTicketID(rs.getInt("ticketID"));
                ticket.setUserName(rs.getString("userName"));
                ticket.setMovieID(rs.getInt("movieID"));
                ticket.setPlace(rs.getInt("place"));
                ticket.setCost(rs.getInt("cost"));
                tickets.add(ticket);
            }
        } catch (SQLException e) {
            e.getErrorCode();
        }
        return tickets;
    }
}
