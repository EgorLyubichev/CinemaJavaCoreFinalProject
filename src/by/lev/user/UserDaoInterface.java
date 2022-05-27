package by.lev.user;

import by.lev.exceptions.UserException;
import by.lev.interfaces.DaoOperation;

import java.util.List;

public interface UserDaoInterface<User, String> extends DaoOperation<User, String> {
    List<String> readUserNameList() throws UserException;
    boolean update(String login, String newPassword) throws UserException;
}
