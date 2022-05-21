package by.lev.exceptions;

public class MovieException extends Exception{
    private EnumMovieException errorCode;

    //public MovieException(EnumMovieException ex) {
//        System.err.println("Error: " + ex + " '" + ex.getMessage() + "'");
//    }

    public MovieException(EnumMovieException errorCode, String message, Throwable cause) {
        //super(message, cause);
        //this.errorCode = errorCode;
        System.out.print(errorCode + ": ");
        System.out.println(message);
        if(cause != null){System.out.println(cause);}
    }

}
