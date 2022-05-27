package by.lev.exceptions;

import static by.lev.Constant.*;

public enum EnumUserException {
    UD_01("User was not added!"),
    UD_01F(COULD_NOT_CLOSE_CONNECTION.getMessage()),
    UD_02("User cannot be read!"),
    UD_02F(COULD_NOT_CLOSE_CONNECTION.getMessage()),
    UD_03("Users cannot be read!"),
    UD_03F(COULD_NOT_CLOSE_CONNECTION.getMessage()),
    UD_04("Password wasn't updated!"),
    UD_04F(COULD_NOT_CLOSE_CONNECTION.getMessage()),
    UD_05("User was not deleted!"),
    UD_05F(COULD_NOT_CLOSE_CONNECTION.getMessage()),
    UD_06("Usernames cannot be read!"),
    UD_06F(COULD_NOT_CLOSE_CONNECTION.getMessage());

    private String message;

    EnumUserException(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
