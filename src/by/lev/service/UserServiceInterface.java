package by.lev.service;

import java.util.List;

public interface UserServiceInterface {
    void updatePassword(String login, String newPassword);
    List<String> getLoginsOfUsers();
}
