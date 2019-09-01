package com.krontobi.connection;

import com.krontobi.PropertyReader;
import org.apache.log4j.Logger;

import java.io.FileNotFoundException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionDB {

    private static String DB_URL;
    private static String USER;
    private static String PASS;
    private static final Logger log = Logger.getLogger(ConnectionDB.class);

    public static void main(String[] argv) {

        try {
            PropertyReader propertyReader = new PropertyReader("connection.properties");
            DB_URL = propertyReader.getProperties().getProperty("url");
            USER = propertyReader.getProperties().getProperty("user");
            PASS = propertyReader.getProperties().getProperty("password");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        log.info("Testing connection to PostgreSQL JDBC");

        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            log.info("PostgreSQL JDBC Driver is not found. Include it in your library path ");
            log.error(e);
            return;
        }

        log.info("PostgreSQL JDBC Driver successfully connected");
        Connection connection = null;

        try {
            connection = DriverManager.getConnection(DB_URL, USER, PASS);
        } catch (SQLException e) {
            log.info("Connection Failed");
            log.error(e);
        } finally {
            try {
                if (connection != null) {
                    log.info("You successfully connected to database now");
                    connection.close();
                } else {
                    log.info("Failed to make connection to database");
                }
            } catch (SQLException e) {
                log.error(e);
            }
        }


    }

}
