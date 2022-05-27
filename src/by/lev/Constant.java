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
    READ_TICKETS_OF_THE_MOVIE("SELECT * FROM tickets WHERE movieID=?");

    private String message;

    Constant(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
