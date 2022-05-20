package by.lev.user;

import static by.lev.user.UsersAccessLevel.MANAGER;

public class Manager extends User{
    private final UsersAccessLevel level = MANAGER;

    public Manager(String login, String password) {
        super(login, password);
    }
    public UsersAccessLevel getLevel() {
        return level;
    }
}
