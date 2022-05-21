package by.lev.exceptions;

public enum EnumUserException {
    UD_001("User was not added!"),
    UD_001f("The program couldn't close connection!"),
    UD_002("User cannot be read!"),
    UD_002f("The program couldn't close connection!"),
    UD_003("Users cannot be read!"),
    UD_003f("The program couldn't close connection!"),
    UD_004("Password wasn't updated!"),
    UD_004f("The program couldn't close connection!"),
    UD_005("User was not deleted!"),
    UD_005f("The program couldn't close connection!"),
    UD_006("Usernames cannot be read!"),
    UD_006f("The program couldn't close connection!"),

    USC_01("The app couldn't to check the login in database!"),

    AF_01("Can't read user from DB");

    private String message;

    EnumUserException(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
