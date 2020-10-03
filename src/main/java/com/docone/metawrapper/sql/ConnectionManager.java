package com.docone.metawrapper.sql;

import java.sql.*;
import java.util.logging.Logger;

public class ConnectionManager {

    private static final Logger LOGGER = Logger.getLogger(ConnectionManager.class.getName());
    private static String driverName = "com.mysql.cj.jdbc.Driver";
    private static String url;
    private static String username;
    private static String password;

    // Initialize from env variables
    static {
        try {
            username = System.getenv("MYSQL_USER");
            password = System.getenv("MYSQL_PW");
            url = String.format(
                    "jdbc:mysql://%s:%s/%s?serverTimezone=UTC&autoReconnect=true&failOverReadOnly=false&maxReconnects=10",
                    System.getenv("MYSQL_HOST"),
                    System.getenv("MYSQL_PORT"),
                    System.getenv("MYSQL_DB")
            );
        } catch (Exception e) {
            LOGGER.warning("Unable to read database credentials. Please make sure your .env is filled out.");
        }
    }

    private static Connection con;

    public static Connection getConnection() {
        try {
            Class.forName(driverName);
            try {
                con = DriverManager.getConnection(url, username, password);
            } catch (SQLException ex) {
                System.out.println(ex);
                LOGGER.warning("Unable to connect to database");
                return null;
            }
        } catch (ClassNotFoundException ex) {
            LOGGER.warning("JDBC driver not found.");
            return null;
        }

        return con;
    }
}
