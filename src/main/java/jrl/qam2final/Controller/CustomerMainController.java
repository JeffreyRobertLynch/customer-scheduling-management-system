package jrl.qam2final.Controller;

import jrl.qam2final.DAO.CustomerDAO;
import jrl.qam2final.DAO.CustomerDAOIMPL;
import jrl.qam2final.Helper.JDBCHelper;
import javafx.fxml.FXML;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import java.util.Optional;
import java.util.ResourceBundle;
import jrl.qam2final.Helper.TranslationManagerHelper;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.Parent;
import java.net.URL;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import jrl.qam2final.Model.CustomerModel;
import java.io.IOException;

/**
 * Controller class for the Customer Main Screen. I paid close attention to setting up all FXML elements with clear IDs
 * and annotations. This will make it easier to establish translation keys for use with the TranslationManagerHelper
 * class in the future and achieve full multi-language support for all FXML elements in the GUI. Though this proactive
 * approach will help a great deal towards full multi-language support, translating dynamic values from the database
 * (formatting customer phone numbers, for example) will require more time and effort.
 *
 * @author Jeffrey Robert Lynch
 */
public class CustomerMainController implements Initializable {

    @FXML
    public Button onActionCMCToCCCButton;
    @FXML
    public Button onActionCMCToCUCButton;
    @FXML
    public Button onActionCustomerDeleteButton;
    @FXML
    public Button onActionCMCToAMCButton;
    @FXML
    public Button onActionCMCToRMCButton;
    @FXML
    public Button onActionExitButton;
    @FXML
    public TableView<CustomerModel> customerMainTableView;
    @FXML
    public TableColumn<CustomerModel, String> customerMainIdColumn;
    @FXML
    public TableColumn<CustomerModel, String> customerMainNameColumn;
    @FXML
    public TableColumn<CustomerModel, String> customerMainAddressColumn;
    @FXML
    public TableColumn<CustomerModel, String> customerMainPostalCodeColumn;
    @FXML
    public TableColumn<CustomerModel, String> customerMainPhoneNumberColumn;
    @FXML
    public TableColumn<CustomerModel, String> customerMainDivisionColumn;
    @FXML
    public Label customerMainTitleLabel;

    /**
     * LAMBDA EXPRESSION # 2.1
     * Method to delete selected customer when the "Delete Customer" button is pushed. An error dialogue is displayed
     * if the user has not selected a customer. When the button is pressed with a customer selected, the user is
     * prompted, via a confirmation dialog, to confirm deletion. If the user confirms deletion, the selected customer is
     * removed from the database and the table is updated to reflect this change.
     * LAMBDA EXPRESSION # 2.1: The lambda expression for the delete method defines the behavior depending on whether
     * selectedCustomer is null. ifPresentOrElse takes two arguments, a consumer if a customer is selected or a runnable
     * if null. If null, an error message is displayed. If not null, deletion occurs. This lambda is beneficial because
     * it is concise and easy to read. The logic is clear whether the value is present or null. This is a good approach
     * for this method, especially if a developer wants to include further functionality later. For example, future
     * functionality might require multiple customers to be deleted at once. In this case, of course, present or null
     * would no longer work for three or more behaviors, so additional changes to the logic would be required.
     *
     * @param actionEvent Action event triggered by the delete button.
     */
    public void onActionDeleteCustomerCMC(ActionEvent actionEvent) {
        Optional<CustomerModel> selectedCustomer = Optional.ofNullable(customerMainTableView.getSelectionModel().getSelectedItem());
        selectedCustomer.ifPresentOrElse(
                customer -> {
                    try {
                        JDBCHelper.startDBConnection();
                        CustomerDAO newCustomerDAO = createCustomerDAOCMC();
                        int customerId = customer.getCustID_IN();
                        String customerName = customer.getCustN_ST();
                        if (displayConfirmationCMC("Delete Customer", "This customer and any scheduled appointments for the customer will be deleted forever. Do you wish to delete the customer?")) {
                            if (newCustomerDAO.deleteCustomerCusDAO(customerId, customerName)) {
                                customerMainTableView.setItems(newCustomerDAO.readCustomerAllCusDAO());
                            } else {
                                displayErrorCMC("Failed to delete customer.");
                            }
                        }
                    } catch (Exception e) {
                        displayErrorCMC("Failed to delete customer.");
                    } finally {
                        JDBCHelper.endDBConnection();
                    }
                },
                () -> displayErrorCMC("No customer selected. Customers can only be deleted when selected.")
        );
    }

    /**
     * Method to handle navigation to the New Customer Screen. An error is displayed if the FXML fails to load.
     *
     * @param actionEvent Action Event triggered by the user pressing the "New Customer" button.
     */
    @FXML
    public void onActionNavigateCustomerCreateCMC(ActionEvent actionEvent) {
        try {
            String fxmlFileName = "/jrl/qam2final/CustomerCreateFXML.fxml";
            URL fxmlURL = getClass().getResource(fxmlFileName);
            if (fxmlURL == null) {
                throw new IOException("FXML file not found: " + fxmlFileName);
            }
            Stage stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
            Parent root = FXMLLoader.load(fxmlURL);
            stage.setScene(new Scene(root));
            System.out.println("Loaded FXML: " + fxmlFileName);
            stage.show();
        } catch (Exception e) {
            displayErrorCMC("Failed to load FXML");
        }
    }

