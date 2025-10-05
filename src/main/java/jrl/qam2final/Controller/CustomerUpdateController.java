package jrl.qam2final.Controller;

import java.util.ResourceBundle;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import jrl.qam2final.DAO.*;
import jrl.qam2final.Helper.JDBCHelper;
import jrl.qam2final.Helper.DivisionByCountryHelper;
import javafx.event.ActionEvent;
import java.io.IOException;
import javafx.scene.Scene;
import javafx.scene.control.*;
import java.net.URL;
import javafx.fxml.FXMLLoader;
import jrl.qam2final.Helper.TranslationManagerHelper;
import javafx.fxml.Initializable;
import javafx.stage.Stage;
import jrl.qam2final.Model.CountryModel;
import jrl.qam2final.Model.CustomerModel;
import jrl.qam2final.Model.DivisionModel;
import javafx.scene.control.Alert;
import javafx.scene.Parent;
import java.util.Optional;

/**
 * Controller class for the Update Customer Screen. I paid close attention to setting up all FXML elements with clear
 * IDs and annotations. This will make it easier to establish translation keys for use with the TranslationManagerHelper
 * class in the future and achieve full multi-language support for all FXML elements in the GUI. Though this proactive
 * approach will help a great deal towards full multi-language support, translating dynamic values from the database
 * will require more time and effort.
 *
 * @author Jeffrey Robert Lynch
 */
public class CustomerUpdateController implements Initializable {

    public String custN_ST;
    public int counID_IN;
    public String addr_ST;
    public String postC_ST;
    public String phone_ST;
    @FXML
    public Label updateCustomerHeaderLabel;
    @FXML
    public Label updateCustomerIdLabel;
    @FXML
    public Label updateCustomerNameLabel;
    @FXML
    public Label updateCustomerAddressLabel;
    @FXML
    public Label updateCustomerPostalCodeLabel;
    @FXML
    public Label updateCustomerPhoneNumberLabel;
    @FXML
    public TextField updateCustomerIdTextField;
    @FXML
    public TextField updateCustomerNameTextField;
    @FXML
    public TextField updateCustomerAddressTextField;
    @FXML
    public TextField updateCustomerPostalCodeTextField;
    @FXML
    public TextField updateCustomerPhoneNumberTextField;
    @FXML
    public Label updateCustomerCountryLabel;
    @FXML
    public ComboBox<CountryModel> updateCustomerCountryComboBox;
    @FXML
    public Label updateCustomerDivisionLabel;
    @FXML
    public ComboBox<DivisionModel> updateCustomerDivisionComboBox;
    @FXML
    public Button updateCustomerSaveButton;
    @FXML
    public Button updateCustomerCancelButton;

    CustomerModel selectCustomer = null;

    /**
     * This method cancels the update of a selected customer. With the aid of a helper method, a confirmation dialogue
     * informs the user that all unsaved changes will be lost. If the user confirms that they wish to cancel, the user
     * returns to the Customer Main Screen.
     *
     * @param actionEvent Action Event triggered by the user pressing the "cancel" button.
     */
    public void onActionCancelUpdatedCustomerCUC(ActionEvent actionEvent) {
        try {
            if (displayConfirmationCUC("Cancel Customer Update", "Changes have not been saved. Do you wish to cancel and return to Customer Main?")) {
                String fxmlFileName = "/jrl/qam2final/CustomerMainFXML.fxml";
                URL fxmlURL = getClass().getResource(fxmlFileName);
                if (fxmlURL == null) {
                    throw new IOException("FXML file not found: " + fxmlFileName);
                }
                FXMLLoader loader = new FXMLLoader(fxmlURL);
                Parent root = loader.load();
                Stage stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
                stage.setScene(new Scene(root));
                System.out.println("Loaded FXML: " + fxmlFileName);
                JDBCHelper.endDBConnection();
            }
        } catch (Exception e) {
            displayErrorCUC("Failed to load FXML");
        }
    }

    /**
     * This method triggers when the "save" button is clicked. It validates the user's input by calling
     * validateCustomerUpdateCUC to ensure the user's input is valid. If all validation checks are passed, it saves the
     * updated information to the database by calling saveCustomerUpdateCUC. After the updates are saved, the method
     * redirects the user to Customer Main.
     *
     * @param actionEvent Action Event triggered by the user clicking the "save" button.
     */
    public void onActionSaveUpdatedCustomerCUC(ActionEvent actionEvent) {
        try {
            if (validateCustomerUpdateCUC()) {
                int customerId = selectCustomer.getCustID_IN();
                int divisionId = updateCustomerDivisionComboBox.getValue().getDiviID_IN();
                saveCustomerUpdateCUC(customerId, divisionId);
                navigateToCustomerMainCUC(actionEvent);
            }
        } catch (Exception e) {
            displayErrorCUC("Failed to load FXML: " + e.getMessage());
        }
    }

    /**
     * Method to handle navigation to the Customer Main Screen after the save button is clicked. An error is
     * displayed if the FXML fails to load.
     *
     * @param actionEvent Action Event triggered by the user pressing the "Save" button.
     */
    private void navigateToCustomerMainCUC(ActionEvent actionEvent) {
        try {
            String fxmlFileName = "/jrl/qam2final/CustomerMainFXML.fxml";
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
            displayErrorCUC("Failed to load FXML");
        }
    }

