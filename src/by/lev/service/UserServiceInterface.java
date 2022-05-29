package by.lev.service;

import by.lev.user.User;

import java.util.List;

public interface UserServiceInterface {
    boolean addUser(User user);
    User getUser(String login);
    boolean updatePassword(String login, String newPassword);
    List<String> getLoginsOfUsers();
    boolean removeUser(String login);
    List<User> getUserList();
}
