package by.lev.user;

import java.util.Objects;

import static by.lev.user.UsersAccessLevel.USER;

public class User {
    protected int userID;
    protected String login;
    protected String password;
    private UsersAccessLevel level = USER;

    public User() {
    }

    public User(String login, String password) {
        this.login = login;
        this.password = password;
    }

    public User(int userID, String login, String password) {
        this.userID = userID;
        this.login = login;
        this.password = password;
    }

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public UsersAccessLevel getLevel() {
        return level;
    }

    protected void setLevel(String level) {
        this.level = UsersAccessLevel.valueOf(level);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(login, user.login) && Objects.equals(password, user.password) && level == user.level;
    }

    @Override
    public int hashCode() {
        return Objects.hash(login, password, level);
    }

    @Override
    public String toString() {
        return "User{" +
                "userID=" + userID +
                ", login='" + login + '\'' +
                ", password='" + password + '\'' +
                ", level=" + level +
                '}';
    }
}
