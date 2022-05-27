package by.lev;

public enum Constant {
    //Controller
    INCORRECT_INPUT("\t>>> НЕВЕРНЫЙ ВВОД <<<"),

    //Dao
    COULD_NOT_CLOSE_CONNECTION("Couldn't close the connection!"),

    //UserDao
    USER_CREATE("INSERT INTO users (login, password, level) VALUES (?,?,?)"),
    USER_READ("SELECT userID, login, password, level FROM users WHERE login=?"),
    USER_READ_ALL("SELECT * FROM users"),
    USER_READ_ALL_LOGINS("SELECT login FROM users"),
    USER_UPDATE_PASSWORD("UPDATE users SET password=? WHERE login=?"),
    USER_DELETE("DELETE FROM users WHERE login=?"),

    //TicketDao
    TICKET_CREATE("INSERT INTO tickets (userName, movieID, place, cost) VALUES (?,?,?,?)"),
    TICKET_READ("SELECT userName, movieID, place, cost FROM tickets WHERE ticketID=?"),
    TICKET_READ_ALL("SELECT * FROM tickets"),
    TICKET_READ_TICKETS_OF_THE_USER("SELECT * FROM tickets WHERE userName=?"),
    TICKET_READ_TICKETS_ID_OF_THE_USER("SELECT ticketID FROM tickets WHERE userName=?"),
    TICKET_REMOVE_USERNAME_FROM_THE_TICKET("UPDATE tickets SET userName=? WHERE ticketID=?"),
    TICKET_SET_THE_USERNAME_IN_THE_TICKET("UPDATE tickets SET userName=? WHERE ticketID=?"),
    TICKET_DELETE_BY_TICKET_ID("DELETE FROM tickets WHERE ticketID=?"),
    TICKET_DELETE_BY_MOVIE_ID("DELETE FROM tickets WHERE movieID=?"),
    READ_TICKETS_OF_THE_MOVIE("SELECT * FROM tickets WHERE movieID=?"),

    //MovieDao
    MOVIE_CREATE("INSERT INTO movie (movieTitle, dateTime) VALUES (?,?)"),
    MOVIE_READ_BY_MOVIE_ID("SELECT * FROM movie WHERE movieID=?"),
    MOVIE_READ_BY_TITLE("SELECT * FROM movie WHERE movieTitle=?"),
    MOVIE_READ_ALL("SELECT * FROM movie"),
    MOVIE_UPDATE_TITLE("UPDATE movie SET movieTitle=? WHERE movieID=?"),
    MOVIE_UPDATE_TIMESTAMP("UPDATE movie SET dateTime=? WHERE movieID=?"),
    MOVIE_DELETE("DELETE FROM movie WHERE movieID=?"),
    MOVIE_READ_TIMESTAMP_LIST_OF_THE_MOVIE("SELECT dateTime FROM movie WHERE movieTitle=?"),
    MOVIE_READ_TITLES("SELECT movieTitle FROM movie"),
    MOVIE_READ_MOVIE_ID_WHERE_TIMESTAMP("SELECT movieID FROM movie WHERE dateTime=?"),
    MOVIE_READ_TIMESTAMP_WHERE_THE_TIMESTAMP("SELECT dateTime FROM movie WHERE dateTime=?")
    ;

    private String message;

    Constant(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
