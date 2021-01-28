package com.rdruzhchenko.service;

import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DbConnection {

    public Connection connect(String fileName) {
        Connection connection = null;

        try {
            URL path = getClass().getClassLoader().getResource(fileName);
            String url = "jdbc:sqlite:" + path.toString();
            connection = DriverManager.getConnection(url);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return connection;
    }

    public void disconnect(Connection connection) {
        try {
            if (connection != null) {
                connection.close();
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
}
