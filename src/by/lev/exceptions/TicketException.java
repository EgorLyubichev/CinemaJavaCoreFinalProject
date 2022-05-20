package by.lev.exceptions;

public class TicketException extends Exception{
    private EnumTicketException errorCode;

    public TicketException(EnumTicketException ex) {
        System.err.println("Error: " + ex + " '" + ex.getMessage() + "'");
    }
}
