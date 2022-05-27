package by.lev.encoder;

import java.util.Base64;

public class Base64encoder {
    public String getEncode(String password){
        return Base64.getEncoder().encodeToString(password.getBytes());
    }
}
