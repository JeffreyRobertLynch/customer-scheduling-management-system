package jrl.qam2final.DAO;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.sql.ResultSet;
import jrl.qam2final.Helper.TranslationManagerHelper;
import java.sql.SQLException;
import javafx.scene.control.Alert;
import jrl.qam2final.Model.CustomerModel;
import java.sql.PreparedStatement;

import static jrl.qam2final.Helper.JDBCHelper.connectDB;

/**
 * Implementation class for the CustomerDAO interface. Provides methods to interact with customer data in the database.
 *
 * @author Jeffrey Robert Lynch
 */
public class CustomerDAOIMPL implements CustomerDAO{

    ObservableList<CustomerModel> customerToTable = FXCollections.observableArrayList();

    /**
     * Helper method for the CustomerDAOIMPL class that displays SQL errors to the user and prints them to the console.
     *
     * @param e SQLException
     */
    private void handleSQLExceptionCusDAO(SQLException e) {
        System.out.println("SQL Error: " + e.getMessage());

        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("SQL Error");
        alert.setHeaderText("An error occurred");
        alert.setContentText("SQL Error: " + e.getMessage());
        alert.showAndWait();
    }

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
    @Override
    public boolean updateCustomerCusDAO(int customerID_TODB, String customerN_TODB, String address_TODB, String postalC_TODB, String phone_TODB, int divisionID_TODB) {
        boolean updateSuccessful = false;
        int updatedCustomerCount = 0;
        try {
            String sql = "UPDATE customers\n" +
                    "SET \n" +
                    "    Customer_Name = ?,\n" +
                    "    Address = ?,\n" +
                    "    Postal_Code = ?,\n" +
                    "    Phone = ?,\n" +
                    "    Division_ID = ?\n" +
                    "WHERE\n" +
                    "    Customer_ID = ?";
            PreparedStatement updateCustomerStatement = connectDB.prepareStatement(sql);
            updateCustomerStatement.setString(1, customerN_TODB);
            updateCustomerStatement.setString(2, address_TODB);
            updateCustomerStatement.setString(3, postalC_TODB);
            updateCustomerStatement.setString(4, phone_TODB);
            updateCustomerStatement.setInt(5, divisionID_TODB);
            updateCustomerStatement.setInt(6, customerID_TODB);
            int modifiedRowCount = updateCustomerStatement.executeUpdate();
            if (modifiedRowCount > 0) {
                updateSuccessful = true;
                updatedCustomerCount = modifiedRowCount;
                displayInfoCusDAO("Update Customer", updatedCustomerCount + " Customer with Customer ID: " + customerID_TODB + " was updated successfully");
            } else {
                displayErrorCusDAO("Update Customer", "Customer with Customer ID: " + customerID_TODB + " failed to update.");
            }
        } catch (SQLException e) {
            handleSQLExceptionCusDAO(e);
        }
        return updateSuccessful;
    }

    /**
     * Reads all customers from the database, returning an ObservableList of CustomerModel objects. Executes a SQL query
     * that selects all customers from the database. Each row from the customer table is converted to a CustomerModel
     * object and added to the list. If a SQLException occurs, it is handled by a helper method named
     * handleSQLExceptionCustomerDAO. Use to populate GUI with customers.
     *
     * @return customerToTable ObservableList of CustomerModel objects representing customers.
     */
    @Override
    public ObservableList<CustomerModel> readCustomerAllCusDAO() {
        try {
            String sql = "SELECT c.Customer_ID, c.Customer_Name, c.Address, c.Postal_Code, c.Phone, c.Division_ID,\n" +
                    "       fld.Division_ID, fld.Division, fld.Country_ID,\n" +
                    "       co.Country_ID, co.Country\n" +
                    "FROM customers AS c\n" +
                    "INNER JOIN first_level_divisions AS fld ON c.Division_ID = fld.Division_ID\n" +
                    "INNER JOIN countries AS co ON fld.Country_ID = co.Country_ID";
            PreparedStatement readCustomerAllStatement = connectDB.prepareStatement(sql);
            ResultSet queryReturn = readCustomerAllStatement.executeQuery();

            for (; queryReturn.next();) {
                int customerID_CusDB = queryReturn.getInt("Customer_ID");
                int divisionID_CusDB = queryReturn.getInt("Division_ID");;
                int countryID_CusDB = queryReturn.getInt("Country_ID");
                String customerN_CusDB = queryReturn.getString("Customer_Name");
                String address_CusDB = queryReturn.getString("Address");
                String postalC_CusDB = queryReturn.getString("Postal_Code");
                String phone_CusDB = queryReturn.getString("Phone");
                String countryN_CusDB = queryReturn.getString("Country");
                String divisionN_CusDB = queryReturn.getString("Division");
                CustomerModel customerDB = new CustomerModel(customerID_CusDB, divisionID_CusDB, countryID_CusDB, customerN_CusDB, address_CusDB, postalC_CusDB,
                        phone_CusDB, countryN_CusDB, divisionN_CusDB);
                customerToTable.add(customerDB);
            }
        } catch (SQLException e) {
            handleSQLExceptionCusDAO(e);
        }
        return customerToTable;
    }

