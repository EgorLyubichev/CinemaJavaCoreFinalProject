package by.lev;

import by.lev.controller.*;
import by.lev.movie.Movie;
import by.lev.movie.MovieDao;
import by.lev.movie.MovieDaoInterface;
import by.lev.service.*;
import by.lev.ticket.Ticket;
import by.lev.ticket.TicketDao;
import by.lev.ticket.TicketDaoInterface;
import by.lev.user.User;
import by.lev.user.UserDao;
import by.lev.user.UserDaoInterface;


public class Main {

    public static UserDaoInterface<User, String> usDao;
    public static UserServiceInterface usServ;
    public static TicketDaoInterface<Ticket, Integer> tickDao;
    public static TicketServiceInterface tickServ;
    public static MovieDaoInterface<Movie, Integer> movDao;
    public static MovieServiceInterface movServ;
    public static RegistrationInterface registration;
    public static UserControllerInterface usCont;
    public static ManagerControllerInterface manCont;
    public static AdministratorControllerInterface adCont;
    public static Entrance entrance;

    public static void main(String[] args) {
        {
            usDao = new UserDao();
            usServ = new UserService(usDao);
            tickDao = new TicketDao();
            tickServ = new TicketService(tickDao);
            movDao = new MovieDao();
            movServ = new MovieService(movDao);
            registration = new Registration(usServ);
            usCont = new UserController(usServ, tickServ, movServ);
            manCont = new ManagerController(usServ, tickServ, movServ);
            adCont = new AdministratorController(usServ, tickServ, movServ);
            entrance = new Entrance(usServ, usCont, manCont, adCont, registration);
        }

        entrance.start();
    }
}
