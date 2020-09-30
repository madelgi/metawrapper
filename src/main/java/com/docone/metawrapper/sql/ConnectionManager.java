package com.docone.metawrapper.sql;

import java.io.IOException;
import java.io.InputStream;
import java.sql.*;
import java.util.Properties;
import java.util.logging.Logger;

public class ConnectionManager {

    private static final Logger LOGGER = Logger.getLogger(ConnectionManager.class.getName());
    private static String url;
    private static String driverName;
    private static String username;
    private static String password;

    // Initialize from properties file
    static {
        try (InputStream input = ConnectionManager.class.getClassLoader().getResourceAsStream("db.properties")) {
            Properties prop = new Properties();
            if (input == null) {
                throw new IOException();
            }
            prop.load(input);

            url = prop.getProperty("db.url");
            driverName = prop.getProperty("db.driver");
            username = prop.getProperty("db.username");
            password = prop.getProperty("db.password");
        } catch (IOException ex) {
            LOGGER.warning("Unable to get database properties.");
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
