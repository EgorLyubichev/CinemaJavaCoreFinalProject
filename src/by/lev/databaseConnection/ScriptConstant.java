package by.lev.databaseConnection;

public enum ScriptConstant {

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
