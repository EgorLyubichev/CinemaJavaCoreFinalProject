package by.lev.exceptions;

import static by.lev.Constant.COULD_NOT_CLOSE_CONNECTION;

public enum EnumMovieException {
    MD_01("The movie was not added!"),
    MD_01F(COULD_NOT_CLOSE_CONNECTION.getMessage()),
    MD_02("The movie couldn't be read!"),
    MD_02F(COULD_NOT_CLOSE_CONNECTION.getMessage()),
    MD_03("Movie couldn't be read!"),
    MD_03F(COULD_NOT_CLOSE_CONNECTION.getMessage()),
    MD_04("Movie list couldn't be read"),
    MD_04F(COULD_NOT_CLOSE_CONNECTION.getMessage()),
    MD_05("Didn't update title"),
    MD_05F(COULD_NOT_CLOSE_CONNECTION.getMessage()),
    MD_06("Didn't update timestamp"),
    MD_06F(COULD_NOT_CLOSE_CONNECTION.getMessage()),
    MD_07("Didn't delete movie"),
    MD_07F(COULD_NOT_CLOSE_CONNECTION.getMessage()),
    MD_08("Didn't read timestamp"),
    MD_08F(COULD_NOT_CLOSE_CONNECTION.getMessage()),
    MD_09("Didn't read title"),
    MD_09F(COULD_NOT_CLOSE_CONNECTION.getMessage()),
    MD_10("Didn't read movie id"),
    MD_10F(COULD_NOT_CLOSE_CONNECTION.getMessage()),
    MD_11("Didn't check slot of timestamp"),
    MD_11F(COULD_NOT_CLOSE_CONNECTION.getMessage());

    private String message;

    EnumMovieException(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