    /**
     * Method to handle navigation to the Update Customer Screen. If a customer has not been selected for update, an
     * error is displayed to the user and navigation does not occur. An error is displayed if the FXML fails to load.
     * The values of the selected customer are saved for modification on the Update Customer Screen.
     *
     * @param actionEvent Action Event triggered by the user pressing the "Update Customer" button.
     */
    public void onActionNavigateCustomerUpdateCMC(ActionEvent actionEvent) {
        try {
            String fxmlFileName = "/jrl/qam2final/CustomerUpdateFXML.fxml";
            URL fxmlURL = getClass().getResource(fxmlFileName);
            if (fxmlURL == null) {
                throw new IOException("FXML file not found: " + fxmlFileName);
            }
            FXMLLoader loader = new FXMLLoader(fxmlURL);
            Parent root = loader.load();
            CustomerUpdateController updateCustomerController = loader.getController();
            CustomerModel selectedCustomer = customerMainTableView.getSelectionModel().getSelectedItem();
            if (selectedCustomer == null) {
                displayErrorCMC("No customer selected. Customers can only be updated when selected.");
                return;
            }
            updateCustomerController.updateCustomerPopulateCUC(selectedCustomer);
            Stage stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));
            System.out.println("Loaded FXML: " + fxmlFileName);
            stage.show();
        } catch (Exception e) {
            displayErrorCMC("Failed to load FXML");
        }
    }

    /**
     * Helper method that displays error messages for methods in the CustomerMainController class. It takes
     * customMessage strings as a parameter and displays the specifics of the error to the user.
     *
     * @param customMessage Custom error message displayed to user
     */
    private void displayErrorCMC(String customMessage) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error Alert");
        alert.setHeaderText("Error Encountered");
        alert.setContentText(customMessage);
        alert.showAndWait();
    }

    /**
     * Method for exiting the application via a button press. With the aid of a helper method, a confirmation dialog to
     * is displayed to the user to confirm exit.
     *
     * @param actionEvent ActionEvent trigger is the user pressing the "exit" button.
     */
    public void onActionExitCMC(ActionEvent actionEvent) {
        if (displayConfirmationCMC("Exit Application", "Do you wish to exit the Customer Appointment Management System?")) {
            System.exit(0);
        }
    }

    /**
     * Method to handle navigation to the Appointment Main Screen. An error is displayed if the FXML fails to load.
     *
     * @param actionEvent Action Event triggered by the user pressing the "Appointment Main" button.
     */
    @FXML
    private void onActionNavigateAppointmentMainCMC(ActionEvent actionEvent) {
        try {
            String fxmlFileName = "/jrl/qam2final/AppointmentMainFXML.fxml";
            URL fxmlURL = getClass().getResource(fxmlFileName);
            if (fxmlURL == null) {
                throw new IOException("FXML file not found: " + fxmlFileName);
            }
            FXMLLoader loader = new FXMLLoader(fxmlURL);
            Parent root = loader.load();
            Stage stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
            System.out.println("Loaded FXML: " + fxmlFileName);
        } catch (Exception e) {
            displayErrorCMC("Failed to load FXML");
        }
    }

    /**
     * Method to handle navigation to the Reports Main Screen. An error is displayed if the FXML fails to load.
     *
     * @param actionEvent Action Event triggered by the user pressing the "Reports Main" button.
     */
    @FXML
    private void onActionNavigateReportMainCMC(ActionEvent actionEvent) {
        try {
            String fxmlFileName = "/jrl/qam2final/ReportMainFXML.fxml";
            URL fxmlURL = getClass().getResource(fxmlFileName);
            if (fxmlURL == null) {
                throw new IOException("FXML file not found: " + fxmlFileName);
            }
            FXMLLoader loader = new FXMLLoader(fxmlURL);
            Parent root = loader.load();
            Stage stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
            System.out.println("Loaded FXML: " + fxmlFileName);
        } catch (Exception e) {
            displayErrorCMC("Failed to load FXML");
        }
    }

    /**
     * Helper method that displays confirmation dialogues for methods in the CustomerMainController class. It takes
     * title strings and contentText strings as parameters.
     *
     * @param title       Title for confirmation dialog
     * @param contentText Content for confirmation dialog.
     * @return True if user confirms by pressing "ok", otherwise false.
     */
    private boolean displayConfirmationCMC(String title, String contentText) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle(title);
        alert.setContentText(contentText);
        Optional<ButtonType> result = alert.showAndWait();
        return result.isPresent() && result.get() == ButtonType.OK;
    }

    /**
     * This method is used to create new data access objects.
     *
     * @return New instance of CustomerDAOIMPL.
     */
    private CustomerDAO createCustomerDAOCMC() {
        return new CustomerDAOIMPL();
    }

    /**
     * Initialization for the CustomerMainController. Populates the table with customers from the database, so they can
     * be viewed, searched, updated, or deleted.
     *
     * @param url            URL
     * @param resourceBundle Resource Bundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        JDBCHelper.startDBConnection();
        CustomerDAO newCustomerDAO = createCustomerDAOCMC();
        customerMainTableView.setItems(newCustomerDAO.readCustomerAllCusDAO());
        customerMainIdColumn.setCellValueFactory(new PropertyValueFactory<>("custID_IN"));
        customerMainNameColumn.setCellValueFactory(new PropertyValueFactory<>("custN_ST"));
        customerMainAddressColumn.setCellValueFactory(new PropertyValueFactory<>("addr_ST"));
        customerMainPostalCodeColumn.setCellValueFactory(new PropertyValueFactory<>("postC_ST"));
        customerMainPhoneNumberColumn.setCellValueFactory(new PropertyValueFactory<>("phone_ST"));
        customerMainDivisionColumn.setCellValueFactory(new PropertyValueFactory<>("diviN_ST"));
    }
}

