package by.lev.controller;

public interface UserControllerInterface {
    void showUserMenu();
    void showUpcomingSessions();
    void buyATicket();
    void showUserTickets();
    boolean checkTicketNumberInTheUserCollection(int ticketID);
    void cancelTheTicket();
    void changePassword();
}
