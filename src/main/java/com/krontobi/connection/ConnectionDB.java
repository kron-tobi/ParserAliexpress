package com.krontobi.connection;

import com.krontobi.PropertyReader;
import org.apache.log4j.Logger;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class ConnectionDB {

    private static String DB_URL;
    private static String USER;
    private static String PASS;
    private static final Logger log = Logger.getLogger(ConnectionDB.class);

    public ConnectionDB() {
        try {
            PropertyReader propertyReader = new PropertyReader("connection.properties");
            DB_URL = propertyReader.getProperties().getProperty("url");
            USER = propertyReader.getProperties().getProperty("user");
            PASS = propertyReader.getProperties().getProperty("password");
        } catch (FileNotFoundException e) {
            log.error(e);
        }
    }

    public void postInfoInDB(String[] str) {
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
        LocalDateTime dateTime = LocalDateTime.now(); // gets the current date and time

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
        //System.out.println(dateTime.format(formatter));
        //System.out.println(date.format(formatter));
        if (str[1].length() > 100) {
            str[1] = str[1].substring(0, 100);
        }
        try {
            connection = DriverManager.getConnection(DB_URL, USER, PASS);
            String sql = "INSERT INTO videocards (id_videocard, date_price, url_videocard, name_videocard, price_videocard, orders_videocard, reviews_videocard) " +
                    "VALUES (?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, getMaxID(connection) + 1);
            preparedStatement.setTimestamp(2, Timestamp.valueOf(dateTime));
            preparedStatement.setString(3, str[0]);
            preparedStatement.setString(4, str[1]);
            preparedStatement.setFloat(5, Float.parseFloat(str[2]));
            preparedStatement.setInt(6, Integer.parseInt(str[3]));
            preparedStatement.setInt(7, Integer.parseInt(str[4]));
            preparedStatement.executeUpdate();
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

    private int getMaxID(Connection connection) {
        int maxValue = 0;
        String sql = "SELECT MAX(id_videocard) " +
                "FROM videocards";
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);
            while (resultSet.next()) {
                maxValue = resultSet.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return maxValue;
    }

}