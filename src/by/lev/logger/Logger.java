package by.lev.logger;

import by.lev.user.User;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;

import static by.lev.controller.Entrance.USER_ONLINE;

public class Logger {

    //USER

    public static void writeUserVisit(){
        String timestamp = LocalDateTime.now().toString();
        String message = "= = = = = = = = = = = = = = = = = = = = = = =\n"
                + timestamp + ": пользователь " + USER_ONLINE.getLogin() + " вошёл в систему.";
        writeToLogFile(message);
    }

    public static void writeUserExit(){
        String timestamp = LocalDateTime.now().toString();
        String message = timestamp + ": пользователь " + USER_ONLINE.getLogin()
                + " вышел из системы.\n= = = = = = = = = = = = = = = = = = = = = = =";
        writeToLogFile(message);
    }

    public static void writeUserRegistration(User user){
        String timestamp = LocalDateTime.now().toString();
        String message = "= = = = = = = = = = = = = = = = = = = = = = =\n"
                + timestamp + ": пользователь " + user.getLogin() + " зарегистрировался.";
        writeToLogFile(message);
    }

    public static void writeUserAction(String methodName){
        String timestamp = LocalDateTime.now().toString();
        String message = timestamp + ": " + USER_ONLINE.getLogin() + " --> " + methodName;
        writeToLogFile(message);
    }
    public static void writeUserAction(String methodName, String comment){
        String timestamp = LocalDateTime.now().toString();
        String message = timestamp + ": " + USER_ONLINE.getLogin() + " --> " + methodName;
        writeToLogFile(message);
    }

    public static void writeBuyingATicketOfTheUser(int ticketId){
        String timestamp = LocalDateTime.now().toString();
        String message = timestamp + ": " + USER_ONLINE.getLogin() + " приобрел билет №" + ticketId;
        writeToLogFile(message);
    }

    public static void writeCancellingOfTheUserTicket(int ticketId){
        String timestamp = LocalDateTime.now().toString();
        String message = timestamp + ": " + USER_ONLINE.getLogin() + " отменил билет №" + ticketId;
        writeToLogFile(message);
    }

    public static void writeMethodError(User user, String methodName){
        String timestamp = LocalDateTime.now().toString();
        String message = timestamp + ": " + user.getLogin() + " --> " + methodName;
        writeToLogFile(message);
    }

    //MANAGER

    //ADMINISTRATOR

    private static void writeToLogFile(String message){
        File file = new File("src/by/lev/target/logFile.txt");
        try {
            FileWriter fw = new FileWriter(file, true);
            BufferedWriter bw = new BufferedWriter(fw);
            bw.write(message);
            bw.newLine();
            bw.close();
        }catch(IOException e){
            e.printStackTrace();
        }
    }
}
