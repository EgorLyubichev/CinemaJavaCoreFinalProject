package by.lev.controller;

import by.lev.encoder.Base64encoder;
import by.lev.service.*;
import by.lev.movie.Movie;
import by.lev.ticket.Ticket;
import by.lev.user.Manager;
import by.lev.user.User;

import java.sql.Timestamp;
import java.util.List;

import static by.lev.Constant.INCORRECT_INPUT;
import static by.lev.controller.InputFunction.*;

public class AdministratorController extends ManagerController implements AdministratorControllerInterface {
    Movie movie = new Movie();

    public AdministratorController(UserServiceInterface usServ,
                                   TicketServiceInterface tickServ,
                                   MovieServiceInterface movServ) {
        super(usServ, tickServ, movServ);
    }

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
                showMovieList();
                System.out.println();
                showMovieOperations();
                break;
            case 2:
                showUpcomingSessions();
                System.out.println();
                showMovieOperations();
                break;
            case 3:
                changeMovieTitle();
                showMovieOperations();
                break;
            case 4:
                changeMovieSession();
                showMovieOperations();
                break;
            case 5:
                addNewMovieAccount();
                showMovieOperations();
                break;
            case 6:
                deleteMovieAccount();
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
                buyATicketForUser();
                System.out.println();
                showTicketOperations();
                break;
            case 2:
                showUserTickets();
                System.out.println();
                showTicketOperations();
                break;
            case 3:
                cancelTheTicket();
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
                showUserList();
                showUserOperations();
                break;
            case 2:
                changePassword();
                showUserOperations();
                break;
            case 3:
                createUserAccount();
                showUserOperations();
                break;
            case 4:
                deleteTheUser();
                showUserOperations();
                break;
            case 5:
                createManagerAccount();
                showAdminMenu();
            case 0:
                showAdminMenu();
        }
    }

    public void addNewMovieAccount() {
        createNewMovie();
        createMovieTickets();
        System.out.println("уч.запись для фильма '" + movie.getTitle().toUpperCase() +
                "' на дату " + movie.showDateTimeWithoutSeconds() + " со списком билетов успешно добавлена");
    }

    public void createNewMovie() {
        Movie movieToLoad = new Movie();
        System.out.println("Введите название фильма...");
        String title = scanString();
        if (title.length() < 1 || title.equals(" ")) {
            System.out.println("операция прервана: некорректное название фильма");
            showMovieOperations();
        }
        movieToLoad.setTitle(title.toUpperCase());
        System.out.println("Введите дату и время показа фильма...");
        String sessionDate = scanString();
        while (!movServ.inputCorrectDateTimeFormat(sessionDate)) {
            System.out.println("Формат даты должен быть: 'гггг-мм-дд чч:мм' или 'гггг-мм-дд чч:мм:сс'");
            sessionDate = scanString();
        }
        if (sessionDate.length() == 16) {
            sessionDate = sessionDate + ":00";
        }
        movieToLoad.setDateTime(Timestamp.valueOf(sessionDate));
        if (!movServ.checkActualityOfTheTime(sessionDate)) {
            System.out.println("операция прервана: данная дата уже прошла");
            showMovieOperations();
        }
        if (movServ.isTheSlotOfThisDateTimeOccuped(sessionDate)) {
            System.out.println("операция прервана: на данную дату уже запланирован другой сеанс");
            showMovieOperations();
        }
        movServ.addMovie(movieToLoad);
        movie = movServ.getMovie(movieToLoad.getTitle());
    }

    public void createMovieTickets() {
        Ticket ticket = new Ticket();
        System.out.println("Введите цену билета для данного сеанса...");
        int price = scanInt();
        if (price < 0) {
            System.out.println("операция прервана: указана некорректная цена билета");
            showMovieOperations();
        }
        int count = 1;
        for (int i = 0; i < 10; i++, count++) {
            ticket.setMovieID(movie.getMovieID());
            ticket.setPlace(count);
            ticket.setCost(price);
            tickServ.addTicket(ticket);
        }
    }

    public void deleteMovieAccount() {
        System.out.println("Введите № уч.записи для удаления...");
        int movieID = scanInt();
        movie = movServ.getMovie(movieID);
        if (movie.getTitle() == null) {
            System.out.println("операция прервана: уч.записи с данным номером не найдено");
            showMovieOperations();
        }
        tickServ.removeTicket(movie);
        movServ.removeMovie(movieID);
        System.out.println("уч.запись №" + movieID + " (фильм: " + movie.getTitle() +
                " " + movie.showDateTimeWithoutSeconds() + ") удалена");
        System.out.println("- - - - - - - - - - - - - - - - - - - - - - - -");
    }

    public void changePassword() {
        System.out.println("введите логин пользователя для смены пароля...");
        String login = scanString();
        User user = usServ.getUser(login);
        if (user.getPassword() == null) {
            System.out.println("операция прервана: такого пользователя нет в базе");
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
            showUserOperations();
        }
        usServ.updatePassword(login, newPassword);
        System.out.println("пароль успешно обновлен");

    }

    public void deleteTheUser() {
        System.out.println("введите логин пользователя для удаления...");
        String login = scanString();
        User user = usServ.getUser(login);
        if (user.getPassword() == null) {
            System.out.println("операция прервана: пользователя с логином " + login + " в базе нет");
            showUserMenu();
        }
        usServ.removeUser(login);
        System.out.println("пользователь " + login + " удален");
        List<Integer> userTicketNumbers = tickServ.getTicketNumbersOfUser(user);
        String positiveResault = "данные пользователя " + login + " успешно удалены";
        if (userTicketNumbers.isEmpty()) {
            System.out.println(positiveResault);
        } else {
            for (Integer value : userTicketNumbers) {
                tickServ.removeUsernameFromTicket(value);
            }
            System.out.println(positiveResault);
        }
    }

    @Override
    public void createUserAccount() {
        System.out.println("Создайте логин для уч.записи пользователя...");
        String login = scanString();
        while(!checkTheCorrectnessOfTheLoginInput(login)){
            login = scanString();
        }
        System.out.println("Создайте пароль для уч.записи пользователя...");
        String password = scanString();
        while(!checkTheCorrectnessOfThePasswordInput(password)){
            password = scanString();
        }
        System.out.println("Повторите пароль...");
        String secondPassword = scanString();
        if (!password.equals(secondPassword)){
            System.out.println("операция прервана: пароли не совпадают");
            showUserOperations();
        }
        String encodedPassword = new Base64encoder().getEncode(password);
        User manager = new User(login, encodedPassword);
        usServ.addUser(manager);
        System.out.println("Новая учетная запись пользователя " + login + " создана!");
        System.out.println();
    }

    public void createManagerAccount() {
        System.out.println("Создайте логин для уч.записи мэнеджера...");
        String login = scanString();
        while(!checkTheCorrectnessOfTheLoginInput(login)){
            login = scanString();
        }
        System.out.println("Создайте пароль для уч.записи мэнеджера...");
        String password = scanString();
        while(!checkTheCorrectnessOfThePasswordInput(password)){
            password = scanString();
        }
        System.out.println("Повторите пароль...");
        String secondPassword = scanString();
        if (!password.equals(secondPassword)){
            System.out.println("операция прервана: пароли не совпадают");
            showUserOperations();
        }
        String encodedPassword = new Base64encoder().getEncode(password);
        User manager = new Manager(login, encodedPassword);
        usServ.addUser(manager);
        System.out.println("Новая учетная запись для мэнеджера " + login + " создана!");
        System.out.println();
    }
}