    /**
     * This method performs validation for customer updates before they can be saved to the database. It builds a string
     * of error messages to display to the user.
     *
     * @return true If the user has not left null fields, otherwise false.
     */
    private boolean validateCustomerUpdateCUC() {
        boolean validInput = true;
        StringBuilder errorMessageBuilder = new StringBuilder();
        addr_ST = updateCustomerAddressTextField.getText();
        custN_ST = updateCustomerNameTextField.getText();
        phone_ST = updateCustomerPhoneNumberTextField.getText();
        postC_ST = updateCustomerPostalCodeTextField.getText();
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
            displayErrorCUC(errorMessageBuilder.toString().trim());
        }
        return validInput;
    }

    /**
     * Saves the updated customer to the database with user initiated changes.
     *
     * @param customerId Customer ID for customer receiving updates.
     * @param divisionId Division ID for the customer receiving updates.
     */
    private void saveCustomerUpdateCUC(int customerId, int divisionId) {
        CustomerDAO newCustomerDAO = createCustomerDAOCUC();
        newCustomerDAO.updateCustomerCusDAO(customerId, custN_ST, addr_ST, postC_ST, phone_ST, divisionId);
    }

    /**
     * Helper method that displays confirmation dialogues for methods in the CustomerUpdateController class. It takes
     * title strings and contentText strings as parameters.
     *
     * @param title       Title for confirmation dialog
     * @param contentText Content for confirmation dialog.
     * @return True if user confirms by pressing "ok", otherwise false.
     */
    private boolean displayConfirmationCUC(String title, String contentText) {
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
    public void onActionDivisionFromCountryCUC(ActionEvent actionEvent) {
        counID_IN = updateCustomerCountryComboBox.getValue().getCounID_IN();
        updateCustomerDivisionComboBox.setItems(DivisionByCountryHelper.getFilteredDivisions(counID_IN));
        updateCustomerDivisionComboBox.getSelectionModel().selectFirst();
    }

    /**
     * This method is used to create new data access objects.
     *
     * @return New instance of CountryDAOIMPL.
     */
    private CountryDAO createCountryDAOCUC() {
        return new CountryDAOIMPL();
    }

    /**
     * This method is used to create new data access objects.
     *
     * @return New instance of CustomerDAOIMPL.
     */
    private CustomerDAO createCustomerDAOCUC() {
        return new CustomerDAOIMPL();
    }

    /**
     * Helper method that displays error messages for methods in the CustomerCreateController class. It takes
     * customMessage strings as a parameter and displays the specifics of the error to the user.
     *
     * @param customMessage Custom error message displayed to user
     */
    private void displayErrorCUC(String customMessage) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error Alert");
        alert.setHeaderText("Error Encountered");
        alert.setContentText(customMessage);
        alert.showAndWait();
    }

    /**
     * Calls two methods to populate fields and combo boxes with the values of the customer selected for update.
     *
     * @param selection CustomerModel for the customer selected for update.
     */
    public void updateCustomerPopulateCUC(CustomerModel selection) {
        JDBCHelper.startDBConnection();
        selectCustomer = selection;
        populateTextFieldsCUC(selection);
        populateComboBoxesCUC(selection);
    }

    /**
     * Populates Date Pickers.
     */
    private void populateTextFieldsCUC(CustomerModel selection) {
        updateCustomerNameTextField.setText(selection.getCustN_ST());
        updateCustomerAddressTextField.setText(selection.getAddr_ST());
        updateCustomerPostalCodeTextField.setText(selection.getPostC_ST());
        updateCustomerPhoneNumberTextField.setText(selection.getPhone_ST());
    }

    /**
     * Populates Combo Boxes.
     */
    private void populateComboBoxesCUC(CustomerModel selection) {
        CountryDAO countryDAO = createCountryDAOCUC();
        ObservableList<CountryModel> allCountries = countryDAO.readCountryAllCouDAO();
        CountryModel selectedCountry = allCountries.stream()
                .filter(country -> country.getCounID_IN() == selection.getCounID_IN())
                .findFirst()
                .orElse(null);
        updateCustomerCountryComboBox.getSelectionModel().select(selectedCountry);
        updateCustomerDivisionComboBox.setItems(DivisionByCountryHelper.getFilteredDivisions(selectedCountry.getCounID_IN()));
        DivisionModel selectedDivision = DivisionByCountryHelper.getFilteredDivisions(selectedCountry.getCounID_IN()).stream()
                .filter(division -> division.getDiviID_IN() == selection.getDiviID_IN())
                .findFirst()
                .orElse(null);
        updateCustomerDivisionComboBox.getSelectionModel().select(selectedDivision);
    }

    /**
     * Initialization for the CustomerUpdateController class. Opens a connection to the database to retrieve countries.
     *
     * @param url            URL
     * @param resourceBundle Resource Bundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        JDBCHelper.startDBConnection();
        CountryDAO newCountryDAO = createCountryDAOCUC();
        updateCustomerCountryComboBox.setItems(newCountryDAO.readCountryAllCouDAO());
    }
}