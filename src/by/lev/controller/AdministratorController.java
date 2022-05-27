package by.lev.controller;

import by.lev.encoder.Base64encoder;
import by.lev.service.InputCorrectness;
import by.lev.service.MovieService;
import by.lev.service.TicketService;
import by.lev.service.UserService;
import by.lev.movie.Movie;
import by.lev.ticket.Ticket;
import by.lev.user.Manager;
import by.lev.user.User;

import java.sql.Timestamp;
import java.util.List;

import static by.lev.Constant.INCORRECT_INPUT;
import static by.lev.controller.InputFunction.*;

public class AdministratorController extends ManagerController {
    Movie movie = new Movie();

    public void showAdminMenu() {
        System.out.println("- - -|      CACTUS CINEMA      |- - -");
        System.out.println("- - -| СТРАНИЦА АДМИНИСТРАТОРА |- - -");
        System.out.println("<1> - операции с фильмами");
        System.out.println("<2> - операции с билетами");
        System.out.println("<3> - операции с пользователями");
        System.out.println("<0> - выход из программы");

        int inputValue = new InputCorrectness().inputCorrectValueFromZeroToThree();
        if (inputValue == -1){
            System.out.println(INCORRECT_INPUT.getMessage());
            System.out.println();
            showAdminMenu();
        }

        switch (inputValue) {
            case 1:
                showMovieOperations();
                break;
            case 2:
                showTicketOperations();
                break;
            case 3:
                showUserOperations();
                break;
            case 0:
                System.exit(0);
        }
    }

    public void showMovieOperations() {
        System.out.println("- - -|      CACTUS CINEMA      |- - -");
        System.out.println("- - -| СТРАНИЦА АДМИНИСТРАТОРА |- - -");
        System.out.println("- - -|   ОПЕРАЦИИ С ФИЛЬМАМИ   |- - -");
        System.out.println("<1> - посмотреть весь список");
        System.out.println("<2> - посмотреть список предстоящих сеансов");
        System.out.println("<3> - редактировать название фильма по номеру учетной записи");
        System.out.println("<4> - редактировать дату и время сеанса по номеру учетной записи");
        System.out.println("<5> - добавить новый сеанс");
        System.out.println("<6> - удалить сеанс");

        System.out.println("<0> - назад");

        int inputValue = new InputCorrectness().inputCorrectValueFromZeroToSix();
        if(inputValue == -1){
            System.out.println(INCORRECT_INPUT.getMessage());
            System.out.println();
            showMovieOperations();
        }

        switch (inputValue) {
            case 1:
                new AdministratorController().showMovieList();
                System.out.println();
                showMovieOperations();
                break;
            case 2:
                new AdministratorController().showUpcomingSessions();
                System.out.println();
                showMovieOperations();
                break;
            case 3:
                new AdministratorController().changeMovieTitle();
                showMovieOperations();
                break;
            case 4:
                new AdministratorController().changeMovieSession();
                showMovieOperations();
                break;
            case 5:
                new AdministratorController().addNewMovieAccount();
                showMovieOperations();
                break;
            case 6:
                new AdministratorController().deleteMovieAccount();
                showAdminMenu();
                break;
            case 0:
                showAdminMenu();
        }
    }

    public void showTicketOperations() {
        System.out.println("- - -|      CACTUS CINEMA      |- - -");
        System.out.println("- - -| СТРАНИЦА АДМИНИСТРАТОРА |- - -");
        System.out.println("- - -|   ОПЕРАЦИИ С БИЛЕТАМИ   |- - -");
        System.out.println("<1> - купить билет для пользователя");
        System.out.println("<2> - посмотреть билеты пользователя");
        System.out.println("<3> - отменить билет для пользователя");

        System.out.println("<0> - назад");

        int inputValue = new InputCorrectness().inputCorrectValueFromZeroToThree();
        if (inputValue == -1){
            System.out.println(INCORRECT_INPUT.getMessage());
            System.out.println();
            showTicketOperations();
        }

        switch (inputValue) {
            case 1:
                new AdministratorController().buyATicketForUser();
                System.out.println();
                showTicketOperations();
                break;
            case 2:
                new AdministratorController().showUserTickets();
                System.out.println();
                showTicketOperations();
                break;
            case 3:
                new AdministratorController().cancelTheTicket();
                showTicketOperations();
                break;
            case 0:
                showAdminMenu();
        }
    }

    public void showUserOperations() {
        System.out.println("- - -|      CACTUS CINEMA      |- - -");
        System.out.println("- - -| СТРАНИЦА АДМИНИСТРАТОРА |- - -");
        System.out.println("- - -|ОПЕРАЦИИ С ПОЛЬЗОВАТЕЛЯМИ|- - -");
        System.out.println("<1> - список пользователей");
        System.out.println("<2> - изменить пароль для пользователя");
        System.out.println("<3> - добавить нового пользователя");
        System.out.println("<4> - удалить пользователя и все его данные");
        System.out.println("<5> - создать учетную запись для мэнеджера");

        System.out.println("<0> - назад");

        int inputValue = new InputCorrectness().inputCorrectValueFromZeroToFive();
        if (inputValue == -1){
            System.out.println(INCORRECT_INPUT.getMessage());
            System.out.println();
            showUserOperations();
        }
        switch (inputValue) {
            case 1:
                new AdministratorController().showUserList();
                showUserOperations();
                break;
            case 2:
                new AdministratorController().changePassword();
                showUserOperations();
                break;
            case 3:
                new Registration().createNewUser();
                showUserOperations();
                break;
            case 4:
                new AdministratorController().deleteTheUser();
                showUserOperations();
                break;
            case 5:
                new AdministratorController().createManagerAccount();
                showAdminMenu();
            case 0:
                showAdminMenu();
        }
    }

