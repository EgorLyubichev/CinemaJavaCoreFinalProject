package by.lev.movie;

import by.lev.ticket.Ticket;

import java.sql.Timestamp;
import java.util.List;

public class Movie {
    private int movieID;
    private String title;
    private Timestamp dateTime;
    private List<Ticket> ticketList;

    public Movie() {
    }

    public Movie(String title, String dateTime) {
        this.title = title;
        if(dateTime.length() == 16){
            String dateTime2 = dateTime + ":00";
            this.dateTime = Timestamp.valueOf(dateTime2);
        }else{this.dateTime = Timestamp.valueOf(dateTime);}
    }

    public Movie(int movieID, String title, String dateTime) {
        this.movieID = movieID;
        this.title = title;
        if(dateTime.length() == 16){
            String dateTime2 = dateTime + ":00";
            this.dateTime = Timestamp.valueOf(dateTime2);
        }else{this.dateTime = Timestamp.valueOf(dateTime);}
    }

    public int getMovieID() {
        return movieID;
    }

    public void setMovieID(int movieID) {
        this.movieID = movieID;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setTicketList(List<Ticket> ticketList) {
        this.ticketList = ticketList;
    }

    public Timestamp getDateTime() {
        return dateTime;
    }

    public void setDateTime(Timestamp dateTime) {
        this.dateTime = dateTime;
    }

    public List<Ticket> getTicketList() {
        return ticketList;
    }


    @Override
    public String toString() {
        return "уч.запись №" + movieID +
                " | фильм:  " + title + " | " + showDateTimeWithoutSeconds() + " " +
                showTicketList() + "\n";
    }

    private String showTicketList() {
        if(ticketList.isEmpty()){
            return "\n\tбилеты не были добавлены!\n";
        }else{
        StringBuilder stb = new StringBuilder("Билеты:\n");
        for (Ticket ticket : ticketList) {
            stb.append("\t" + ticket.toString() + "\n");
        }
        return stb.toString();}
    }

    private String showDateTimeWithoutSeconds(){
        return dateTime.toString().substring(0,16);
    }
}