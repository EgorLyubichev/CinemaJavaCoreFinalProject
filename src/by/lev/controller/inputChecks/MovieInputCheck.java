package by.lev.controller.inputChecks;

import by.lev.databaseConnection.AbstractConnection;
import by.lev.exceptions.EnumMovieException;
import by.lev.exceptions.MovieException;
import by.lev.movie.Movie;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


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

    public boolean isTheSlotOfThisDateTimeOccuped(String dateTime) throws MovieException {
        try {
            Connection connection = AbstractConnection.getConnection();
            PreparedStatement prs = connection.prepareStatement("SELECT dateTime FROM movie WHERE dateTime=?");
            prs.setTimestamp(1, Timestamp.valueOf(dateTime));
            ResultSet rs = prs.executeQuery();
            while(rs.next()){
                Timestamp ts = rs.getTimestamp("dateTime");
                if(ts != null){
                    System.out.println("Указанная дата уже занята другим сеансом!");
                    return true;
                }
            }
        } catch (SQLException e) {
            throw new MovieException(EnumMovieException.MIC_01);
        }
        return false;
    }
}
