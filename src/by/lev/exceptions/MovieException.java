package by.lev.exceptions;

public class MovieException extends Exception{
    private EnumMovieException errorCode;

    public MovieException(EnumMovieException ex) {
        System.err.println("Error: " + ex + " '" + ex.getMessage() + "'");
    }
}
