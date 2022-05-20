package by.lev.databaseConnection;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public abstract class AbstractConnection {
    private static Connection connection = null;
    private static Properties properties = getProperties();

    public static Connection getConnection() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            if (connection == null) {
                connection = DriverManager.getConnection(
                        properties.getProperty("url"),
                        properties.getProperty("login"),
                        properties.getProperty("password")

                );
            }
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
        return connection;
    }

    private static Properties getProperties() {
        File file = new File("src/by/lev/files/databaseConnection.properties");
        Properties properties = new Properties();
        try {
            properties.load(new FileReader(file));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return properties;
    }

    public static void closeConnection() throws SQLException {
        if(connection != null){
            connection.close();
            connection = null;
        }
    }
}
