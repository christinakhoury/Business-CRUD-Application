package ap.mni.controllers;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {
    private static final String URL = "jdbc:mysql://192.168.1.109:3306/mvc_app";

    private static final String USER = "root"; // change to your MySQL username if different
    private static final String PASSWORD = "123456"; // replace with your MySQL password

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}
