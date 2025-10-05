package jrl.qam2final.Helper;

import javafx.scene.control.Alert;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * A helper class for managing JDBC connections to the database.
 *
 *@author Jeffrey Robert Lynch
 */
public abstract class JDBCHelper {

    private static final String PROTOCOL_NAME = "jdbc";
    private static final String VENDOR_NAME = ":mysql:";
    private static final String LOCATION_NAME = "//localhost/";
    private static final String DATABASE_NAME = "client_schedule";
    private static final String JDBC_URL = PROTOCOL_NAME + VENDOR_NAME + LOCATION_NAME + DATABASE_NAME + "?connectionTimeZone = SERVER";
    private static final String DRIVER_NAME = "com.mysql.cj.jdbc.Driver";
    private static final String USER_NAME = "your_db_user_here";
    private static final String PASSWORD = "your_password_here";
    public static Connection connectDB;

    /**
     * Handles SQL exception for JDBCHelper.
     *
     * @param e SQL exception
     */
    private static void handleSQLExceptionJDBCH(SQLException e) {
        System.out.println("SQL Error: " + e.getMessage());
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("SQL Error");
        alert.setHeaderText("An error occurred");
        alert.setContentText("SQL Error: " + e.getMessage());
        alert.showAndWait();
    }

    /**
     * Database connection is established.
     */
    public static void startDBConnection(){
        try {
            Class.forName(DRIVER_NAME);
            connectDB = DriverManager.getConnection(JDBC_URL, USER_NAME, PASSWORD );
            System.out.println("Start Database Connection.");
        }catch(Exception e){
            System.out.println("Error starting connection: " + e.getMessage());
        }
    }

    /**
     * Ends the database connection unless already null.
     */
    public static void endDBConnection() {
        try {
            if (connectDB != null) {
                connectDB.close();
                System.out.println("End Database Connection.");
            } else {
                System.out.println("Connection is already null.");
            }
        } catch (SQLException e) {
            handleSQLExceptionJDBCH(e);
        }
    }
}