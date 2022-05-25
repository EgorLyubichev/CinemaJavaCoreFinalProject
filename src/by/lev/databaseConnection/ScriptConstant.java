package by.lev.databaseConnection;

public enum ScriptConstant {
    //User

    //Ticket
    GET_ALL_FIELDS_FROM_TICKETS_BY_THE_MOVIEID_REQUEST("SELECT * FROM tickets WHERE movieID=?"),
    GET_TICKETS_ID_FROM_TICKETS_BY_THE_USERNAME_REQUEST("SELECT ticketID FROM tickets WHERE userName=?"),
    UPDATE_THE_USERNAME_FIELD_INTO_THE_TICKET("UPDATE tickets SET userName=? WHERE ticketID=?"),

    //Movie
    SELECT_DATE_TIME_FROM_THE_MOVIE_WHERE_TITLE("SELECT dateTime FROM movie WHERE movieTitle=?"),
    SELECT_TITLES_OF_MOVIES("SELECT movieTitle FROM movie"),
    SELECT_MOVIEID_FROM_MOVIE_WHERE_DATETIME("SELECT movieID FROM movie WHERE dateTime=?");

    private String script;

    ScriptConstant(String script) {
        this.script = script;
    }

    public String getScript() {
        return script;
    }
}
