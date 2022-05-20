package by.lev.controller;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegistrationFunctional {
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
            System.out.println("The field of password is empty!");
            return false;
        } else if (password.length() < 8) {
            System.out.println("Password length must be more than 7 symbols!");
            return false;
        } else if (password.length() > 46) {
            System.out.println("Password length must be less than 46 symbols!");
            return false;
        } else if (password.contains(" ")) {
            System.out.println("The password must not contain spaces");
            return false;
        } else if (getWordOfOnlyNumbersAndLetters(password) == false) {
            System.out.println("You can use only numbers or letters!");
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
