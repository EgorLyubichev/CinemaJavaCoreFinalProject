package by.lev.controller;

import by.lev.exceptions.UserException;
import by.lev.user.User;
import by.lev.user.UserDao;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static by.lev.controller.InputFunction.*;

public class Registration {
    User newUser = new User();

    public void createNewUser() {
        setCorrectLogin();
        setCorrectPassword();

        try {
            new UserDao().create(newUser);
        } catch (UserException e) {
            throw new RuntimeException(e);
        }
        System.out.println("учетная запись успешно добавлена");
    }

    public void setCorrectLogin() {
        System.out.println("- - -| CACTUS CINEMA |- - -");
        System.out.println("- - -|  РЕГИСТРАЦИЯ  |- - -");
        System.out.println("введите логин для новой учетной записи: ");
        String login = scanString();
        while (checkTheCorrectnessOfTheLoginInput(login) == false) {
            login = scanString();
        }
        List<String> userLoginList = new ArrayList<>();
        try {
            userLoginList = new UserDao().readUserNameList();
            for (String username:userLoginList) {
                username = username.toLowerCase();
            }
        } catch (UserException e) {
            throw new RuntimeException(e);
        }
        if (userLoginList.contains(login.toLowerCase())){
            System.out.println("пользователь с таким именем уже существует");
            setCorrectLogin();
        }
            newUser.setLogin(login);
    }

    public void setCorrectPassword() {
        System.out.println("Пароль: ...");
        String password = scanString();
        while (checkTheCorrectnessOfThePasswordInput(password) == false) {
            password = scanString();
        }
        System.out.println("повторите пароль ...");
        String repeatPassword = scanString();
        if (password.equals(repeatPassword)) {
            newUser.setPassword(password);
        } else {
            System.out.println("введенные пароли не совпадают");
            setCorrectPassword();
        }
    }

    public boolean checkTheCorrectnessOfTheLoginInput(String login) {
        if (login.isEmpty()) {
            System.out.println("Поле 'логин' осталось пустым!");
            return false;
        } else if (login.length() < 3) {
            System.out.println("Длина логина должна быть не менее 3-х символов!");
            return false;
        } else if (login.length() > 28) {
            System.out.println("Длина логина не может быть больше 28 символов!");
            return false;
        } else if (login.contains(" ")) {
            System.out.println("Логин не может содержать пробелы!");
            return false;
        } else if (getWordOfOnlyNumbersAndLetters(login) == false) {
            System.out.println("Вы можете использовать только цифры и буквы!");
            return false;
        } else {
            return true;
        }
    }

    public boolean checkTheCorrectnessOfThePasswordInput(String password) {
        if (password.isEmpty()) {
            System.out.println("поле осталось пустым");
            return false;
        } else if (password.length() < 6) {
            System.out.println("пароль не должен быть менее 6-ти символов");
            return false;
        } else if (password.length() > 36) {
            System.out.println("пароль не может быть более 36 символов");
            return false;
        } else if (password.contains(" ")) {
            System.out.println("пароль не может содержать пробелов");
            return false;
        } else if (getWordOfOnlyNumbersAndLetters(password) == false) {
            System.out.println("Вы можете использовать только буквы и цифры");
            return false;
        } else {
            return true;
        }
    }

    private static boolean getWordOfOnlyNumbersAndLetters(String word) {
        Pattern pattern = Pattern.compile("[A-Za-z0-9]{1,}");
        Matcher matcher = pattern.matcher(word);
        return matcher.matches();
    }

    public String createARandomPassword() {
        int passwordLength = 16;
        String abc = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        String result = "";
        for (int i = 0; i < passwordLength; i++) {
            result += abc.charAt((int) (Math.random() * abc.length()));
        }
        return result;
    }

}



