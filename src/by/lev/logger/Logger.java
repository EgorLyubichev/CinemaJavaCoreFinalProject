package by.lev.logger;

import by.lev.user.User;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Timestamp;
import java.time.LocalDateTime;

import static by.lev.controller.Entrance.USER_ONLINE;

public class Logger {
    private static String time = LocalDateTime.now().toString().substring(0,19);

    //USER

    public static void writeVisit(){
        String message = "= = = = = = = = = = = = = = = = = = = = = = =\n"
                + time + ": пользователь " + USER_ONLINE.getLogin() + " вошёл в систему.";
        writeToLogFile(message);
    }

    public static void writeExit(){
        String message = time + ": пользователь " + USER_ONLINE.getLogin()
                + " вышел из системы.\n= = = = = = = = = = = = = = = = = = = = = = =";
        writeToLogFile(message);
    }

    public static void writeRegistration(User user){
        String message = "= = = = = = = = = = = = = = = = = = = = = = =\n"
                + time + ": пользователь " + user.getLogin() + " зарегистрировался.";
        writeToLogFile(message);
    }

    public static void writeAction(String methodName){
        String message = time + ": " + USER_ONLINE.getLogin() + " --> " + methodName;
        writeToLogFile(message);
    }

    public static void writeBuyingATicketOfTheUser(int ticketId, String title, Timestamp session){
        StringBuilder message = new StringBuilder();
                message.append(time)
                       .append(": ").append(USER_ONLINE.getLogin())
                       .append(" приобрел билет №").append(ticketId)
                       .append(" на фильм: ").append(title)
                       .append(" на сеанс ").append(session.toString().substring(0,16));
        writeToLogFile(message.toString());
    }

    public static void writeCancellingOfTheUserTicket(int ticketId){
        String message = time + ": " + USER_ONLINE.getLogin() + " отменил билет №" + ticketId;
        writeToLogFile(message);
    }


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
