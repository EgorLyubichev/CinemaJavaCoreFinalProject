package by.lev.controller;

import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class InputFunction {
    public static String scanString() {
        Scanner scanner = new Scanner(System.in);
        return scanner.nextLine();
    }

    public static int scanInt() {
        Scanner scanner = new Scanner(System.in);
        return scanner.nextInt();
    }

    public static boolean checkTheCorrectnessOfTheLoginInput(String login) {
        if ("0".equals(login)) {
            return true;
        }else if (login.isEmpty()) {
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
        } else if (!getWordOfOnlyNumbersAndLetters(login)) {
            System.out.println("Вы можете использовать только цифры и буквы!");
            return false;
        } else {
            return true;
        }
    }

    public static boolean checkTheCorrectnessOfThePasswordInput(String password) {
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
        } else if (!getWordOfOnlyNumbersAndLetters(password)) {
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
}
