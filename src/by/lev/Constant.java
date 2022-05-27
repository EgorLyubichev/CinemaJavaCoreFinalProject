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
    USER_DELETE("DELETE FROM users WHERE login=?");

    private String message;

    Constant(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
