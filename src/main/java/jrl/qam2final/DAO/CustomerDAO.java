package jrl.qam2final.DAO;

import javafx.collections.ObservableList;
import jrl.qam2final.Model.CustomerModel;

/**
 * CustomerDAO interface defines methods for interacting with customer data in the database.
 *
 * @author Jeffrey Robert Lynch
 */
public interface CustomerDAO {

    /**
     * Updates the information for a customer in the database. If a SQLException occurs, it is handled by a helper
     * method named handleSQLExceptionCusDAO. If the update is successful, a message is displayed to the user using the
     * helper method displayInfoCusDAO. If the update is not successful an error message is displayed to the user using
     * the helper method displayErrorCusDAO.
     *
     * @param customerID_TODB Customer ID of updated customer.
     * @param customerN_TODB Customer Name of updated customer.
     * @param address_TODB Address of updated customer.
     * @param postalC_TODB Postal Code of updated customer.
     * @param phone_TODB Phone Number of updated customer.
     * @param divisionID_TODB Division ID of updated customer.
     * @return True if update is successful, false otherwise.
     */
    boolean updateCustomerCusDAO(int customerID_TODB, String customerN_TODB, String address_TODB, String postalC_TODB, String phone_TODB, int divisionID_TODB);

    /**
     * Deletes a customer from the database by customer ID and customer ame. It calls a method to ensure a customer's
     * appointments are deleted before the customer is deleted. If a SQLException occurs, it is handled by a helper
     * method named handleSQLExceptionCusDAO. If the delete operation is successful, a message is displayed to the user
     * using the helper method displayInfoCusDAO. If the delete operation is not successful an error message is
     * displayed to the user using the helper method displayErrorCusDAO.
     *
     * @param customerID_DelDB Customer ID for customer deleted from database.
     * @param customerN_DelDB Customer Name for customer deleted from database.
     * @return True if deletion is successful, false otherwise.
     */
    boolean deleteCustomerCusDAO(int customerID_DelDB, String customerN_DelDB);

    /**
     * Creates a new customer entry in the database with values provided by the onActionSaveCreatedCustomerCCC
     * controller method. If a SQLException occurs, it is handled by a helper method named handleSQLExceptionCusDAO. If
     * the insert is successful, a message is displayed to the user using the helper method displayInfoCusDAO. If the
     * insert is not successful an error message is displayed to the user using the helper method displayErrorCusDAO.
     *
     * @param customerN_TODB  Customer Name for new customer.
     * @param address_TODB    Address for new customer.
     * @param postalC_TODB    Postal Code for new customer.
     * @param phone_TODB      Phone Number for new customer.
     * @param divisionID_TODB Division ID for new customer.
     * @return True if creation succeeds, otherwise false.
     */
    boolean createCustomerCusDAO(String customerN_TODB, String address_TODB, String postalC_TODB, String phone_TODB, int divisionID_TODB);

    /**
     * Reads all customers from the database, returning an ObservableList of CustomerModel objects. Executes a SQL query
     * that selects all customers from the database. Each row from the customer table is converted to a CustomerModel
     * object and added to the list. If a SQLException occurs, it is handled by a helper method named
     * handleSQLExceptionCustomerDAO. Use to populate GUI with customers.
     *
     * @return customerToTable ObservableList of CustomerModel objects representing customers.
     */
    ObservableList<CustomerModel> readCustomerAllCusDAO();
}