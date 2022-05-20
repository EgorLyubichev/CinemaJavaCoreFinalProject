package by.lev.controller.userActions;

import by.lev.controller.inputChecks.MovieInputCheck;
import by.lev.exceptions.MovieException;
import by.lev.movie.Movie;
import by.lev.movie.MovieDAO;

import java.sql.Timestamp;

import static by.lev.controller.controllerFunctions.ControllerFunction.*;

public class AdministratorController extends ManagerController {
    Movie movie = new Movie();

    public void addNewMovie() throws MovieException {
        System.out.println("Введите название фильма...");
        String title = scanString();
        System.out.println("Введите дату и время показа фильма...");
        boolean checkDateCorrectness = false;
        while (checkDateCorrectness == false) {
            String sessionDate = scanString();
            if (sessionDate.length() == 16) {
                sessionDate = sessionDate + ":00";
            }
            MovieInputCheck mich = new MovieInputCheck();
            if (mich.inputCorrectDateTimeFormat(sessionDate) == true &&
                    mich.checkActualityOfTheTime(sessionDate) == true &&
                    mich.isTheSlotOfThisDateTimeOccuped(sessionDate) == false) {
                Movie movieToLoad = new Movie();
                movieToLoad.setTitle(title);
                movieToLoad.setDateTime(Timestamp.valueOf(sessionDate));
                MovieDAO md = new MovieDAO();
                md.create(movieToLoad);

                        checkDateCorrectness = true;
            }
        }
    }

    public void createMovieTickets() {
        System.out.println();
    }
}
