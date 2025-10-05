package jrl.qam2final.Controller;

import javafx.fxml.FXML;
import jrl.qam2final.DAO.*;
import jrl.qam2final.Helper.JDBCHelper;
import jrl.qam2final.Helper.DivisionByCountryHelper;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.Parent;
import jrl.qam2final.Helper.TranslationManagerHelper;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import java.util.Optional;
import java.util.ResourceBundle;
import jrl.qam2final.Model.CountryModel;
import jrl.qam2final.Model.DivisionModel;
import javafx.fxml.FXMLLoader;
import javafx.event.ActionEvent;
import javafx.scene.control.Label;
import java.io.IOException;
import java.net.URL;

/**
 * Controller class for the New Customer Screen. I paid close attention to setting up all FXML elements with clear IDs
 * and annotations. This will make it easier to establish translation keys for use with the TranslationManagerHelper
 * class in the future and achieve full multi-language support for all FXML elements in the GUI. Though this proactive
 * approach will help a great deal towards full multi-language support, translating dynamic values from the database
 * will require more time and effort.
 *
 * @author Jeffrey Robert Lynch
 */
public class CustomerCreateController implements Initializable {

    @FXML
    public Label newCustomerHeaderLabel;
    @FXML
    public TextField newCustomerIdTextField;
    @FXML
    public Label newCustomerIdLabel;
    @FXML
    public TextField newCustomerNameTextField;
    @FXML
    public Label newCustomerNameLabel;
    @FXML
    public TextField newCustomerAddressTextField;
    @FXML
    public Label newCustomerAddressLabel;
    @FXML
    public TextField newCustomerPostalCodeTextField;
    public String addr_ST;
    public String postC_ST;
    @FXML
    public Label newCustomerPostalCodeLabel;
    @FXML
    public TextField newCustomerPhoneNumberTextField;
    @FXML
    public Label newCustomerPhoneNumberLabel;
    public String phone_ST;
    @FXML
    public Label newCustomerCountryLabel;
    private int counID_IN;
    public String custN_ST;
    @FXML
    public ComboBox<CountryModel> newCustomerCountryComboBox;
    @FXML
    public Label newCustomerDivisionLabel;
    @FXML
    public ComboBox<DivisionModel> newCustomerDivisionComboBox;
    @FXML
    public Button newCustomerSaveButton;
    @FXML
    public Button newCustomerCancelButton;

    /**
     * This method cancels the creation of a new customer. With the aid of a helper method, a confirmation dialogue
     * informs the user that all unsaved changes will be lost. If the user confirms that they wish to cancel, the user
     * returns to the Customer Main Screen.
     *
     * @param actionEvent Action Event triggered by the user pressing the "cancel" button.
     */
    public void onActionCancelCreatedCustomerCCC(ActionEvent actionEvent) {
        try {
            String fxmlFileName = "/jrl/qam2final/CustomerMainFXML.fxml";
            URL fxmlURL = getClass().getResource(fxmlFileName);
            if (fxmlURL == null) {
                throw new IOException("FXML file not found: " + fxmlFileName);
            }
            if (displayConfirmationCCC("Cancel New Customer", "Changes have not been saved. Do you wish to cancel and return to Customer Main?")) {
                FXMLLoader loader = new FXMLLoader(fxmlURL);
                Parent root = loader.load();
                Stage stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
                stage.setScene(new Scene(root));
                System.out.println("Loaded FXML: " + fxmlFileName);
                JDBCHelper.endDBConnection();
            }
        } catch (Exception e) {
            displayErrorCCC("Failed to load FXML");
        }
    }

    /**
     * This method triggers when the "save" button is clicked. It validates the user's input by calling
     * validateCreatedCustomerCCC to ensure the user's input is valid. If all validation checks are passed, it saves the
     * new customer and their information to the database by calling saveCreatedCustomerCCC. After the updates are
     * saved, the method redirects the user to Customer Main.
     *
     * @param actionEvent Action Event triggered by the user clicking the "save" button.
     */
    public void onActionSaveCreatedCustomerCCC(ActionEvent actionEvent) {
        try {
            if (validateCreatedCustomerCCC()) {
                int divisionId = newCustomerDivisionComboBox.getSelectionModel().getSelectedItem().getDiviID_IN();
                saveCreatedCustomerCCC(divisionId);
                navigateToCustomerMainCCC(actionEvent);
            }
        } catch (Exception e) {
            displayErrorCCC("Failed to load FXML: " + e.getMessage());
        }
    }

    /**
     * Method to handle navigation to the Customer Main Screen after the save button is clicked. An error is
     * displayed if the FXML fails to load.
     *
     * @param actionEvent Action Event triggered by the user pressing the "Save" button.
     */
    private void navigateToCustomerMainCCC(ActionEvent actionEvent) {
        try {
            String fxmlFileName = "/jrl/qam2final/CustomerMainFXML.fxml";
            URL fxmlURL = getClass().getResource(fxmlFileName);
            if (fxmlURL == null) {
                throw new IOException("FXML file not found: " + fxmlFileName);
            }
            Stage stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
            Parent root = FXMLLoader.load(fxmlURL);
            stage.setScene(new Scene(root));
            System.out.println("Loaded FXML: " + fxmlFileName);
            stage.show();
        } catch (IOException e) {
            displayErrorCCC("Failed to load FXML");
        }
    }

