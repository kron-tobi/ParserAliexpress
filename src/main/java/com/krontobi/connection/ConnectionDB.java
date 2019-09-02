package com.krontobi.connection;

import com.krontobi.ParserURL;
import com.krontobi.PropertyReader;
import org.apache.log4j.Logger;

import java.io.FileNotFoundException;
import java.sql.*;
import java.time.LocalDateTime;


public class ConnectionDB {

    private static String DB_URL;
    private static String USER;
    private static String PASS;
    private static final Logger log = Logger.getLogger(ConnectionDB.class);
    private Connection connection = null;

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

    public void setConnectionDB() {
        log.info("Testing connection to PostgreSQL JDBC");
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            log.info("PostgreSQL JDBC Driver is not found. Include it in your library path ");
            log.error(e);
            return;
        }
        log.info("PostgreSQL JDBC Driver successfully connected");

        try {
            connection = DriverManager.getConnection(DB_URL, USER, PASS);
        } catch (SQLException e) {
            log.info("Connection Failed");
            log.error(e);
        }
    }

    public void closeConnectionDB() {
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

    public boolean existURL(String shortURL) {
        String sql = "SELECT * " +
                "FROM url_all_products " +
                "WHERE short_url_product = '" + shortURL + "'";
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);
            if (resultSet.next()) {
                log.info("This url exist");
                return true;
            } else {
                log.info("This url NOT exist");
                return false;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public void addNewURL(String url, String shortURL) {
        String sql = "INSERT INTO url_all_products(id_url_product, url_product, short_url_product) VALUES (?, ?, ?)";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, getMaxID("id_url_product", "url_all_products") + 1);
            preparedStatement.setString(2, url);
            preparedStatement.setString(3, shortURL);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            log.error(e);
        }
    }

    public void insertInDB(String[] str) {
        try {
            if (str[1].length() > 100) {
                str[1] = str[1].substring(0, 100);
            }
            LocalDateTime dateTime = LocalDateTime.now(); // gets the current date and time
            //DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
            connection = DriverManager.getConnection(DB_URL, USER, PASS);
            String sql = "INSERT INTO videocards (id_videocard, date_price, url_videocard, name_videocard, price_videocard, orders_videocard, reviews_videocard) " +
                    "VALUES (?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, getMaxID("id_videocard", "videocards") + 1);
            preparedStatement.setTimestamp(2, Timestamp.valueOf(dateTime));
            preparedStatement.setInt(3, getId("short_url_product", str[0], "url_all_products"));
            preparedStatement.setString(4, str[1]);
            preparedStatement.setFloat(5, Float.parseFloat(str[2]));
            preparedStatement.setInt(6, Integer.parseInt(str[3]));
            preparedStatement.setInt(7, Integer.parseInt(str[4]));
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            log.info("Insert INTO data base Failed");
            log.error(e);
        }
    }

    private int getMaxID(String id, String path) {
        int maxValue = 0;
        String sql = "SELECT MAX(" + id + ") " +
                "FROM " + path;
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

    private int getId(String varDB, String varMy, String path) {
        int id = 0;
        String sql = "SELECT * " +
                "FROM " + path +
                " WHERE " + varDB + " = '" + varMy + "'";
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);
            if (resultSet.next()) {
                id = resultSet.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return id;
    }

}
