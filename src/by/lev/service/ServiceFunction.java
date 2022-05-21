package by.lev.service;

import java.util.Scanner;

public class ServiceFunction {
    public static String scanString() {
        Scanner scanner = new Scanner(System.in);
        return scanner.nextLine();
    }

    public static int scanInt() {
        Scanner scanner = new Scanner(System.in);
        return scanner.nextInt();
    }
}
