package jrl.qam2final.DAO;

import javafx.collections.ObservableList;
import jrl.qam2final.Model.ContactModel;

/**
 * ContactDAO interface defines methods for interacting with contact data in the database.
 *
 * @author Jeffrey Robert Lynch
 */
public interface ContactDAO {

    /**
     * Reads all contact data from the database, returning an ObservableList of ContactModel objects. Executes a SQL
     * query that selects all contacts from the database. Each row from the contact table is converted to a ContactModel
     * object and added to the list. If a SQLException occurs, it is handled by a helper method named
     * handleSQLExceptionConDAO. Used to populate GUI combo boxes.
     *
     * @return contactConDB ObservableList of ContactModel objects representing all contacts in the database.
     */
    ObservableList<ContactModel> readContactAllConDAO();

}