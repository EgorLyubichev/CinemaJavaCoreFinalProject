package by.lev.databaseConnection;

public enum ScriptConstant {

    //Movie


    SELECT_MOVIEID_FROM_MOVIE_WHERE_DATETIME("SELECT movieID FROM movie WHERE dateTime=?");

    private String script;

    ScriptConstant(String script) {
        this.script = script;
    }

    public String getScript() {
        return script;
    }
}
