package by.lev.user;

import by.lev.exceptions.UserException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static by.lev.databaseConnection.AbstractConnection.*;
import static by.lev.exceptions.EnumUserException.*;

public class UserDao implements UserDatabaseAction<User, String> {
    @Override
    public boolean create(User user) throws UserException {
        try {
            Connection connection = getConnection();
            PreparedStatement prs = connection.prepareStatement(
                    "INSERT INTO users (login, password, level) VALUES (?,?,?)");
            prs.setString(1, user.getLogin());
            prs.setString(2, user.getPassword());
            prs.setString(3, user.getLevel().toString());
            prs.execute();
            return true;
        } catch (SQLException e) {
            throw new UserException(UD_001, UD_001.getMessage(), e);
        } finally {
            try {
                closeConnection();
            } catch (SQLException e) {
                throw new UserException(UD_001f, UD_001f.getMessage(), e);
            }
        }
    }

    @Override
    public User read(String userName) throws UserException {
        User user = new User();
        try {
            Connection connection = getConnection();
            PreparedStatement prs = connection.prepareStatement(
                    "SELECT userID, login, password, level FROM users WHERE login=?");
            prs.setString(1, userName);
            ResultSet rs = prs.executeQuery();
            while (rs.next()) {
                user.setUserID(rs.getInt("userID"));
                user.setLogin(rs.getString("login"));
                user.setPassword(rs.getString("password"));
                user.setLevel(rs.getString("level"));
            }
            return user;
        } catch (SQLException e) {
            throw new UserException(UD_002, UD_002.getMessage(), e);
        } finally {
            try {
                closeConnection();
            } catch (SQLException e) {
                throw new UserException(UD_002f, UD_002f.getMessage(), e);
            }
        }
    }

    @Override
    public List<User> readAll() throws UserException {
        List<User> users = new ArrayList<>();
        try {
            Connection connection = getConnection();
            PreparedStatement prs = connection.prepareStatement("SELECT * FROM users");
            final ResultSet rs = prs.executeQuery();
            while (rs.next()) {
                User user = new User();
                user.setUserID(rs.getInt("userID"));
                user.setLogin(rs.getString("login"));
                user.setPassword(rs.getString("password"));
                user.setLevel(rs.getString("level"));
                users.add(user);
            }
            return users;
        } catch (SQLException e) {
            throw new UserException(UD_003, UD_003.getMessage(), e);
        } finally {
            try {
                closeConnection();
            } catch (SQLException e) {
                throw new UserException(UD_003f, UD_003f.getMessage(), e);
            }
        }
    }


    public List<String> getUserNameList() throws UserException {
        List<String> usernames = new ArrayList<>();
        try {
            Connection connection = getConnection();
            PreparedStatement prs = connection.prepareStatement("SELECT login FROM users");
            final ResultSet rs = prs.executeQuery();
            while (rs.next()) {
                String username = rs.getString("login");
                usernames.add(username);
            }
        } catch (SQLException e) {
            throw new UserException(UD_006, UD_006.getMessage(), e);
        } finally {
            try {
                closeConnection();
            } catch (SQLException e) {
                throw new UserException(UD_006f, UD_006f.getMessage(), e);
            }
        }
        return usernames;
    }


    public boolean update(String login, String newPassword) throws UserException {
        try {
            Connection connection = getConnection();
            PreparedStatement prs = connection.prepareStatement(
                    "UPDATE users SET password=? WHERE login=?");
            prs.setString(1, newPassword);
            prs.setString(2, login);
            prs.execute();
            return true;
        } catch (SQLException e) {
            throw new UserException(UD_004, UD_004.getMessage(), e);
        } finally {
            try {
                closeConnection();
            } catch (SQLException e) {
                throw new UserException(UD_004f, UD_004f.getMessage(), e);
            }
        }
    }

    @Override
    public boolean delete(String login) throws UserException {
        try {
            Connection connection = getConnection();
            PreparedStatement prs = connection.prepareStatement("DELETE FROM users WHERE login=?");
            prs.setString(1, login);
            prs.execute();
            return true;
        } catch (SQLException e) {
            throw new UserException(UD_005, UD_005.getMessage(), e);
        } finally {
            try {
                closeConnection();
            } catch (SQLException e) {
                throw new UserException(UD_005f, UD_005f.getMessage(), e);
            }
        }
    }
}
