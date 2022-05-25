package by.lev.service.inputChecks;

import by.lev.databaseConnection.AbstractConnection;
import by.lev.exceptions.EnumMovieException;
import by.lev.exceptions.MovieException;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static by.lev.databaseConnection.AbstractConnection.closeConnection;
import static by.lev.exceptions.EnumMovieException.MD_009;
import static by.lev.exceptions.EnumMovieException.MD_009F;


public class MovieInputCheck {
    public boolean checkTheCorrectnessMovieTitle(String title) {
        if (title.isEmpty()) {
            System.out.println("Movie title can not be empty!");
            return false;
        }
        return true;
    }

    public boolean inputCorrectDateTimeFormat(String dateTime) {
        Pattern pattern = Pattern.compile("[\\d]{4}\\-[\\d]{2}\\-[\\d]{2}\\ [\\d]{2}\\:[\\d]{2}");
        Matcher matcher = pattern.matcher(dateTime);
        Pattern pattern2 = Pattern.compile("[\\d]{4}\\-[\\d]{2}\\-[\\d]{2}\\ [\\d]{2}\\:[\\d]{2}\\:[\\d]{2}");
        Matcher matcher2 = pattern2.matcher(dateTime);
        if (matcher.matches() == true || matcher2.matches() == true) {
            return true;
        } else {
            System.out.println("Формат даты должен быть: 'гггг-мм-дд чч:мм' или 'гггг-мм-дд чч:мм:сс'");
            return false;
        }
    }

    public boolean checkActualityOfTheTime(String dateTime) {
        Timestamp currentTime = Timestamp.valueOf(LocalDateTime.now());
        if (Timestamp.valueOf(dateTime).before(currentTime)) {
            System.out.println("Указанная дата уже прошла!");
            return false;
        }
        return true;
    }


}
