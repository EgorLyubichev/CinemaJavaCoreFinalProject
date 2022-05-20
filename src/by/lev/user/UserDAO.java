package by.lev.user;

import by.lev.Interfaces.CRUDoperation;
import by.lev.exceptions.UserException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static by.lev.databaseConnection.AbstractConnection.*;
import static by.lev.exceptions.EnumUserException.*;

public class UserDAO implements CRUDoperation<User, String> {
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
            throw new UserException(UD_001);
        } finally {
            try {
                closeConnection();
            } catch (SQLException e) {
                throw new UserException(UD_001f);
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
        } catch (SQLException throwables) {
            throw new UserException(UD_002);
        } finally {
            try {
                closeConnection();
            } catch (SQLException e) {
                throw new UserException(UD_002f);
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
            throw new UserException(UD_003);
        }finally {
            try {
                closeConnection();
            } catch (SQLException e) {
                throw new UserException(UD_003f);
            }
        }
    }

    @Override
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
            throw new UserException(UD_004);
        }finally {
            try {
                closeConnection();
            } catch (SQLException e) {
                throw new UserException(UD_004f);
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
            throw new UserException(UD_005);
        }finally {
            try {
                closeConnection();
            } catch (SQLException e) {
                throw new UserException(UD_005f);
            }
        }
    }
}
