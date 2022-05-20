package by.lev.exceptions;

public class UserException extends Exception {
    private EnumUserException errorCode;

    public UserException(EnumUserException ex) {
        System.err.println("Error: " + ex + " '" + ex.getMessage() + "'");
    }
}
