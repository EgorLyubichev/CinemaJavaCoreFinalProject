package by.lev.ticket;

public class Ticket {
    private int ticketID;
    private String userName;
    private int movieID;
    private int place;
    private int cost;
    boolean bought;

    public Ticket() {
        if (userName != null) {
            bought = true;
        }
    }

    public Ticket(String userName, int movieID, int place, int cost) {
        this.userName = userName;
        this.movieID = movieID;
        this.place = place;
        this.cost = cost;
        if (userName != null) {
            bought = true;
        }
    }

    public Ticket(int ticketID, String userName, int movieID, int place, int cost) {
        this.ticketID = ticketID;
        this.userName = userName;
        this.movieID = movieID;
        this.place = place;
        this.cost = cost;
        if (userName != null) {
            bought = true;
        }
    }

    public int getTicketID() {
        return ticketID;
    }

    public void setTicketID(int ticketID) {
        this.ticketID = ticketID;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public int getMovieID() {
        return movieID;
    }

    public void setMovieID(int movieID) {
        this.movieID = movieID;
    }

    public int getPlace() {
        return place;
    }

    public void setPlace(int place) {
        this.place = place;
    }

    public int getCost() {
        return cost;
    }

    public void setCost(int cost) {
        this.cost = cost;
    }

    public boolean isBought() {
        return bought;
    }

    public String userNameToString() {
        if (userName == null) {
            return "не назначен";
        }
        return userName;
    }


    public String boughtToString() {
        if (bought == false) {
            return "не приобретен";
        } else {
            return "приобретен";
        }
    }

    @Override
    public String toString() {
        return "Билет №" + ticketID +
                ", пользователь: " + userNameToString() +
                ", номер фильма: " + movieID +
                ", место: " + place +
                ", цена: " + cost +
                " | " + boughtToString();
    }
}