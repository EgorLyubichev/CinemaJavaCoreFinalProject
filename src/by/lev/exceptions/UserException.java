package by.lev.exceptions;

public class UserException extends Exception {
    private EnumUserException errorCode;

    public UserException(EnumUserException errorCode, String message, Throwable cause) {
        super(message, cause);
        this.errorCode = errorCode;
    }
}
