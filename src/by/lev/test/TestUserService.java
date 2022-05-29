package by.lev.test;

import by.lev.service.UserService;
import by.lev.service.UserServiceInterface;
import by.lev.user.User;
import by.lev.user.UserDao;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

public class TestUserService {

    @Test
    public void userServiceTest() {
        UserServiceInterface usi = new UserService(new UserDao());
        User testUser = new User("TestLogin", "TestPassword");
        usi.addUser(testUser);
        User testUser2 = usi.getUser(testUser.getLogin());
        Assertions.assertTrue(testUser.equals(testUser2));
        testUser.setPassword("newTestPassword");
        usi.updatePassword(testUser.getLogin(), testUser.getPassword());
        testUser2 = usi.getUser(testUser.getLogin());
        Assertions.assertTrue(testUser.equals(testUser2));
        usi.removeUser(testUser.getLogin());
        testUser2 = usi.getUser(testUser.getLogin());
        Assertions.assertNull(testUser2.getLogin());
    }

    @Test
    public void getLoginListTest() {
        UserServiceInterface usi = new UserService(new UserDao());
        List<String> loginList = usi.getLoginsOfUsers();
        Assertions.assertFalse(loginList.isEmpty());
    }
}
