package by.lev.user;

import by.lev.exceptions.UserException;
import by.lev.interfaces.CrudOperation;

import java.util.List;

public interface UserDatabaseAction<T, R> extends CrudOperation<User, String, String> {
    List<String> getUserNameList() throws UserException;

}
