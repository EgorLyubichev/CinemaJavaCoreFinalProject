package by.lev.controller;

public interface ManagerControllerInterface {
    void showManagerMenu();
    void showMovieList();
    void showUpcomingSessions();
    void showUserList();
    void buyATicketForUser();
    void showTheUserTickets();
    void cancelTheTicket();
    void checkLoginInBase(String login);
    void checkIfTheTicketIsFree(int ticketID);
    void checkIfTheTicketIsOfTheUser(int ticketID, String username);
    void changeMovieTitle();
    void changeMovieSession();
}
