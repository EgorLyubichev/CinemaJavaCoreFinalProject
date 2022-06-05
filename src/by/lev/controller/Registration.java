package by.lev.controller;

import by.lev.encoder.Base64encoder;
import by.lev.service.UserServiceInterface;
import by.lev.user.User;

import java.util.ArrayList;
import java.util.List;

import static by.lev.Main.entrance;
import static by.lev.controller.InputFunction.*;
import static by.lev.controller.InputFunction.scanString;
import static by.lev.logger.Logger.writeRegistration;

public class Registration implements RegistrationInterface{
    UserServiceInterface usServ;

    public Registration(UserServiceInterface usServ) {
        this.usServ = usServ;
    }

    User newUser = new User();

    public void createNewUser() {
        setCorrectLogin();
        setCorrectPassword();
        usServ.addUser(newUser);
        writeRegistration(newUser);
        System.out.println("учетная запись успешно добавлена");
    }

    private void setCorrectLogin() {
        System.out.println("- - -| CACTUS CINEMA |- - -");
        System.out.println("- - -|  РЕГИСТРАЦИЯ  |- - -");
        System.out.println("введите логин для новой учетной записи...\n<0> - для выхода из регистрации");
        String login = scanString();
        while (!checkTheCorrectnessOfTheLoginInput(login)) {
            login = scanString();
        }
        if ("0".equals(login)){
            entrance.start();
        }
        List<String> userLoginList = usServ.getLoginsOfUsers();
        List<String> userLoginListToLowerCase = new ArrayList<>();
        for (String loginFromDb : userLoginList) {
            userLoginListToLowerCase.add(loginFromDb.toLowerCase());
        }
        if (userLoginListToLowerCase.contains(login.toLowerCase())) {
            System.out.println("пользователь с таким именем уже существует");
            setCorrectLogin();
        }
        newUser.setLogin(login);
    }
    private void setCorrectPassword() {
        System.out.println("Пароль: ...");
        String password = scanString();
        while (!checkTheCorrectnessOfThePasswordInput(password)) {
            password = scanString();
        }
        System.out.println("повторите пароль ...");
        String repeatPassword = scanString();
        if (password.equals(repeatPassword)) {
            newUser.setPassword(new Base64encoder().getEncode(password));
        } else {
            System.out.println("введенные пароли не совпадают");
            setCorrectPassword();
        }
    }

}