    /**
     * Helper method that displays error messages for methods in the AppointmentDAOIMPL class.
     *
     * @param title Title for the alert.
     * @param message Message displayed to user.
     */
    private void displayErrorCusDAO(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

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
    @Override
    public boolean createCustomerCusDAO(String customerN_TODB, String address_TODB, String postalC_TODB, String phone_TODB, int divisionID_TODB) {
        boolean createSuccessful = false;
        int createdCustomerCount = 0;
        try {
            String sql = "INSERT INTO customers SET\n" +
                    "Customer_Name = ?,\n" +
                    "Address = ?,\n" +
                    "Postal_Code = ?,\n" +
                    "Phone = ?,\n" +
                    "Division_ID = ?";
            PreparedStatement createCustomerStatement = connectDB.prepareStatement(sql);
            createCustomerStatement.setString(1, customerN_TODB);
            createCustomerStatement.setString(2, address_TODB);
            createCustomerStatement.setString(3, postalC_TODB);
            createCustomerStatement.setString(4, phone_TODB);
            createCustomerStatement.setInt(5, divisionID_TODB);
            int modifiedRowCount = createCustomerStatement.executeUpdate();
            if (modifiedRowCount > 0) {
                createSuccessful = true;
                createdCustomerCount = modifiedRowCount;
                displayInfoCusDAO("Create Customer", createdCustomerCount + " Customer with Name: " + customerN_TODB + " created.");
            } else {
                displayErrorCusDAO("Create Customer", "Customer Insert operation failed.");
            }
        } catch (SQLException e) {
            handleSQLExceptionCusDAO(e);
        }
        return createSuccessful;
    }

    /**
     * Helper method that displays information messages for methods in the AppointmentDAOIMPL class.
     *
     * @param title Title for the alert.
     * @param message Message shown to the user.
     */
    private void displayInfoCusDAO(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText("Successful Database Operation");
        alert.setContentText(message);
        alert.showAndWait();
    }

    /**
     * This method is used to create new data access objects.
     *
     * @return New instance of AppointmentDAOIMPL.
     */
    private AppointmentDAO createAppointmentDAOIMPLCusDAO() {
        return new AppointmentDAOIMPL();
    }

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
    @Override
    public boolean deleteCustomerCusDAO(int customerID_DelDB, String customerN_DelDB) {
        boolean deletionSuccess = false;
        int deletedCustomerCount = 0;
        try {
            AppointmentDAO newAppointmentDAO = createAppointmentDAOIMPLCusDAO();
            newAppointmentDAO.deleteAppointmentsByCustomerIdAppDAO(customerID_DelDB);
            String sql = "DELETE FROM customers\n" +
                    "WHERE (Customer_ID, Customer_Name) IN (\n" +
                    "    SELECT ? AS Customer_ID, ? AS Customer_Name\n" +
                    ")";
            PreparedStatement deleteCustomerStatement = connectDB.prepareStatement(sql);
            deleteCustomerStatement.setInt(1, customerID_DelDB);
            deleteCustomerStatement.setString(2, customerN_DelDB);
            int modifiedRowCount = deleteCustomerStatement.executeUpdate();
            if (modifiedRowCount > 0) {
                deletionSuccess = true;
                deletedCustomerCount = modifiedRowCount;
                displayInfoCusDAO("Delete Customer", deletedCustomerCount + " Customer with Customer ID: " + customerID_DelDB + " and Customer Name: " + customerN_DelDB  + " successfully deleted.");
            } else {
                displayErrorCusDAO("Delete Customer", "Customer with Customer ID: " + customerID_DelDB + " and Customer Name: " + customerN_DelDB + " failed deletion.");
            }
        } catch (SQLException e) {
            handleSQLExceptionCusDAO(e);
        }
        return deletionSuccess;
    }
}