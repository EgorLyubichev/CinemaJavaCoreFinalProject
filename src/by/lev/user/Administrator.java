package by.lev.user;

import static by.lev.user.UsersAccessLevel.ADMINISTRATOR;

public class Administrator extends Manager {
    private final UsersAccessLevel level = ADMINISTRATOR;

    public Administrator(String login, String password) {
        super(login, password);
    }
    public UsersAccessLevel getLevel() {
        return level;
    }
}
