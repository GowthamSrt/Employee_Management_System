package utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
* <p>
* Initialize and ensures the succesful connection between databse and
* application interface to transfer the data.
* </p>
*/

public class DatabaseConnection {
    private static final String URL = "jdbc:mysql://localhost:3306/employee_management";
    private static final String USER = "root";
    private static final String PASSWORD = "";
    private static Connection connection = null;

    /**
    * <p>
    * Checks whether the connection between databse and application already
    * connected or not to ensure the safe connectivity.
    * </p>
    */
    public static Connection getConnection() {
        try {
            if (connection == null || connection.isClosed()) {
               createConnection();
            }
        } catch (SQLException e) {
           System.out.println(e.getMessage());
        }
        return connection;
    }

    /**
    * <p>
    * Creates the connection between database and application using the
    * user request and connect it and then the operations are done.
    * </p>
    */
    private static void createConnection() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection(URL, USER, PASSWORD);
            //System.out.println("Connected Succesfully");
        } catch (ClassNotFoundException | SQLException e) {
            throw new RuntimeException("Error initializing database connection", e);
        }   
    }

    /**
    * <p>
    * Disconnects the connection between database and application when each
    * operation is completed to ensure that only one operation is done in
    * every connection.
    * </p>
    */

    public static void closeConnection() {
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}