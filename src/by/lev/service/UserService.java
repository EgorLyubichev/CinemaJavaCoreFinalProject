package by.lev.service;

import by.lev.exceptions.UserException;
import by.lev.user.User;
import by.lev.user.UserDao;
import by.lev.user.UserDaoInterface;
import by.lev.user.UserNameComparator;

import java.util.ArrayList;
import java.util.List;

public class UserService implements UserServiceInterface {
    private UserDaoInterface<User, String> userDao;

    public UserService(UserDaoInterface<User, String> userDao) {
        this.userDao = userDao;
    }

    @Override
    public boolean addUser(User user) {
        try {
            userDao.create(user);
            return true;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public User getUser(String login) {
        User user = new User();
        try {
            user = userDao.read(login);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return user;
    }

    @Override
    public boolean updatePassword(String login, String newPassword) {
        try {
            userDao.update(login, newPassword);
            return true;
        } catch (UserException e) {
            return false;
        }
    }

    @Override
    public List<String> getLoginsOfUsers() {
        List<String> loginsOfUsers = new ArrayList<>();
        try {
            loginsOfUsers = userDao.readUserNameList();
        } catch (UserException e) {
            e.printStackTrace();
        }
        loginsOfUsers.sort(new UserNameComparator());
        return loginsOfUsers;
    }

    @Override
    public boolean removeUser(String login) {
        try {
            userDao.delete(login);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public List<User> getUserList() {
        List<User> userList = new ArrayList<>();
        try {
             userList = userDao.readAll();
            return userList;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return userList;
    }
}
