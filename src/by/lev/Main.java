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
    public static void main(String[] args) {
        UserDaoInterface<User, String> usDao = new UserDao();
        UserServiceInterface usServ = new UserService(usDao);
        TicketDaoInterface<Ticket, Integer> tickDao = new TicketDao();
        TicketServiceInterface tickServ = new TicketService(tickDao);
        MovieDaoInterface<Movie, Integer> movDao = new MovieDao();
        MovieServiceInterface movServ = new MovieService(movDao);

        RegistrationInterface registration = new Registration(usServ);

        UserControllerInterface usCont = new UserController(usServ, tickServ, movServ);
        ManagerControllerInterface manCont = new ManagerController(usServ, tickServ, movServ);
        AdministratorControllerInterface adCont  =new AdministratorController(usServ, tickServ, movServ);

        EntranceInterface entrance = new Entrance(usServ, usCont, manCont, adCont, registration);
        entrance.start();

    }
}
