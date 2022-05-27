package by.lev.exceptions;

import static by.lev.Constant.COULD_NOT_CLOSE_CONNECTION;

public enum EnumTicketException {
    TD_01("The ticket was not added!"),
    TD_01F(COULD_NOT_CLOSE_CONNECTION.getMessage()),
    TD_02("The ticket couldn't be read!"),
    TD_02F(COULD_NOT_CLOSE_CONNECTION.getMessage()),
    TD_03("The ticketList couldn't be read!"),
    TD_03F(COULD_NOT_CLOSE_CONNECTION.getMessage()),
    TD_04("The tickets could not be read!"),
    TD_04F(COULD_NOT_CLOSE_CONNECTION.getMessage()),
    TD_05("Id of tickets couldn't be read!"),
    TD_05F(COULD_NOT_CLOSE_CONNECTION.getMessage()),
    TD_06("Did not delete username from the ticket"),
    TD_06F(COULD_NOT_CLOSE_CONNECTION.getMessage()),
    TD_07("Didn't set username in the ticket"),
    TD_07F(COULD_NOT_CLOSE_CONNECTION.getMessage()),
    TD_08("Didn't delete the ticket"),
    TD_08F(COULD_NOT_CLOSE_CONNECTION.getMessage()),
    TD_09("Didn't delete tickets with the movie id"),
    TD_09F(COULD_NOT_CLOSE_CONNECTION.getMessage()),
    TD_10("Didn't read ticket list"),
    TD_10F(COULD_NOT_CLOSE_CONNECTION.getMessage());

    private String message;

    EnumTicketException(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
