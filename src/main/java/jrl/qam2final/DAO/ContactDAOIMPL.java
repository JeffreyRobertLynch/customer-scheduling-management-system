package jrl.qam2final.DAO;

import javafx.collections.ObservableList;
import jrl.qam2final.Model.ContactModel;
import javafx.scene.control.Alert;
import java.sql.PreparedStatement;
import jrl.qam2final.Helper.TranslationManagerHelper;
import java.sql.ResultSet;
import java.sql.SQLException;
import javafx.collections.FXCollections;

import static jrl.qam2final.Helper.JDBCHelper.connectDB;

/**
 * Implementation of the ContactDAO interface. Provides methods to interact with contact data in the database.
 *
 * @author Jeffrey Robert Lynch
 */
public class ContactDAOIMPL implements ContactDAO{

    /**
     * Helper method for the ContactDAOIMPL class that displays SQL errors to the user and prints them to the console.
     *
     * @param e SQLException
     */
    private void handleSQLExceptionConDAO(SQLException e) {
        System.out.println("SQL Error: " + e.getMessage());

        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("SQL Error");
        alert.setHeaderText("An error occurred");
        alert.setContentText("SQL Error: " + e.getMessage());
        alert.showAndWait();
    }

    /**
     * Reads all contact data from the database, returning an ObservableList of ContactModel objects. Executes a SQL
     * query that selects all contacts from the database. Each row from the contact table is converted to a ContactModel
     * object and added to the list. If a SQLException occurs, it is handled by a helper method named
     * handleSQLExceptionConDAO. Used to populate GUI combo boxes.
     *
     * @return contactConDB ObservableList of ContactModel objects representing all contacts in the database.
     */
    @Override
    public ObservableList<ContactModel> readContactAllConDAO() {
        ObservableList<ContactModel> contactConDB = FXCollections.observableArrayList();
        try {
            String sql = "SELECT Contact_ID, Contact_Name, Email FROM contacts";
            PreparedStatement readContactAllStatement = connectDB.prepareStatement(sql);
            ResultSet queryReturn = readContactAllStatement.executeQuery();
            for (; queryReturn.next();) {
                int contactID_ConDB = queryReturn.getInt("Contact_ID");
                String contactN_ConDB = queryReturn.getString("Contact_Name");
                String email_ConDB = queryReturn.getString("Email");
                ContactModel contactDB = new ContactModel(contactID_ConDB, contactN_ConDB, email_ConDB);
                contactConDB.add(contactDB);
            }
        } catch (SQLException e) {
            handleSQLExceptionConDAO(e);
        }
        return contactConDB;
    }
}