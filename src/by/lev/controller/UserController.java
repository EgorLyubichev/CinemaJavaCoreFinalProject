package by.lev.controller;

import static by.lev.Constant.INCORRECT_INPUT;
import static by.lev.controller.Entrance.USER_ONLINE;

import by.lev.encoder.Base64encoder;
import by.lev.movie.Movie;
import by.lev.service.*;
import by.lev.ticket.Ticket;

import static by.lev.controller.InputFunction.*;
import static by.lev.logger.Logger.*;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserController implements UserControllerInterface {
    protected UserServiceInterface usServ;
    protected TicketServiceInterface tickServ;
    protected MovieServiceInterface movServ;

    public UserController(UserServiceInterface usServ,
                          TicketServiceInterface tickServ,
                          MovieServiceInterface movServ) {
        this.usServ = usServ;
        this.tickServ = tickServ;
        this.movServ = movServ;
    }

    Movie movie = new Movie();
    Ticket ticket = new Ticket();
    List<Ticket> freeTickets;
    List<Timestamp> timestampList = new ArrayList<>();

    public void showUserMenu() {
        writeAction(" меню пользователя");
        System.out.println("- - -|  CACTUS CINEMA  |- - -");
        System.out.println("- - -| ГЛАВНАЯ СТРАНИЦА|- - -");
        System.out.println("<1> - предстоящие фильмы");
        System.out.println("<2> - купить билет");
        System.out.println("<3> - купленные билеты");
        System.out.println("<4> - отменить билет");
        System.out.println("<5> - сменить пароль");
        System.out.println("<0> - выход из программы");

        int inputValue = new InputCorrectness().inputCorrectValueFromZeroToFive();
        if (inputValue == -1){
            System.out.println(INCORRECT_INPUT.getMessage());
            System.out.println();
            showUserMenu();
        }
        switch (inputValue) {
            case 1:
                writeAction(" предстоящие фильмы");
                showUpcomingSessions();
                System.out.println();
                showUserMenu();
                break;
            case 2:
                writeAction(" покупка билетов");
                buyATicket();
                break;
            case 3:
                writeAction(" список собственных билетов");
                showUserTickets();
                showUserMenu();
                break;
            case 4:
                cancelTheTicket();
                showUserMenu();
                break;
            case 5:
                writeAction(" вкладка 'изменить пароль'");
                changePassword();
                showUserMenu();
                break;
            case 0:
                writeExit();
                System.exit(0);
        }
    }

    public void showUpcomingSessions() {
        List<Movie> upcomingMovies = movServ.getUpcomingMovies();
        for (Movie movie : upcomingMovies) {
            StringBuilder strB = new StringBuilder(movie.getDateTime().toString().substring(0, 16))
                    .append(" | ").append(movie.getTitle())
                    .append("\n- - - - - - - - - - - - - - - - - -");
            System.out.println(strB);
        }
    }

    public void buyATicket() {
        showUpcomingSessionsOfTheMovie();
        showFreeTicketsOfTheSession();
        chooseATicket();
        buyingTheTicket();
    }

    private void showUpcomingSessionsOfTheMovie() {
        System.out.println("Введите название фильма...");
        String title = scanString();
        title = title.toUpperCase().trim();
        if (movServ.checkIfTitleListContainsTheTitle(title)) {
            System.out.println("Предстоящие сеансы:");
            List<Timestamp> movieSessions = movServ.getUpcomingTimestampsOfTheMovie(title);
            if (movieSessions.isEmpty()) {
                System.out.println("\tна данный фильм сеансов пока не назначено");
            } else {
                movie.setTitle(title);
                int count = 1;
                for (int i = 0; i < movieSessions.size(); i++, count++) {
                    System.out.println("<" + count + "> - " + movieSessions.get(i).toString().substring(0, 16));
                }
            }
            timestampList = movieSessions;
        } else {
            System.out.println("операция прервана: заданного названия в списке имеющихся фильмов нет");
            showUserMenu();
        }
    }

    private void showFreeTicketsOfTheSession() {
        System.out.println("Выберите сеанс из предложенных...");
        boolean correctInput = false;
        while (!correctInput) {
            int choice = scanInt();
            if (choice > 0 && choice <= timestampList.size()) {
                movie.setDateTime(timestampList.get(choice - 1));
                correctInput = true;
            } else {
                System.out.println("Неверный запрос! Повторите попытку.");
                correctInput = false;
            }
        }
        System.out.println("Имеющиеся билеты:");
        movie.setMovieID(movServ.getMovieIdOnTheDateTimeRequest(movie.getDateTime()));
        freeTickets = tickServ.getFreeTicketsOfTheSession(movie.getMovieID());
        if (freeTickets.isEmpty()) {
            System.out.println("\tк сожалению, на данный сеанс свободных билетов не осталось");
            showUserMenu();
        } else {
            for (Ticket ticket : freeTickets) {
                String dateForViewing = movie.getDateTime().toString().substring(0, 16);
                StringBuilder strB = new StringBuilder(dateForViewing);
                strB.append(" | ").append(movie.getTitle()).append(" | № билета: ").
                        append(ticket.getTicketID()).append(" | место: ").append(ticket.getPlace()).
                        append(" | ").append("цена билета: ").append(ticket.getCost()).append("$");
                System.out.println(strB);
            }
        }
    }

    private void chooseATicket() {
        List<Integer> freeTicketNumbers = new ArrayList<>();
        for (Ticket freeTicket : freeTickets) {
            freeTicketNumbers.add(freeTicket.getTicketID());
        }
        System.out.println("Выберите нужный билет... (№ билета)");
        int choice = scanInt();
        if (freeTicketNumbers.contains(choice)) {
            ticket = tickServ.getTicket(choice);
            System.out.println("Билет " + ticket.getTicketID() + " выбран!");
        } else {
            System.out.println("Неверный ввод данных");
            chooseATicket();
        }
    }

    private void buyingTheTicket() {
        System.out.println("Стоимость билета " + ticket.getCost() + "$");
        System.out.println("<1> - оплатить билет\n<2> - вернуться в основное меню");
        int choice = scanInt();
        switch (choice) {
            case 1:
                tickServ.assignTheUserInTheTicket(ticket.getTicketID(), USER_ONLINE);
                System.out.println("Билет оплачен!");
                writeBuyingATicketOfTheUser(ticket.getTicketID(), movie.getTitle(), movie.getDateTime());
                System.out.println("<1> - купить новый билет\n<2> - перейти в основное меню");
                boolean inputCorrect = false;
                while (!inputCorrect) {
                    int choice2 = scanInt();
                    if (choice2 == 1) {
                        inputCorrect = true;
                        buyATicket();
                    } else if (choice2 == 2) {
                        inputCorrect = true;
                        showUserMenu();
                    } else {
                        System.out.println("Неверный ввод данных");
                    }
                }
                break;
            case 2:
                showUserMenu();
                break;
            default:
                System.out.println("Неверный ввод данных!");
                buyingTheTicket();
        }
    }

    public void showUserTickets() {
        List<Ticket> userTickets;
        Movie movie;
        userTickets = tickServ.getUserTickets(USER_ONLINE);
        for (Ticket ticket : userTickets) {
            int movieID = ticket.getMovieID();
            movie = movServ.getMovie(movieID);
            StringBuilder strB = new StringBuilder("Билет № ");
            strB.append(ticket.getTicketID()).append(" | ").append(movie.getTitle()).append(" | ")
                    .append(movie.getDateTime().toString().substring(0, 16)).append(" | владелец: ")
                    .append(USER_ONLINE.getLogin()).append(" | место: ").append(ticket.getPlace())
                    .append(" | цена: ").append(ticket.getCost()).append("$");
            System.out.println(strB);
        }
    }

    private boolean checkTicketNumberInTheUserCollection(int ticketID) {
        List<Integer> userTicketIdList = tickServ.getTicketNumbersOfUser(USER_ONLINE);
        if (userTicketIdList.contains(ticketID)) {
            return true;
        }
        return false;
    }

    public void cancelTheTicket() {
        System.out.println("Ваши билеты:");
        showUserTickets();
        System.out.println("Введите номер билета для отмены...");
        int choice = scanInt();
        if (checkTicketNumberInTheUserCollection(choice)) {
            tickServ.removeUsernameFromTicket(choice);
            writeCancellingOfTheUserTicket(choice);
            System.out.println("Ваш запрос выполнен!");
        } else {
            System.out.println("Введенного номера билета в списке ваших не имеется!");
        }
        System.out.println();
        System.out.println("Ваши билеты:");
        showUserTickets();
        System.out.println();
    }

    public void changePassword() {
        System.out.println("введите свой пароль...");
        String oldPassword = scanString();
        String encodeOldPassword = new Base64encoder().getEncode(oldPassword);
        if (!USER_ONLINE.getPassword().equals(encodeOldPassword)) {
            System.out.println("в операции отказано: вы ввели неверный пароль");
            showUserMenu();
        }
        System.out.println("введите новый пароль...");
        String newPassword = scanString();
        while (!checkTheCorrectnessOfThePasswordInput(newPassword)) {
            newPassword = scanString();
        }
        System.out.println("повторите пароль...");
        String repeatPassword = scanString();
        if (!newPassword.equals(repeatPassword)) {
            System.out.println("введенные пароли не совпадают");
            writeAction(" пароль не изменен, несовпадение ввода данных");
            showUserMenu();
        }
        String encodeNewPassword = new Base64encoder().getEncode(newPassword);
        usServ.updatePassword(USER_ONLINE.getLogin(), encodeNewPassword);
        System.out.println("пароль успешно обновлен");
        writeAction(" пароль изменен успешно");
    }
}
