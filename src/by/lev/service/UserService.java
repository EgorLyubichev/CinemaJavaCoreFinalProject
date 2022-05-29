package by.lev.service;

import by.lev.exceptions.UserException;
import by.lev.user.User;
import by.lev.user.UserDao;
import by.lev.user.UserDaoInterface;
import by.lev.user.UserNameComparator;

import java.util.ArrayList;
import java.util.List;

public class UserService implements UserServiceInterface {

    public UserService(UserDaoInterface<User, String> userDao) {
    }

    @Override
    public boolean addUser(User user) {
        try {
            new UserDao().create(user);
            return true;
        } catch (UserException e) {
            return false;
        }
    }

    @Override
    public User getUser(String login) {
        User user = new User();
        try {
            user = new UserDao().read(login);

        } catch (UserException e) {
            e.printStackTrace();
        }
        return user;
    }

    @Override
    public boolean updatePassword(String login, String newPassword) {
        try {
            new UserDao().update(login, newPassword);
            return true;
        } catch (UserException e) {
            return false;
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

    @Override
    public boolean removeUser(String login) {
        try {
            new UserDao().delete(login);
            return true;
        } catch (UserException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public List<User> getUserList() {
        try {
            List<User> userList = new UserDao().readAll();
            return userList;
        } catch (UserException e) {
            return null;
        }
    }
}
