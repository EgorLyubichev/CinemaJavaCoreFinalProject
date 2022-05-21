package by.lev.service.inputChecks;

import by.lev.databaseConnection.AbstractConnection;
import by.lev.exceptions.UserException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import static by.lev.exceptions.EnumUserException.USC_01;

public class UserInputCheck {






    public boolean isTheLoginAndPasswordInTheDatabase(String login, String password) throws UserException {
        String loginFromDatabase = null;
        String passwordFromDatabase = null;
        try {
            Connection connection = AbstractConnection.getConnection();
            PreparedStatement prs = connection.prepareStatement("SELECT login AND password FROM users WHERE login=?");
            prs.setString(1, login);
            ResultSet rs = prs.executeQuery();
            while (rs.next()) {
                loginFromDatabase = rs.getString("login");
                passwordFromDatabase = rs.getString("password");
            }
        } catch (SQLException e) {
            throw new UserException(USC_01);
        } finally {
            try {
                AbstractConnection.closeConnection();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        if (login.equalsIgnoreCase(loginFromDatabase) && password.equals(passwordFromDatabase)) {
            return true;
        } else {
            System.out.println("Вы ввели неверный пароль!");
            return false;
        }
    }


}
