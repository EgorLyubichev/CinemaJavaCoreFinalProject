package by.lev.movie;

import by.lev.databaseConnection.AbstractConnection;
import by.lev.exceptions.MovieException;
import by.lev.ticket.TicketDao;

import static by.lev.Constant.*;
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
            PreparedStatement prs = connection.prepareStatement(MOVIE_CREATE.getMessage());
            prs.setString(1, movie.getTitle());
            prs.setTimestamp(2, movie.getDateTime());
            prs.execute();
            return true;
        } catch (SQLException e) {
            throw new MovieException(MD_01, MD_01.getMessage(), e);
        } finally {
            try {
                closeConnection();
            } catch (
                    SQLException e) {
                throw new MovieException(MD_01F, MD_01F.getMessage(), e);
            }
        }
    }

    @Override
    public Movie read(Integer movieID) throws MovieException {
        Movie movie = new Movie();
        try {
            Connection connection = getConnection();
            PreparedStatement prs = connection.prepareStatement(MOVIE_READ_BY_MOVIE_ID.getMessage());
            prs.setInt(1, movieID);
            ResultSet rs = prs.executeQuery();
            while (rs.next()) {
                movie.setMovieID(rs.getInt("movieID"));
                movie.setTitle(rs.getString("movieTitle"));
                movie.setDateTime(rs.getTimestamp("dateTime"));
                movie.setTicketList(new TicketDao().readAllTicketsOfTheMovie(connection, movieID));
            }
            return movie;
        } catch (SQLException e) {
            throw new MovieException(MD_02, MD_02.getMessage(), e);
        } finally {
            try {
                closeConnection();
            } catch (SQLException e) {
                throw new MovieException(MD_02F, MD_02F.getMessage(), e);
            }
        }
    }

    @Override
    public Movie read(String title) throws MovieException {
        Movie movie = new Movie();
        try {
            Connection connection = getConnection();
            PreparedStatement prs = connection.prepareStatement(MOVIE_READ_BY_TITLE.getMessage());
            prs.setString(1, title);
            ResultSet rs = prs.executeQuery();
            while (rs.next()) {
                movie.setMovieID(rs.getInt("movieID"));
                movie.setTitle(rs.getString("movieTitle"));
                movie.setDateTime(rs.getTimestamp("dateTime"));
                movie.setTicketList(new TicketDao().readAllTicketsOfTheMovie(connection, movie.getMovieID()));
            }
            return movie;
        } catch (SQLException e) {
            throw new MovieException(MD_03, MD_03.getMessage(), e);
        } finally {
            try {
                closeConnection();
            } catch (SQLException e) {
                throw new MovieException(MD_03F, MD_03F.getMessage(), e);
            }
        }
    }

    @Override
    public List<Movie> readAll() throws MovieException {
        List<Movie> movies = new ArrayList<>();
        try {
            Connection connection = getConnection();
            PreparedStatement prs = connection.prepareStatement(MOVIE_READ_ALL.getMessage());
            final ResultSet rs = prs.executeQuery();
            while (rs.next()) {
                Movie movie = new Movie();
                movie.setMovieID(rs.getInt("movieID"));
                movie.setTitle(rs.getString("movieTitle"));
                movie.setDateTime(rs.getTimestamp("dateTime"));
                movie.setTicketList(new TicketDao().readAllTicketsOfTheMovie(connection, movie.getMovieID()));
                movies.add(movie);
            }
        } catch (SQLException e) {
            throw new MovieException(MD_04, MD_04.getMessage(), e);
        } finally {
            try {
                closeConnection();
            } catch (SQLException e) {
                throw new MovieException(MD_04F, MD_04F.getMessage(), e);
            }
        }
        return movies;
    }

    @Override
    public boolean update(Integer movieID, String movieTitle) throws MovieException {
        try {
            Connection connection = getConnection();
            PreparedStatement prs = connection.prepareStatement(MOVIE_UPDATE_TITLE.getMessage());
            prs.setString(1, movieTitle);
            prs.setInt(2, movieID);
            prs.execute();
            return true;
        } catch (SQLException e) {
            throw new MovieException(MD_05, MD_05.getMessage(), e);
        } finally {
            try {
                closeConnection();
            } catch (SQLException e) {
                throw new MovieException(MD_05F, MD_05F.getMessage(), e);
            }
        }
    }

    @Override
    public boolean update(int movieID, Timestamp newTimestamp) throws MovieException {
        try {
            Connection connection = getConnection();
            PreparedStatement prs = connection.prepareStatement(MOVIE_UPDATE_TIMESTAMP.getMessage());
            prs.setTimestamp(1, newTimestamp);
            prs.setInt(2, movieID);
            prs.execute();
            return true;
        } catch (SQLException e) {
            throw new MovieException(MD_06, MD_06.getMessage(), e);
        } finally {
            try {
                closeConnection();
            } catch (SQLException e) {
                throw new MovieException(MD_06F, MD_06F.getMessage(), e);
            }
        }
    }

    @Override
    public boolean delete(Integer movieID) throws MovieException {
        try {
            Connection connection = getConnection();
            PreparedStatement prs = connection.prepareStatement(MOVIE_DELETE.getMessage());
            prs.setInt(1, movieID);
            prs.execute();
            return true;
        } catch (SQLException e) {
            throw new MovieException(MD_07, MD_07.getMessage(), e);
        } finally {
            try {
                closeConnection();
            } catch (SQLException e) {
                throw new MovieException(MD_07F, MD_07F.getMessage(), e);
            }
        }
    }

    @Override
    public List<Timestamp> readTimestampsOfTheMovie(String title) throws MovieException {
        List<Timestamp> timestamps = new ArrayList<>();
        try {
            Connection connection = getConnection();
            PreparedStatement prs = connection.prepareStatement(
                    MOVIE_READ_TIMESTAMP_LIST_OF_THE_MOVIE.getMessage());
            prs.setString(1, title.toUpperCase());
            ResultSet rs = prs.executeQuery();
            while (rs.next()) {
                Timestamp timestamp = rs.getTimestamp("dateTime");
                timestamps.add(timestamp);
            }
        } catch (SQLException e) {
            throw new MovieException(MD_08, MD_08.getMessage(), e);
        } finally {
            try {
                closeConnection();
            } catch (SQLException e) {
                throw new MovieException(MD_08F, MD_08F.getMessage(), e);
            }
        }
        return timestamps;
    }

    @Override
    public List<String> readTitles() throws MovieException {
        List<String> titles = new ArrayList<>();
        try {
            Connection connection = getConnection();
            PreparedStatement prs = connection.prepareStatement(MOVIE_READ_TITLES.getMessage());
            ResultSet rs = prs.executeQuery();
            while (rs.next()) {
                String title = rs.getString("movieTitle");
                titles.add(title);
            }
            return titles;
        } catch (SQLException e) {
            throw new MovieException(MD_09, MD_09.getMessage(), e);
        } finally {
            try {
                closeConnection();
            } catch (SQLException e) {
                throw new MovieException(MD_09F, MD_09F.getMessage(), e);
            }
        }
    }

    @Override
    public int readMovieIdOnTheDateTimeRequest(Timestamp dateTime) throws MovieException {
        try {
            Connection connection = getConnection();
            PreparedStatement prs = connection.prepareStatement(MOVIE_READ_MOVIE_ID_WHERE_TIMESTAMP.getMessage());
            prs.setTimestamp(1, dateTime);
            ResultSet rs1 = prs.executeQuery();
            if (rs1.next()) {
                return rs1.getInt("movieID");
            } else {
                return -1;
            }
        } catch (SQLException e) {
            throw new MovieException(MD_10, MD_10.getMessage(), e);
        } finally {
            try {
                closeConnection();
            } catch (SQLException e) {
                throw new MovieException(MD_10F, MD_10F.getMessage(), e);
            }
        }
    }

    @Override
    public boolean isTheSlotOfThisDateTimeOccuped(String dateTime) throws MovieException {
        List<Timestamp> timestamps = new ArrayList<>();
        try {
            Connection connection = AbstractConnection.getConnection();
            PreparedStatement prs = connection.prepareStatement(MOVIE_READ_TIMESTAMP_WHERE_THE_TIMESTAMP.getMessage());
            prs.setTimestamp(1, Timestamp.valueOf(dateTime));
            ResultSet rs = prs.executeQuery();
            while (rs.next()) {
                timestamps.add(rs.getTimestamp("dateTime"));
            }
            if (timestamps.isEmpty()){return false;}
        } catch (SQLException e) {
            throw new MovieException(MD_11, MD_11.getMessage(), e);
        } finally {
            try {
                closeConnection();
            } catch (SQLException e) {
                throw new MovieException(MD_11F, MD_11F.getMessage(), e);
            }
        }
        return true;
    }
}
