package by.lev.movie;

import by.lev.databaseConnection.AbstractConnection;
import by.lev.exceptions.MovieException;
import by.lev.ticket.Ticket;

import static by.lev.databaseConnection.ScriptConstant.*;
import static by.lev.databaseConnection.AbstractConnection.*;
import static by.lev.exceptions.EnumMovieException.*;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;


public class MovieDao implements MovieDaoInterface<Movie, Integer> {
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
            throw new MovieException(MD_001, MD_001.getMessage(), e);
        } finally {
            try {
                closeConnection();
            } catch (
                    SQLException e) {
                throw new MovieException(MD_001f, MD_001f.getMessage(), e);
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
            throw new MovieException(MD_002, MD_002.getMessage(), e);
        } finally {
            try {
                closeConnection();
            } catch (SQLException e) {
                throw new MovieException(MD_002f, MD_002f.getMessage(), e);
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
            throw new MovieException(MD_002, MD_002.getMessage(), e);
        } finally {
            try {
                closeConnection();
            } catch (SQLException e) {
                throw new MovieException(MD_002f, MD_002f.getMessage(), e);
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
            throw new MovieException(MD_003, MD_003.getMessage(), e);
        } finally {
            try {
                closeConnection();
            } catch (SQLException e) {
                throw new MovieException(MD_003f, MD_003f.getMessage(), e);
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
            throw new MovieException(MD_004, MD_004.getMessage(), e);
        } finally {
            try {
                closeConnection();
            } catch (SQLException e) {
                throw new MovieException(MD_004f, MD_004f.getMessage(), e);
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
            throw new MovieException(MD_005, MD_005.getMessage(), e);
        } finally {
            try {
                closeConnection();
            } catch (SQLException e) {
                throw new MovieException(MD_005f, MD_005f.getMessage(), e);
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
                Ticket ticket = new Ticket(
                        rs.getInt("ticketID"),
                        rs.getString("userName"),
                        rs.getInt("movieID"),
                        rs.getInt("place"),
                        rs.getInt("cost")
                );
                tickets.add(ticket);
            }
        } catch (SQLException e) {
            e.getErrorCode();
        }
        return tickets;
    }

    @Override
    public List<Timestamp> readTimestampsOfTheMovie(String title) throws MovieException {
        List<Timestamp> timestamps = new ArrayList<>();
        try {
            Connection connection = getConnection();
            PreparedStatement prs = connection.prepareStatement(
                    SELECT_DATE_TIME_FROM_THE_MOVIE_WHERE_TITLE.getScript());
            prs.setString(1, title.toUpperCase());
            ResultSet rs = prs.executeQuery();
            while (rs.next()) {
                Timestamp timestamp = rs.getTimestamp("dateTime");
                timestamps.add(timestamp);
            }
        } catch (SQLException e) {
            throw new MovieException(MD_006, MD_006.getMessage(), e);
        } finally {
            try {
                closeConnection();
            } catch (SQLException e) {
                throw new MovieException(MD_006f, MD_006f.getMessage(), e);
            }
        }
        return timestamps;
    }

    @Override
    public List<String> readTitles() throws MovieException {
        List<String> titles = new ArrayList<>();
        try {
            Connection connection = getConnection();
            PreparedStatement prs = connection.prepareStatement(
                    SELECT_TITLES_OF_MOVIES.getScript());
            ResultSet rs = prs.executeQuery();
            while (rs.next()) {
                String title = rs.getString("movieTitle");
                titles.add(title);
            }
            return titles;
        } catch (SQLException e) {
            throw new MovieException(MD_008, MD_008.getMessage(), e);
        } finally {
            try {
                closeConnection();
            } catch (SQLException e) {
                throw new MovieException(MD_008F, MD_008F.getMessage(), e);
            }
        }
    }

    public int readMovieIdOnTheDateTimeRequest(Timestamp dateTime) throws MovieException {
        try {
            Connection connection = getConnection();
            PreparedStatement prs = connection.prepareStatement(
                    "SELECT movieID FROM movie WHERE dateTime=?");
            prs.setTimestamp(1, dateTime);
            ResultSet rs1 = prs.executeQuery();
            if (rs1.next()) {
                return rs1.getInt("movieID");
            } else {
                return -1;
            }
        } catch (SQLException e) {
            throw new MovieException(MD_007, MD_007.getMessage(), e);
        } finally {
            try {
                closeConnection();
            } catch (SQLException e) {
                throw new MovieException(MD_007f, MD_007f.getMessage(), e);
            }
        }
    }
    public boolean isTheSlotOfThisDateTimeOccuped(String dateTime) throws MovieException {
        List<Timestamp> timestamps = new ArrayList<>();
        try {
            Connection connection = AbstractConnection.getConnection();
            PreparedStatement prs = connection.prepareStatement("SELECT dateTime FROM movie WHERE dateTime=?");
            prs.setTimestamp(1, Timestamp.valueOf(dateTime));
            ResultSet rs = prs.executeQuery();
            while (rs.next()) {
                timestamps.add(rs.getTimestamp("dateTime"));
            }
            if (timestamps.isEmpty()){return false;}
        } catch (SQLException e) {
            throw new MovieException(MD_009, MD_009.getMessage(), e);
        } finally {
            try {
                closeConnection();
            } catch (SQLException e) {
                throw new MovieException(MD_009F, MD_009F.getMessage(), e);
            }
        }
        return true;
    }
}
