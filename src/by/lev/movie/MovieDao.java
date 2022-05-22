package by.lev.movie;

import by.lev.exceptions.MovieException;
import by.lev.ticket.Ticket;

import static by.lev.databaseConnection.ScriptConstant.*;
import static by.lev.databaseConnection.AbstractConnection.*;
import static by.lev.exceptions.EnumMovieException.*;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


public class MovieDao implements MovieDatabaseAction<Movie, Integer, String> {
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
            throw new MovieException(MD_001, e.getMessage(), new Throwable().getCause());
        } finally {
            try {
                closeConnection();
            } catch (
                    SQLException e) {
                throw new MovieException(MD_001f, e.getMessage(), new Throwable().getCause());
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
            throw new MovieException(MD_002, e.getMessage(), new Throwable().getCause());
        } finally {
            try {
                closeConnection();
            } catch (SQLException e) {
                throw new MovieException(MD_002f, e.getMessage(), new Throwable().getCause());
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
            throw new MovieException(MD_002, e.getMessage(), new Throwable().getCause());
        } finally {
            try {
                closeConnection();
            } catch (SQLException e) {
                throw new MovieException(MD_002f, e.getMessage(), new Throwable().getCause());
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
            throw new MovieException(MD_003, e.getMessage(), new Throwable().getCause());
        } finally {
            try {
                closeConnection();
            } catch (SQLException e) {
                throw new MovieException(MD_003f, e.getMessage(), new Throwable().getCause());
            }
        }
        return movies;
    }

    public boolean update(Integer movieID, String movieTitle) throws MovieException {
        try {
            Connection connection = getConnection();
            PreparedStatement prs = connection.prepareStatement(
                    "UPDATE movie SET movieTitle=? WHERE movieID=?");
            prs.setString(1, movieTitle);
            prs.setInt(2, movieID);
            prs.execute();
            return true;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            try {
                closeConnection();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }


    public boolean update(int movieID, Timestamp newTimestamp) throws MovieException {
        try {
            Connection connection = getConnection();
            PreparedStatement prs = connection.prepareStatement(
                    "UPDATE movie SET dateTime=? WHERE movieID=?");
            prs.setTimestamp(1, newTimestamp);
            prs.setInt(2, movieID);
            prs.execute();
            return true;
        } catch (SQLException e) {
            throw new MovieException(MD_004, e.getMessage(), new Throwable().getCause());
        } finally {
            try {
                closeConnection();
            } catch (SQLException e) {
                throw new MovieException(MD_004f, e.getMessage(), new Throwable().getCause());
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
            throw new MovieException(MD_005, e.getMessage(), new Throwable().getCause());
        } finally {
            try {
                closeConnection();
            } catch (SQLException e) {
                throw new MovieException(MD_005f, e.getMessage(), new Throwable().getCause());
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

    @Override
    public List<Timestamp> getTheDateTimeOnRequestMovieTitle(String title) {
        List<Timestamp> movieSessions = new ArrayList<>();
        try {
            Connection connection = getConnection();
            PreparedStatement prs = connection.prepareStatement(SELECT_DATE_TIME_FROM_THE_MOVIE_WHERE_TITLE.getScript());
            prs.setString(1, title.toUpperCase());
            ResultSet rs = prs.executeQuery();
            while (rs.next()) {
                Timestamp timestamp = rs.getTimestamp("dateTime");
                if (timestamp.after(Timestamp.valueOf(LocalDateTime.now()))) {
                    movieSessions.add(timestamp);
                }
            }
        } catch (SQLException e) {
            new MovieException(MD_006, e.getMessage(), new Throwable().getCause());
        } finally {
            try {
                closeConnection();
            } catch (SQLException e) {
                new MovieException(MD_006f, e.getMessage(), new Throwable().getCause());
            }
        }
        return movieSessions;
    }

    public int getMovieIdOnTheDateTimeRequest(Timestamp dateTime) throws MovieException {
        Connection connection = getConnection();
        PreparedStatement prs1 = null;
        try {
            prs1 = connection.prepareStatement("SELECT movieID FROM movie WHERE dateTime=?");
            prs1.setTimestamp(1, dateTime);
            ResultSet rs1 = prs1.executeQuery();
            if (rs1.next()) {
                return rs1.getInt("movieID");
            } else {
                return -1;
            }
        } catch (SQLException e) {
            throw new MovieException(MD_007, e.getMessage(), new Throwable().getCause());
        } finally {
            try {
                closeConnection();
            } catch (SQLException e) {
                throw new MovieException(MD_007f, e.getMessage(), new Throwable().getCause());
            }
        }

    }
}
