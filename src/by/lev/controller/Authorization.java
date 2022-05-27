package by.lev.controller;

import by.lev.encoder.Base64encoder;
import by.lev.service.UserService;
import by.lev.user.User;

import static by.lev.user.UsersAccessLevel.*;
import static by.lev.controller.InputFunction.*;

public class Authorization implements AuthorizationInterface {

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
        USER_ONLINE = new UserService().getUser(userSEND.getLogin());
        if (USER_ONLINE.getLogin() == null) {
            System.out.println("Пользователь с таким логином не зарегистрирован.\n");
            new MainMenu().start();
        }
    }

    private void inputPassword() {
        System.out.println("Введите ваш пароль...");
        String password = scanString();
        String passwordCode = new Base64encoder().getEncode(password);
        userSEND.setPassword(passwordCode);
    }

    private void checkInputPassword() {
        if (!USER_ONLINE.getPassword().equals(userSEND.getPassword())) {
            System.out.println("Введен неверный пароль.");
            new MainMenu().start();
        }
    }

    private void goToActualMenu() {
        if (USER_ONLINE.getLevel() == USER) {
            new UserController().showUserMenu();
        } else if (USER_ONLINE.getLevel() == MANAGER) {
            new ManagerController().showManagerMenu();
        } else if (USER_ONLINE.getLevel() == ADMINISTRATOR) {
            new AdministratorController().showAdminMenu();
        }
    }
}
