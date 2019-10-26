package com.krontobi.connection;

import com.krontobi.PropertyReader;
import org.apache.log4j.Logger;

import java.io.FileNotFoundException;
import java.sql.*;
import java.time.LocalDateTime;


public class ConnectionDB {

    private static final PropertyReader propertyReader = new PropertyReader("connection.properties");
    private static String DB_URL = propertyReader.getProperties().getProperty("url");
    private static final String USER = propertyReader.getProperties().getProperty("user");
    private static String PASS = propertyReader.getProperties().getProperty("password");
    private static final Logger log = Logger.getLogger(ConnectionDB.class);
    private Connection connection = null;

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
            log.error("Connection Failed " + e);
        }
    }

    public void closeConnectionDB() {
        if (connection != null) {
            try {
                connection.close();
                log.info("You successfully connected to database now");
            } catch (SQLException e) {
                log.error("Close connection Failed " + e);
            }
        } else {
            log.info("Failed to make connection to database");
        }
    }

    public boolean existURL(String shortURL) {
        String sql = "SELECT * " +
                "FROM url_product " +
                "WHERE short_url = '" + shortURL + "'";
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
        String sql = "INSERT INTO url_product(id, url, short_url) VALUES (?, ?, ?)";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, getMaxID("id", "url_product") + 1);
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
            String sql = "INSERT INTO product (id, date_price, url, description, price, orders, reviews) " +
                    "VALUES (?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, getMaxID("id", "product") + 1);
            preparedStatement.setTimestamp(2, Timestamp.valueOf(dateTime));
            preparedStatement.setInt(3, getId("short_url", str[0], "url_product"));
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

    private int getMaxID(String id, String table) {
        int maxValue = 0;
        String sql = "SELECT MAX(" + id + ") " +
                "FROM " + table;
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

    private int getId(String varDB, String varMy, String table) {
        int id = 0;
        String sql = "SELECT * " +
                "FROM " + table +
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
