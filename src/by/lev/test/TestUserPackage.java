package by.lev.test;

import by.lev.service.UserService;
import by.lev.service.UserServiceInterface;
import by.lev.user.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;

public class TestUserPackage {

    @Test
    public void set_Administrator_expect_Administrator(){
        User administrator = new Administrator("TestAdmin", "TestAdmPassword");
        UserDaoInterface<User, String> usd = new UserDao();
        UserServiceInterface uss = new UserService(usd);
        boolean add = uss.addUser(administrator);
        Assertions.assertTrue(add);

        User returnAdmin = uss.getUser(administrator.getLogin());
        Assertions.assertEquals(UsersAccessLevel.ADMINISTRATOR, returnAdmin.getLevel());

        boolean remove = uss.removeUser(returnAdmin.getLogin());
        Assertions.assertTrue(remove);

        administrator = uss.getUser(returnAdmin.getLogin());
        Assertions.assertNull(administrator.getLogin());


    }

    @Test
    public void set_Manager_expect_Manager(){
        User manager = new Manager("TestManager", "TestManPassword");
        UserDaoInterface<User, String> usd = new UserDao();
        UserServiceInterface uss = new UserService(usd);
        boolean add = uss.addUser(manager);
        Assertions.assertTrue(add);

        User returnManager = uss.getUser(manager.getLogin());
        Assertions.assertEquals(UsersAccessLevel.MANAGER, returnManager.getLevel());

        boolean remove = uss.removeUser(returnManager.getLogin());
        Assertions.assertTrue(remove);

        manager = uss.getUser(returnManager.getLogin());
        Assertions.assertNull(manager.getLogin());
    }

    @Test
    public void common_test_UserDao_methods() {
        try {
            UserDaoInterface<User, String> usd = new UserDao();
            User user = new User("TestLogin", "TestPassword");

            boolean create = usd.create(user);
            Assertions.assertTrue(create);

            String newPassword = "newPasswordTest";
            boolean update = usd.update(user.getLogin(), newPassword);
            Assertions.assertTrue(update);

            boolean delete = usd.delete(user.getLogin());
            Assertions.assertTrue(delete);

            user = usd.read(user.getLogin());
            Assertions.assertNull(user.getLogin());

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void expect_ListOfUsers(){
        UserDaoInterface<User, String> usd = new UserDao();
        UserServiceInterface usi = new UserService(usd);
        List<User> userList = usi.getUserList();
        Assertions.assertFalse(userList.isEmpty());
    }

    @Test
    public void expect_ListOfLogins(){
        UserDaoInterface<User, String> usd = new UserDao();
        UserServiceInterface usi = new UserService(usd);
        List<String> loginList = usi.getLoginsOfUsers();
        Assertions.assertFalse(loginList.isEmpty());
    }

    @Test
    public void equals_other_users_expect_false(){
        User user1 = new User("Test1", "Pass");
        User user2 = new User("Test2", "Pass");
        boolean result = user1.equals(user2);
        Assertions.assertFalse(result);
    }

}
