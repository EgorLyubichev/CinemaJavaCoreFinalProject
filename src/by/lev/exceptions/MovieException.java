package by.lev.exceptions;

public class MovieException extends Exception {
    private EnumMovieException errorCode;

    public MovieException(EnumMovieException errorCode, String message, Throwable cause) {
        super(message, cause);
        this.errorCode = errorCode;
    }
}
