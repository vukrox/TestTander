package ru.vukrox.periphery.dao.postgres;

import ru.vukrox.periphery.configuration.AppConfiguration;

import java.sql.*;

/***
 * Class responsible for handling of the connection with DB.
 * It can open and close the connection to the DB.
 */

public class PostgreDBConnecton {

    //a method to open connection
    public static Connection establishConnection(AppConfiguration appConfiguration) {

        Connection connection = null;
        try {
            connection = DriverManager.getConnection(
                    appConfiguration.getConfigValue("url"),
                    appConfiguration.getConfigValue("user"),
                    appConfiguration.getConfigValue("password"));

            if (connection != null) {
                System.out.println("Connection with SQL DB is established successfully!");
            } else {
                System.out.println("Can't connect to the Data Base.");
            }
        } catch (Exception e) {
            System.out.println("Something went wrong.");
            e.getCause();
        }
        return connection;
    }

    //a method to close the connection
    public static void closeConnetion(Connection connection) {
        try {
            if (connection != null) {
                connection.close();
            }
        } catch (SQLException sqlException) {
            System.out.println("Couldn't close connection");
            sqlException.printStackTrace();
        }
        System.out.println("Connection was closed successfully!");
    }
}
