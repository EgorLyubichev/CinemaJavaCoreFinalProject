package by.lev.controller;

import by.lev.controller.menu.UserMenu;
import by.lev.exceptions.UserException;
import by.lev.user.User;
import by.lev.user.UserDAO;

import static by.lev.user.UsersAccessLevel.*;
import static by.lev.controller.controllerFunctions.ControllerFunction.*;

public class AuthorizationFunctional {

    User userSEND = new User();
    public static User USER_MAIN = new User();

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
            USER_MAIN = new UserDAO().read(userSEND.getLogin());
        } catch (UserException e) {
            e.printStackTrace();
        }
        if (USER_MAIN.getLogin() == null) {
            System.out.println("Пользователь с таким логином не зарегистрирован.\n");
            new Entrance().start();
        }
    }

    private void inputPassword() {
        System.out.println("Введите ваш пароль...");
        userSEND.setPassword(scanString());
    }

    private void checkInputPassword() {
        if (!USER_MAIN.getPassword().equals(userSEND.getPassword())) {
            System.out.println("Введен неверный пароль.");
            new Entrance().start();
        }
    }

    private void goToActualMenu() {
        if (USER_MAIN.getLevel() == USER) {
            new UserMenu().showUserMenu();
        } else if (USER_MAIN.getLevel() == MANAGER) {

        } else if (USER_MAIN.getLevel() == ADMINISTRATOR) {

        }
    }
}
