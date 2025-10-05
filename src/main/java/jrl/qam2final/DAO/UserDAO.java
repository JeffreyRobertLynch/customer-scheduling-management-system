package jrl.qam2final.DAO;

import javafx.collections.ObservableList;
import jrl.qam2final.Model.UserModel;

/**
 * UserDAO interface defines methods for interacting with user data in the database.
 *
 * @author Jeffrey Robert Lynch
 */
public interface UserDAO {

    /**
     * Reads all user data from the database, returning an ObservableList of UserModel objects. Executes a SQL query
     * that selects all users from the database. Each row from the user table is converted to a UserModel object and
     * added to the list. If a SQLException occurs, it is handled by a helper method named handleSQLExceptionUserDAO.
     *
     * @return userFromDB ObservableList of UserModel objects representing all users in the database.
     */
    ObservableList<UserModel> readUserAllUserDAO();

}