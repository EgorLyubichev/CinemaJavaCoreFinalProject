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
import static by.lev.Constant.*;

public class UserDao implements UserDaoInterface<User, String> {
    @Override
    public boolean create(User user) throws UserException {
        try {
            Connection connection = getConnection();
            PreparedStatement prs = connection.prepareStatement(USER_CREATE.getMessage());
            prs.setString(1, user.getLogin());
            prs.setString(2, user.getPassword());
            prs.setString(3, user.getLevel().toString());
            prs.execute();
            return true;
        } catch (SQLException e) {
            throw new UserException(UD_01, UD_01.getMessage(), e);
        } finally {
            try {
                closeConnection();
            } catch (SQLException e) {
                throw  new UserException(UD_01F, UD_01F.getMessage(), e);
            }
        }
    }

    @Override
    public User read(String userName) throws UserException {
        User user = new User();
        try {
            Connection connection = getConnection();
            PreparedStatement prs = connection.prepareStatement(USER_READ.getMessage());
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
            throw new UserException(UD_02, UD_02.getMessage(), e);
        } finally {
            try {
                closeConnection();
            } catch (SQLException e) {
                throw new UserException(UD_02F, UD_02F.getMessage(), e);
            }
        }
    }

    @Override
    public List<User> readAll() throws UserException {
        List<User> users = new ArrayList<>();
        try {
            Connection connection = getConnection();
            PreparedStatement prs = connection.prepareStatement(USER_READ_ALL.getMessage());
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
            throw new UserException(UD_03, UD_03.getMessage(), e);
        } finally {
            try {
                closeConnection();
            } catch (SQLException e) {
                throw new UserException(UD_03F, UD_03F.getMessage(), e);
            }
        }
    }

    @Override
    public List<String> readUserNameList() throws UserException {
        List<String> usernames = new ArrayList<>();
        try {
            Connection connection = getConnection();
            PreparedStatement prs = connection.prepareStatement(USER_READ_ALL_LOGINS.getMessage());
            final ResultSet rs = prs.executeQuery();
            while (rs.next()) {
                String username = rs.getString("login");
                usernames.add(username);
            }
        } catch (SQLException e) {
            throw new UserException(UD_06, UD_06.getMessage(), e);
        } finally {
            try {
                closeConnection();
            } catch (SQLException e) {
                throw new UserException(UD_06F, UD_06F.getMessage(), e);
            }
        }
        return usernames;
    }

    @Override
    public boolean update(String login, String newPassword) throws UserException {
        try {
            Connection connection = getConnection();
            PreparedStatement prs = connection.prepareStatement(USER_UPDATE_PASSWORD.getMessage());
            prs.setString(1, newPassword);
            prs.setString(2, login);
            prs.execute();
            return true;
        } catch (SQLException e) {
            throw new UserException(UD_04, UD_04.getMessage(), e);
        } finally {
            try {
                closeConnection();
            } catch (SQLException e) {
                throw new UserException(UD_04F, UD_04F.getMessage(), e);
            }
        }
    }

    @Override
    public boolean delete(String login) throws UserException {
        try {
            Connection connection = getConnection();
            PreparedStatement prs = connection.prepareStatement(USER_DELETE.getMessage());
            prs.setString(1, login);
            prs.execute();
            return true;
        } catch (SQLException e) {
            throw new UserException(UD_05, UD_05.getMessage(), e);
        } finally {
            try {
                closeConnection();
            } catch (SQLException e) {
                throw new UserException(UD_05F, UD_05F.getMessage(), e);
            }
        }
    }
}
