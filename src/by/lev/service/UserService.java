package by.lev.service;

import by.lev.exceptions.UserException;
import by.lev.user.UserDao;
import by.lev.user.UserNameComparator;

import java.util.ArrayList;
import java.util.List;

public class UserService implements UserServiceInterface{
    @Override
    public void updatePassword(String login, String newPassword) {
        try {
            new UserDao().update(login, newPassword);
        } catch (UserException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<String> getLoginsOfUsers() {
        List<String> loginsOfUsers = new ArrayList<>();
        try {
            loginsOfUsers = new UserDao().readUserNameList();
        } catch (UserException e) {
            e.printStackTrace();
        }
        loginsOfUsers.sort(new UserNameComparator());
        return loginsOfUsers;
    }
}
