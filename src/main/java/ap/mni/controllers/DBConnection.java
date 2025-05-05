package ap.mni.controllers;

import java.sql.Connection;
import java.sql.DriverManager;//Imports DriverManager, which is used to create a connection to a database using JDBC
import java.sql.SQLException;

public class DBConnection {
    private static final String URL = "jdbc:mysql://172.20.10.2:3306/mvc_app";
    private static final String USER = "root";
    private static final String PASSWORD = "123456";

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}
/*ha to connect to your MySQL database using JDBC.

Connection conn = DBConnection.getConnection();  use conn to create statements and interact with the database.

 */