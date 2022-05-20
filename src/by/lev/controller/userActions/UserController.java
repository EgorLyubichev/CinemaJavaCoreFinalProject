package by.lev.controller.userActions;

import static by.lev.controller.AuthorizationFunctional.USER_MAIN;

import by.lev.controller.menu.UserMenu;
import by.lev.exceptions.MovieException;
import by.lev.exceptions.TicketException;
import by.lev.movie.Movie;
import by.lev.movie.MovieDAO;
import by.lev.movie.MovieDateTimeComporator;
import by.lev.ticket.Ticket;
import by.lev.ticket.TicketDAO;

import static by.lev.controller.controllerFunctions.ControllerFunction.*;
import static by.lev.databaseConnection.AbstractConnection.*;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class UserController {

    public void seeExcitingMovies() {
        List<Movie> futureMovie = new ArrayList<>();
        List<Movie> allMovies = null;
        try {
            allMovies = new MovieDAO().readAll();
        } catch (MovieException e) {
            e.getMessage();
        }
        allMovies.sort(new MovieDateTimeComporator());
        for (Movie movie : allMovies) {
            if (movie.getDateTime().after(Timestamp.valueOf(LocalDateTime.now()))) {
                StringBuilder strB = new StringBuilder("Фильм: ").append(movie.getTitle()).append("\nсеанс: ")
                        .append(movie.getDateTime().toString().substring(0, 16)).append("\n- - - - - - - - - - - -");
                System.out.println(strB);
            }
        }
    }

    public static class BuyingATicket {
        Movie movie = new Movie();
        Ticket ticket = new Ticket();
        List<Ticket> freeTickets;
        List<Timestamp> timestampList;

        public void buyATicket() {
            showExcitingSessionsOfTheMovie();
            showFreeTicketsOfTheSession();
            chooseATicket();
            buyingTheTicket();
        }

        public void showExcitingSessionsOfTheMovie() {
            System.out.println("Введите название фильма...");
            String title = scanString();
            System.out.println("Предстоящие сеансы:");
            List<Timestamp> datesOfMovie = new ArrayList<>();
            try {
                Connection connection = getConnection();
                PreparedStatement prs = connection.prepareStatement("SELECT dateTime FROM movie WHERE movieTitle=?");
                prs.setString(1, title.toUpperCase());
                ResultSet rs = prs.executeQuery();
                while (rs.next()) {
                    Timestamp timestamp = rs.getTimestamp("dateTime");
                    if (timestamp.after(Timestamp.valueOf(LocalDateTime.now()))) {
                        datesOfMovie.add(timestamp);
                    }
                }
                timestampList = datesOfMovie;
            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
                try {
                    closeConnection();
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
            if (datesOfMovie.isEmpty()) {
                System.out.println("\tна данный фильм сеансов пока не назначено");
                new UserMenu().showUserMenu();
            } else {
                movie.setTitle(title);

                int count = 1;
                for (int i = 0; i < datesOfMovie.size(); i++, count++) {
                    System.out.println("<" + count + "> - " + datesOfMovie.get(i).toString().substring(0, 16));
                }
            }
        }

        public void showFreeTicketsOfTheSession() {
            System.out.println("Выберите сеанс из предложенных...");
            boolean correctInput = false;
            while (correctInput == false) {
                int choice = scanInt();
                if (choice > 0 && choice <= timestampList.size()) {
                    correctInput = true;
                } else {
                    System.out.println("Неверный запрос! Повторите попытку.");
                }
                movie.setDateTime(timestampList.get(choice - 1));
            }
            System.out.println("Имеющиеся билеты:");
            List<Ticket> freeTickets = new ArrayList<>();
            try {
                Connection connection = getConnection();
                PreparedStatement prs1 = connection.prepareStatement("SELECT movieID FROM movie WHERE dateTime=?");
                prs1.setTimestamp(1, movie.getDateTime());
                ResultSet rs1 = prs1.executeQuery();
                while (rs1.next()) {
                    movie.setMovieID(rs1.getInt("movieID"));
                }
                PreparedStatement prs = connection.prepareStatement(
                        "SELECT * FROM tickets WHERE movieID=?"
                );
                prs.setInt(1, movie.getMovieID());
                ResultSet rs = prs.executeQuery();
                while (rs.next()) {
                    Ticket ticket = new Ticket();
                    ticket.setTicketID(rs.getInt("ticketID"));
                    ticket.setUserName(rs.getString("userName"));
                    ticket.setPlace(rs.getInt("place"));
                    ticket.setCost(rs.getInt("cost"));
                    if (ticket.getUserName() == null) {
                        freeTickets.add(ticket);
                    }
                }
                this.freeTickets = freeTickets;
            } catch (SQLException e) {
                e.printStackTrace();
            }
            if (freeTickets.isEmpty()) {
                System.out.println("\tк сожалению, на данный сеанс свободных билетов не осталось");
                new UserMenu().showUserMenu();
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

        public void chooseATicket() {
            List<Integer> freeTicketNumbers = new ArrayList<>();
            System.out.println("Выберите нужный билет... (№ билета)");
            int choice = scanInt();
            for (Ticket freeTicket : freeTickets) {
                freeTicketNumbers.add(freeTicket.getTicketID());
            }
            if (freeTicketNumbers.contains(choice)) {
                try {
                    ticket = new TicketDAO().read(choice);
                    System.out.println("Билет " + ticket.getTicketID() + " выбран!");
                } catch (TicketException e) {
                    throw new RuntimeException(e);
                }
            } else {
                System.out.println("Неверный ввод данных");
                chooseATicket();
            }
        }

        public void buyingTheTicket() {
            System.out.println("Стоимость билета " + ticket.getCost() + "$");
            System.out.println("<1> - оплатить билет\n<2> - вернуться в основное меню");
            int choice = scanInt();
            switch (choice) {
                case 1:
                    try {
                        new TicketDAO().update(ticket.getTicketID(), USER_MAIN);
                    } catch (TicketException e) {
                        throw new RuntimeException(e);
                    }
                    System.out.println("Билет оплачен!");
                    System.out.println("<1> - купить новый билет\n<2> - перейти в основное меню");
                    boolean inputCorrect = false;
                    while (inputCorrect == false) {
                        int choice2 = scanInt();
                        if (choice2 == 1) {
                            inputCorrect = true;
                            buyATicket();
                        } else if (choice2 == 2) {
                            inputCorrect = true;
                            new UserMenu().showUserMenu();
                        } else {
                            System.out.println("Неверный ввод данных");
                            inputCorrect = false;
                        }
                    }
                    break;
                case 2:
                    new UserMenu().showUserMenu();
                    break;
                default:
                    System.out.println("Неверный ввод данных!");
                    buyingTheTicket();
            }
        }
    }

    public void showUserTickets() {
        List<Ticket> userTickets;
        Movie movie;
        try {
            userTickets = new TicketDAO().readAll(USER_MAIN);
        } catch (TicketException e) {
            throw new RuntimeException(e);
        }
        for (Ticket ticket : userTickets) {
            int movieID = ticket.getMovieID();
            try {
                movie = new MovieDAO().read(movieID);
                StringBuilder strB = new StringBuilder("Билет № ");
                strB.append(ticket.getTicketID()).append(" | ").append(movie.getTitle()).append(" | ")
                        .append(movie.getDateTime().toString().substring(0, 16)).append(" | владелец: ").append(USER_MAIN.getLogin()).append(" | место: ")
                        .append(ticket.getPlace()).append(" | цена: ").append(ticket.getCost()).append("$");
                System.out.println(strB);
            } catch (MovieException e) {
                throw new RuntimeException(e);
            }

        }
    }

    public void cancelTheTicket() {
        System.out.println("Ваши билеты:");
        showUserTickets();
        System.out.println("Введите номер билета для отмены...");
        int choice = scanInt();
        try {
            Connection connection = getConnection();
            PreparedStatement prs = connection.prepareStatement(
                    "UPDATE tickets SET userName=? WHERE ticketID=?");
            prs.setString(1, null);
            prs.setInt(2, choice);
            prs.execute();

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                closeConnection();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        System.out.println("Ваш запрос выполнен!");
        System.out.println();
        System.out.println("Ваши билеты:");
        showUserTickets();
        System.out.println();
    }
}