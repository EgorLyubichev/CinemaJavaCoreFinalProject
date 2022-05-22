package by.lev.controller;

import by.lev.exceptions.UserException;
import by.lev.user.User;
import by.lev.user.UserDao;

import static by.lev.user.UsersAccessLevel.*;
import static by.lev.service.ServiceFunction.*;

public class Authorization {

    User userSEND = new User();
    public static User USER_ONLINE = new User();

    public void autorization() {
        inputLogin();
        checkLoginInDatabase();
        inputPassword();
        checkInputPassword();
        goToActualMenu();
    }

    private void inputLogin() {
        System.out.println("Введите ваш логин...");
        String login = scanString();
        userSEND.setLogin(login);
    }

    private void checkLoginInDatabase() {
        try {
            USER_ONLINE = new UserDao().read(userSEND.getLogin());
        } catch (UserException e) {
            e.printStackTrace();
        }
        if (USER_ONLINE.getLogin() == null) {
            System.out.println("Пользователь с таким логином не зарегистрирован.\n");
            new Menu().start();
        }
    }

    private void inputPassword() {
        System.out.println("Введите ваш пароль...");
        userSEND.setPassword(scanString());
    }

    private void checkInputPassword() {
        if (!USER_ONLINE.getPassword().equals(userSEND.getPassword())) {
            System.out.println("Введен неверный пароль.");
            new Menu().start();
        }
    }

    private void goToActualMenu() {
        if (USER_ONLINE.getLevel() == USER) {
            new UserMenu().showUserMenu();
        } else if (USER_ONLINE.getLevel() == MANAGER) {
            new ManagerMenu().showManagerMenu();
        } else if (USER_ONLINE.getLevel() == ADMINISTRATOR) {
            new AdministratorMenu().showAdminMenu();
        }
    }
}
