package by.lev.controller.controllerFunctions;

import java.util.Scanner;

public class ControllerFunction {
    public static String scanString() {
        Scanner scanner = new Scanner(System.in);
        return scanner.nextLine();
    }

    public static int scanInt() {
        Scanner scanner = new Scanner(System.in);
        return scanner.nextInt();
    }
}
