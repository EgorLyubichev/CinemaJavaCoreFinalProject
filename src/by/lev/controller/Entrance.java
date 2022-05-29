package by.lev.controller;

import by.lev.encoder.Base64encoder;
import by.lev.service.InputCorrectness;
import by.lev.service.UserServiceInterface;
import by.lev.user.User;

import static by.lev.Constant.INCORRECT_INPUT;
import static by.lev.controller.InputFunction.*;
import static by.lev.logger.Logger.*;
import static by.lev.user.UsersAccessLevel.*;

public class Entrance implements EntranceInterface {
    private UserServiceInterface usServ;
    private UserControllerInterface usCont;
    private ManagerControllerInterface manCont;
    private AdministratorControllerInterface adCont;
    private RegistrationInterface registration;

    public Entrance(UserServiceInterface usServ,
                    UserControllerInterface usCont,
                    ManagerControllerInterface manCont,
                    AdministratorControllerInterface adCont,
                    RegistrationInterface registration) {
        this.usServ = usServ;
        this.usCont = usCont;
        this.manCont = manCont;
        this.adCont = adCont;
        this.registration = registration;
    }

    User userSEND = new User();
    public static User USER_ONLINE = new User();

    public void start() {
        System.out.println("- - -| CACTUS CINEMA |- - -");
        System.out.println("<1> - ВХОД");
        System.out.println("<2> - РЕГИСТРАЦИЯ");
        System.out.println("<0> - ВЫХОД");
        int value = new InputCorrectness().inputCorrectValueFromZeroToTwo();
        if (value == -1) {
            System.out.println(INCORRECT_INPUT.getMessage());
            start();
        }
        switch (value) {
            case 1:
                authorization();
                break;
            case 2:
                registration.createNewUser();
                start();
                break;
            case 0:
                System.exit(0);

        }
    }

    public void authorization() {
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
        USER_ONLINE = usServ.getUser(userSEND.getLogin());
        if (USER_ONLINE.getLogin() == null) {
            System.out.println("Пользователь с таким логином не зарегистрирован.\n");
            start();
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
            start();
        }
    }

    private void goToActualMenu() {
        writeUserVisit();
        if (USER_ONLINE.getLevel() == USER) {
            usCont.showUserMenu();
        } else if (USER_ONLINE.getLevel() == MANAGER) {
            manCont.showManagerMenu();
        } else if (USER_ONLINE.getLevel() == ADMINISTRATOR) {
            adCont.showAdminMenu();
        }
    }
}