    public void addNewMovieAccount() {
        createNewMovie();
        createMovieTickets();
        System.out.println("уч.запись для фильма '" + movie.getTitle().toUpperCase() +
                "' на дату " + movie.getDateTime().toString() + " со списком билетов успешно добавлена");
    }

    public void createNewMovie() {
        Movie movieToLoad = new Movie();
        System.out.println("Введите название фильма...");
        String title = scanString();
        if (title.length() < 1 || title.equals(" ")) {
            System.out.println("операция прервана: некорректное название фильма");
            new AdministratorController().showMovieOperations();
        }
        movieToLoad.setTitle(title.toUpperCase());
        System.out.println("Введите дату и время показа фильма...");
        String sessionDate = scanString();
        while (!new MovieService().inputCorrectDateTimeFormat(sessionDate)) {
            System.out.println("Формат даты должен быть: 'гггг-мм-дд чч:мм' или 'гггг-мм-дд чч:мм:сс'");
            sessionDate = scanString();
        }
        if (sessionDate.length() == 16) {
            sessionDate = sessionDate + ":00";
        }
        movieToLoad.setDateTime(Timestamp.valueOf(sessionDate));
        if (!new MovieService().checkActualityOfTheTime(sessionDate)) {
            System.out.println("операция прервана: данная дата уже прошла");
            new AdministratorController().showMovieOperations();
        }
        if (new MovieService().isTheSlotOfThisDateTimeOccuped(sessionDate)) {
            System.out.println("операция прервана: на данную дату уже запланирован другой сеанс");
            new AdministratorController().showMovieOperations();
        }
        new MovieService().addMovie(movieToLoad);
        movie = new MovieService().getMovie(movieToLoad.getTitle());
    }

    public void createMovieTickets() {
        Ticket ticket = new Ticket();
        System.out.println("Введите цену билета для данного сеанса...");
        int price = scanInt();
        if (price < 0) {
            System.out.println("операция прервана: указана некорректная цена билета");
            new AdministratorController().showMovieOperations();
        }
        int count = 1;
        for (int i = 0; i < 10; i++, count++) {
            ticket.setMovieID(movie.getMovieID());
            ticket.setPlace(count);
            ticket.setCost(price);
            new TicketService().addTicket(ticket);
        }
    }

    public void deleteMovieAccount() {
        System.out.println("Введите № уч.записи для удаления...");
        int movieID = scanInt();
        movie = new MovieService().getMovie(movieID);
        if (movie.getTitle() == null) {
            System.out.println("операция прервана: уч.записи с данным номером не найдено");
            new AdministratorController().showMovieOperations();
        }
        new TicketService().removeTicket(movie);
        new MovieService().removeMovie(movieID);
        System.out.println("уч.запись №" + movieID + " удалена");
    }

    public void changePassword() {
        System.out.println("введите логин пользователя для смены пароля...");
        String login = scanString();
        User user = new UserService().getUser(login);
        if (user.getPassword() == null) {
            System.out.println("операция прервана: такого пользователя нет в базе");
        }
        System.out.println("введите новый пароль...");
        String newPassword = scanString();
        while (!new Registration().checkTheCorrectnessOfThePasswordInput(newPassword)) {
            newPassword = scanString();
        }
        System.out.println("повторите пароль...");
        String repeatPassword = scanString();
        if (!newPassword.equals(repeatPassword)) {
            System.out.println("введенные пароли не совпадают");
            new AdministratorController().showUserOperations();
        }
        new UserService().updatePassword(login, newPassword);
        System.out.println("пароль успешно обновлен");

    }

    public void deleteTheUser() {
        System.out.println("введите логин пользователя для удаления...");
        String login = scanString();
        User user = new UserService().getUser(login);
        if (user.getPassword() == null) {
            System.out.println("операция прервана: пользователя с логином " + login + " в базе нет");
            new AdministratorController().showUserMenu();
        }
        new UserService().removeUser(login);
        System.out.println("пользователь " + login + " удален");
        List<Integer> userTicketNumbers = new TicketService().getTicketNumbersOfUser(user);
        String positiveResault = "данные пользователя " + login + " успешно удалены";
        if (userTicketNumbers.isEmpty()) {
            System.out.println(positiveResault);
        } else {
            for (Integer value : userTicketNumbers) {
                new TicketService().removeUsernameFromTicket(value);
            }
            System.out.println(positiveResault);
        }
    }

    public void createManagerAccount() {
        System.out.println("Создайте логин для уч.записи мэнеджера...");
        String login = scanString();
        while(!new Registration().checkTheCorrectnessOfTheLoginInput(login)){
            login = scanString();
        }
        System.out.println("Создайте пароль для уч.записи мэнеджера...");
        String password = scanString();
        while(!new Registration().checkTheCorrectnessOfThePasswordInput(password)){
            password = scanString();
        }
        System.out.println("Повторите пароль...");
        String secondPassword = scanString();
        if (!password.equals(secondPassword)){
            System.out.println("операция прервана: пароли не совпадают");
            new AdministratorController().showUserOperations();
        }
        String encodedPassword = new Base64encoder().getEncode(password);
        User manager = new Manager(login, encodedPassword);
        new UserService().addUser(manager);
        System.out.println("Новая учетная запись для мэнеджера " + login + " создана!");
        System.out.println();
    }
}
