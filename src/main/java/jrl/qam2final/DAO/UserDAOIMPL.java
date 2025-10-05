package jrl.qam2final.DAO;

import java.sql.PreparedStatement;
import javafx.collections.ObservableList;
import jrl.qam2final.Model.UserModel;
import javafx.scene.control.Alert;
import jrl.qam2final.Helper.TranslationManagerHelper;
import java.sql.ResultSet;
import javafx.collections.FXCollections;
import java.sql.SQLException;

import static jrl.qam2final.Helper.JDBCHelper.connectDB;

/**
 * Implementation of UserDAO interface. Provides methods for operations related to user data in the database.
 *
 * @author Jeffrey Robert Lynch
 */
public class UserDAOIMPL implements UserDAO{

    ObservableList<UserModel> userFromDB = FXCollections.observableArrayList();

    /**
     * Helper method for the UserDAOIMPL class that displays SQL errors to the user and prints them to the
     * console.
     *
     * @param e SQLException
     */
    private void handleSQLExceptionUserDAO(SQLException e) {
        System.out.println("SQL Error: " + e.getMessage());
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("SQL Error");
        alert.setHeaderText("An error occurred");
        alert.setContentText("SQL Error: " + e.getMessage());
        alert.showAndWait();
    }

    /**
     * Reads all user data from the database, returning an ObservableList of UserModel objects. Executes a SQL query
     * that selects all users from the database. Each row from the user table is converted to a UserModel object and
     * added to the list. If a SQLException occurs, it is handled by a helper method named handleSQLExceptionUserDAO.
     *
     * @return userFromDB ObservableList of UserModel objects representing all users in the database.
     */
    @Override
    public ObservableList<UserModel> readUserAllUserDAO() {
        try {
            String sql = "SELECT \n" +
                    "    User_ID, \n" +
                    "    User_Name, \n" +
                    "    Password \n" +
                    "FROM \n" +
                    "    users";
            PreparedStatement readUserAllStatement = connectDB.prepareStatement(sql);
            ResultSet queryReturn = readUserAllStatement.executeQuery();
            for (; queryReturn.next();) {
                int userID_UsDB = queryReturn.getInt("User_ID");
                String userN_UsDB = queryReturn.getString("User_Name");
                String pass_UsDB = queryReturn.getString("Password");
                UserModel userDB = new UserModel(userID_UsDB, userN_UsDB, pass_UsDB);
                userFromDB.add(userDB);
            }
        } catch (SQLException e) {
            handleSQLExceptionUserDAO(e);
        }
        return userFromDB;
    }

}