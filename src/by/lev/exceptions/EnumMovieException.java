package by.lev.exceptions;

public enum EnumMovieException {
    MD_001("The movie was not added!"),
    MD_001f("The program couldn't close connection!"),
    MD_002("The movie couldn't be read!"),
    MD_002f("The program couldn't close connection!"),
    MD_003("Movies couldn't be read!"),
    MD_003f("The program couldn't close connection!"),
    MD_004("The movie couldn't be updated!"),
    MD_004f("The program couldn't close connection!"),
    MD_005("The movie couldn't be deleted!"),
    MD_005f("The program couldn't close connection!"),
    MD_006("Don't get a dateTime from database."),
    MD_006f("The program couldn't close connection!"),
    MD_007("Don't get the movieID from database."),
    MD_007f("The program couldn't close connection!"),
    MIC_01("The app couldn't get dateTime this movie!"),
    UC_01("The app couldn't get dateTime this movie!");

    private String message;

    EnumMovieException(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