    /**
     * This method performs validation for new customer fields before they can be saved to the database. It builds a
     * string of error messages to display to the user.
     *
     * @return true If the user has not left null fields, otherwise false.
     */
    private boolean validateCreatedCustomerCCC() {
        boolean validInput = true;
        StringBuilder errorMessageBuilder = new StringBuilder();
        addr_ST = newCustomerAddressTextField.getText();
        custN_ST = newCustomerNameTextField.getText();
        phone_ST = newCustomerPhoneNumberTextField.getText();
        postC_ST = newCustomerPostalCodeTextField.getText();
        if (addr_ST.trim().isEmpty()) {
            errorMessageBuilder.append("Customer Address field is empty.\n");
            validInput = false;
        }
        if (custN_ST.trim().isEmpty()) {
            errorMessageBuilder.append("Customer Name field is empty.\n");
            validInput = false;
        }
        if (phone_ST.trim().isEmpty()) {
            errorMessageBuilder.append("Customer Phone Number field is empty.\n");
            validInput = false;
        }
        if (postC_ST.trim().isEmpty()) {
            errorMessageBuilder.append("Customer Postal Code field is empty.\n");
            validInput = false;
        }
        if (!validInput) {
            displayErrorCCC(errorMessageBuilder.toString().trim());
        }
        return validInput;
    }

    /**
     * Saves the new customer to the database with user initiated inputs.
     *
     * @param divisionId Division ID for the new customer.
     */
    private void saveCreatedCustomerCCC(int divisionId) {
        CustomerDAO newCustomerDAO = createCustomerDAOCCC();
        newCustomerDAO.createCustomerCusDAO(custN_ST, addr_ST, postC_ST, phone_ST, divisionId);
    }

    /**
     * Helper method that displays confirmation dialogues for methods in the CustomerCreateController class. It takes
     * title strings and contentText strings as parameters.
     *
     * @param title       Title for confirmation dialog
     * @param contentText Content for confirmation dialog.
     * @return True if user confirms by pressing "ok", otherwise false.
     */
    private boolean displayConfirmationCCC(String title, String contentText) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle(title);
        alert.setContentText(contentText);
        Optional<ButtonType> result = alert.showAndWait();
        return result.isPresent() && result.get() == ButtonType.OK;
    }

    /**
     * Method to update the options offered in the division combo box based on the country selected from the country
     * combo box. By default, the first item in the division combo box is selected.
     *
     * @param actionEvent Action Event triggered by user selecting a country in the combo box.
     */
    public void onActionDivisionFromCountryCCC(ActionEvent actionEvent) {
        counID_IN = newCustomerCountryComboBox.getValue().getCounID_IN();
        newCustomerDivisionComboBox.setItems(DivisionByCountryHelper.getFilteredDivisions(counID_IN));
        newCustomerDivisionComboBox.getSelectionModel().select(0);
    }

    /**
     * Helper method that displays error messages for methods in the CustomerCreateController class. It takes
     * customMessage strings as a parameter and displays the specifics of the error to the user.
     *
     * @param customMessage Custom error message displayed to user
     */
    private void displayErrorCCC(String customMessage) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error Alert");
        alert.setHeaderText("Error Encountered");
        alert.setContentText(customMessage);
        alert.showAndWait();
    }

    /**
     * This method is used to create new data access objects.
     *
     * @return New instance of CountryDAOIMPL.
     */
    private CountryDAO createCountryDAOCCC() {
        return new CountryDAOIMPL();
    }

    /**
     * This method is used to create new data access objects.
     *
     * @return New instance of CustomerDAOIMPL.
     */
    private CustomerDAO createCustomerDAOCCC() {
        return new CustomerDAOIMPL();
    }

    /**
     * Initialization for the CustomerCreateController class. Opens a connection to the database to retrieve countries.
     * Divisions are updated based on the country selection in the country combo box. By default, the first item in the
     * division combo box is selected.
     *
     * @param url            URL
     * @param resourceBundle Resource Bundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            JDBCHelper.startDBConnection();
            CountryDAO newCountryDAO = createCountryDAOCCC();
            newCustomerCountryComboBox.setItems(newCountryDAO.readCountryAllCouDAO());
            newCustomerCountryComboBox.getSelectionModel().select(0);
            counID_IN = newCustomerCountryComboBox.getValue().getCounID_IN();
            newCustomerDivisionComboBox.setItems(DivisionByCountryHelper.getFilteredDivisions(counID_IN));
            newCustomerDivisionComboBox.getSelectionModel().select(0);
        } catch (Exception e) {
            displayErrorCCC("Initialization Failed.");
        }
    }
}