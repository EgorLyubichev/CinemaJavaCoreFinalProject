package by.lev.exceptions;

public enum EnumTicketException {
    TD_01("The ticket was not added!"),
    TD_01f("The program couldn't close connection!"),
    TD_02("The ticket could not be read!"),
    TD_02f("The program couldn't close connection!"),
    TD_03("The ticketList could not be read!"),
    TD_03f("The program couldn't close connection!"),
    TD_031("The ticket could not be read!"),
    TD_031f("The program couldn't close connection!"),
    TD_04("The ticket could not be updated!"),
    TD_042("The ticket could not be updated!"),
    TD_04f("The program couldn't close connection!"),
    TD_042f("The program couldn't close connection!"),
    TD_05("The ticket could not be updated!"),
    TD_05f("The program couldn't close connection!"),
    TD_06("The ticketList could not be read!"),
    TD_06f("The program couldn't close connection!"),
    MSC_01("The app couldn't check movieID from the ticket!"),
    UC_01("The ticket could not be read!");

    private String message;

    EnumTicketException(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
