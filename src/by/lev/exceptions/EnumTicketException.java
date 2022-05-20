package by.lev.exceptions;

public enum EnumTicketException {
    TD_001("The ticket was not added!"),
    TD_001f("The program couldn't close connection!"),
    TD_002("The ticket could not be read!"),
    TD_002f("The program couldn't close connection!"),
    TD_003("The ticket could not be read!"),
    TD_003f("The program couldn't close connection!"),
    TD_0031("The ticket could not be read!"),
    TD_0031f("The program couldn't close connection!"),
    TD_004("The ticket could not be updated!"),
    TD_0041("The ticket could not be updated!"),
    TD_004f("The program couldn't close connection!"),
    TD_0041f("The program couldn't close connection!"),
    TD_005("The ticket could not be deleted!"),
    TD_005f("The program couldn't close connection!"),
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
