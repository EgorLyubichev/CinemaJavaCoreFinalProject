package by.lev.controller;

import java.util.Scanner;

public class InputFunction {
    public static String scanString() {
        Scanner scanner = new Scanner(System.in);
        return scanner.nextLine();
    }

    public static int scanInt() {
        Scanner scanner = new Scanner(System.in);
        return scanner.nextInt();
    }
}
