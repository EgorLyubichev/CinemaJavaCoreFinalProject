package by.lev;

import by.lev.encoder.Base64encoder;

public class MyTest {
    public static void main(String[] args) {
        String name = "BigBoss";
        String password = new Base64encoder().getEncode(name);
        System.out.println(password);
    }
}
