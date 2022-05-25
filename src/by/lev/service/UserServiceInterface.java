package by.lev.service;

import by.lev.user.User;

import java.util.List;

public interface UserServiceInterface {
    User getUser(String login);
    void updatePassword(String login, String newPassword);
    List<String> getLoginsOfUsers();
    boolean removeUser(String login);
}
