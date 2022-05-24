package by.lev.exceptions;

public class TicketException extends Exception {
    private EnumTicketException errorCode;

    public TicketException(EnumTicketException errorCode, String message, Throwable cause) {
        super(message, cause);
        this.errorCode = errorCode;
    }
}
