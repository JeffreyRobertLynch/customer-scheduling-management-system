package jrl.qam2final.Controller;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import jrl.qam2final.DAO.*;
import jrl.qam2final.Helper.JDBCHelper;
import javafx.event.ActionEvent;
import javafx.scene.Parent;
import jrl.qam2final.Helper.TranslationManagerHelper;
import javafx.scene.Scene;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.scene.control.*;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.stage.Stage;
import jrl.qam2final.Model.AppointmentModel;
import jrl.qam2final.Model.ContactModel;
import jrl.qam2final.Model.CustomerModel;
import jrl.qam2final.Model.UserModel;
import jrl.qam2final.Helper.TimeConversionHelper;
import java.io.IOException;
import java.net.URL;
import java.time.*;

/**
 * Controller class for the New Appointment Screen. I paid close attention to setting up all FXML elements with clear
 * IDs and annotations. This will make it easier to establish translation keys for use with the TranslationManagerHelper
 * class in the future and achieve full multi-language support for all FXML elements in the GUI. Though this proactive
 * approach will help a great deal towards full multi-language support, translating dynamic values from the database
 * (appointment types, for example) will require more time and effort.
 *
 * @author Jeffrey Robert Lynch
 */
public class AppointmentCreateController implements Initializable {

    @FXML
    public Label appointmentNewIdLabel;
    @FXML
    public Label appointmentNewTitleLabel;
    public String titl_ST;
    @FXML
    public Label appointmentNewDescriptionLabel;
    public String desc_ST;
    @FXML
    public Label appointmentNewLocationLabel;
    public String loca_ST;
    @FXML
    public Label appointmentNewTypeLabel;
    public String type_ST;
    @FXML
    public Label appointmentNewStartDateLabel;
    public LocalDate staD_LD;
    @FXML
    public Label appointmentNewStartTimeLabel;
    public LocalTime staT_LT;
    @FXML
    public Label appointmentNewEndDateLabel;
    public LocalDate endD_LD;
    @FXML
    public Label appointmentNewEndTimeLabel;
    public LocalTime endT_LT;
    @FXML
    public Label appointmentNewCustomerLabel;
    public int custID_IN;
    @FXML
    public Label appointmentNewUserLabel;
    public int userID_IN;
    @FXML
    public Label appointmentNewContactLabel;
    public int contID_IN;
    @FXML
    public TextField appointmentNewIdTextField;
    @FXML
    public TextField appointmentNewTitleTextField;
    @FXML
    public TextField appointmentNewDescriptionTextField;
    @FXML
    public TextField appointmentNewLocationTextField;
    @FXML
    public TextField appointmentNewTypeTextField;
    @FXML
    public DatePicker appointmentNewStartDatePicker;
    @FXML
    public ComboBox<LocalTime>  newAppointmentStartComboBox;
    @FXML
    public DatePicker appointmentNewEndDatePicker;
    @FXML
    public ComboBox<LocalTime>  newAppointmentEndComboBox;
    @FXML
    public ComboBox<CustomerModel> newAppointmentCustomerComboBox;
    @FXML
    public ComboBox<UserModel> newAppointmentUserComboBox;
    @FXML
    public ComboBox<ContactModel> newAppointmentContactComboBox;
    @FXML
    public Button onActionSaveNewAppointmentButton;
    @FXML
    public Button onActionCancelNewAppointmentButton;
    @FXML
    public Label newAppointmentTitleLabel;
    public LocalDateTime staDT_LDT;
    public LocalDateTime endDT_LDT;

    /**
     * Helper method that displays error messages for methods in the AppointmentCreateController class. It takes
     * customMessage strings as a parameter and displays the specifics of the error to the user.
     *
     * @param customMessage Custom error message displayed to user
     */
    private void displayErrorACC(String customMessage) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error Alert");
        alert.setHeaderText("Error Encountered");
        alert.setContentText(customMessage);
        alert.showAndWait();
    }

    /**
     * This method triggers when the "save" button is clicked. It validates the user's input by calling
     * validateAppointmentUpdateACC to ensure the user's input is valid. If all validation checks are passed, it saves
     * the new appointment to the database by calling saveAppointmentUpdateACC. After the updates are saved, the method
     * redirects the user to Appointment Main.
     *
     * @param actionEvent Action Event triggered by the user clicking the "save" button.
     */
    public void onActionSaveCreatedAppointmentACC(ActionEvent actionEvent) {
        try {
            if (validateCreatedAppointmentACC()) {
                saveCreatedAppointmentACC();
                navigateAppointmentMainACC(actionEvent);
            }
        } catch (Exception e) {
            displayErrorACC("Failed to load FXML");
        }
    }

    /**
     * Method to handle navigation to the Appointment Main Screen after the save button is clicked. An error is
     * displayed if the FXML fails to load.
     *
     * @param actionEvent Action Event triggered by the user pressing the "Save" button.
     */
    @FXML
    public void navigateAppointmentMainACC(ActionEvent actionEvent) {
        try {
            String fxmlFileName = "/jrl/qam2final/AppointmentMainFXML.fxml";
            URL fxmlURL = getClass().getResource(fxmlFileName);
            if (fxmlURL == null) {
                throw new IOException("FXML file not found: " + fxmlFileName);
            }
            Stage stage = (Stage)((Button)actionEvent.getSource()).getScene().getWindow();
            Parent root = FXMLLoader.load(fxmlURL);
            stage.setScene(new Scene(root));
            System.out.println("Loaded FXML: " + fxmlFileName);
            stage.show();
            JDBCHelper.endDBConnection();
        } catch (IOException e) {
            displayErrorACC("Failed to load FXML");
        }
    }

    /**
     * This method performs validation for new appointments. User input is validated before it can be saved to the
     * database. The method builds a string of error messages to display to the user if there are null fields.
     * The method also ensures appointment times are valid, including appointment overlaps. Each check, if failed,
     * provides a specific message to the user.
     *
     * @return true If the user has not left null fields or invalid times, otherwise false.
     */
    private boolean validateCreatedAppointmentACC() {
        boolean invalidInput = false;
        StringBuilder errorMessageBuilder = new StringBuilder();
        custID_IN = newAppointmentCustomerComboBox.getSelectionModel().getSelectedItem().getCustID_IN();
        userID_IN = newAppointmentUserComboBox.getSelectionModel().getSelectedItem().getUserID_IN();
        contID_IN = newAppointmentContactComboBox.getSelectionModel().getSelectedItem().getContID_IN();
        desc_ST = appointmentNewDescriptionTextField.getText();
        loca_ST = appointmentNewLocationTextField.getText();
        titl_ST = appointmentNewTitleTextField.getText();
        type_ST = appointmentNewTypeTextField.getText();
        staD_LD = appointmentNewStartDatePicker.getValue();
        staT_LT = newAppointmentStartComboBox.getSelectionModel().getSelectedItem();
        staDT_LDT = LocalDateTime.of(staD_LD, staT_LT);
        endD_LD = appointmentNewEndDatePicker.getValue();
        endT_LT = newAppointmentEndComboBox.getSelectionModel().getSelectedItem();
        endDT_LDT = LocalDateTime.of(endD_LD, endT_LT);
        if (desc_ST.trim().isEmpty()) {
            errorMessageBuilder.append("Description field is empty.\n");
            invalidInput = true;
        }
        if (loca_ST.trim().isEmpty()) {
            errorMessageBuilder.append("Location field is empty.\n");
            invalidInput = true;
        }
        if (titl_ST.trim().isEmpty()) {
            errorMessageBuilder.append("Title field is empty.\n");
            invalidInput = true;
        }
        if (type_ST.trim().isEmpty()) {
            errorMessageBuilder.append("Type field is empty.\n");
            invalidInput = true;
        }
        if (!invalidInput) {
            long maxDurationHours = 8;
            long durationHours = Duration.between(staDT_LDT, endDT_LDT).toHours();
            if (durationHours > maxDurationHours) {
                errorMessageBuilder.append("Appointment duration cannot exceed ").append(maxDurationHours).append(" hours.");
                invalidInput = true;
            } else {
                AppointmentDAO newAppointmentDAO = createAppointmentDAOACC();
                if (staDT_LDT.isAfter(endDT_LDT)) {
                    errorMessageBuilder.append("Appointment start must be before appointment end.");
                    invalidInput = true;
                } else if (!newAppointmentDAO.checkAppointmentStartAppDAO(staDT_LDT) || !newAppointmentDAO.checkAppointmentEndAppDAO(endDT_LDT)) {
                    errorMessageBuilder.append("Appointments cannot be scheduled outside of regular business hours.");
                    invalidInput = true;
                } else if (newAppointmentDAO.createdAppointmentOverlapCheckAppDAO(custID_IN, staD_LD, endD_LD, staT_LT, endT_LT)) {
                    errorMessageBuilder.append("Customer cannot have appointments that overlap. Please select a different time to schedule.");
                    invalidInput = true;
                }
            }
        }
        if (invalidInput) {
            displayErrorACC(errorMessageBuilder.toString());
        }
        return !invalidInput;
    }

    /**
     * Saves the new appointment to the database with user initiated inputs.
     */
    private void saveCreatedAppointmentACC() {
        AppointmentDAO newAppointmentDAO = createAppointmentDAOACC();
        newAppointmentDAO.createAppointmentAppDAO(custID_IN, userID_IN, contID_IN, titl_ST, desc_ST, loca_ST, type_ST,
                staDT_LDT, endDT_LDT);
    }

    /**
     * Helper method that displays confirmation dialogues for methods in the AppointmentCreateController class.
     * Currently used by only one method, so it does not need to display customized strings.
     *
     * @return True if the user confirms that they want to cancel appointment creation, otherwise false
     */
    private boolean displayConfirmationACC() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Cancel New Appointment?");
        alert.setContentText("Changes have not been saved. Do you wish to cancel and return to Appointment Main?");
        Optional<ButtonType> result = alert.showAndWait();
        return result.isPresent() && result.get() == ButtonType.OK;
    }

    /**
     * This method cancels the creation of a new appointment. With the aid of a helper method, a confirmation dialogue
     * informs the user that all unsaved changes will be lost. If the user confirms that they wish to cancel, the user
     * returns to the Appointment Main Screen.
     *
     * @param actionEvent ActionEvent triggered by the user pressing the "cancel" button.
     */
    public void onActionCancelCreatedAppointmentACC(ActionEvent actionEvent) {
        try {
            if (displayConfirmationACC()) {
                String fxmlFileName = "/jrl/qam2final/AppointmentMainFXML.fxml";
                URL fxmlURL = getClass().getResource(fxmlFileName);
                if (fxmlURL == null) {
                    throw new IOException("FXML file not found: " + fxmlFileName);
                }
                FXMLLoader loader = new FXMLLoader(fxmlURL);
                Parent root = loader.load();
                Stage stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
                stage.setScene(new Scene(root));
                JDBCHelper.endDBConnection();
                System.out.println("Loaded FXML: " + fxmlFileName);
            }
        } catch (Exception e) {
            displayErrorACC("Failed to load FXML");
        }
    }

    /**
     * This method is used to create new data access objects.
     *
     * @return New instance of AppointmentDAOIMPL.
     */
    private AppointmentDAO createAppointmentDAOACC() {
        return new AppointmentDAOIMPL();
    }

    /**
     * This method is used to create new data access objects.
     *
     * @return New instance of ContactDAOIMPL.
     */
    private ContactDAO createContactDAOACC() {
        return new ContactDAOIMPL();
    }

    /**
     * This method is used to create new data access objects.
     *
     * @return New instance of CustomerDAOIMPL.
     */
    private CustomerDAO createCustomerDAOACC() {
        return new CustomerDAOIMPL();
    }

    /**
     * This method is used to create new data access objects.
     *
     * @return New instance of UserDAOIMPL.
     */
    private UserDAO createUserDAOACC() {
        return new UserDAOIMPL();
    }

    /**
     * Initialize Combo Boxes.
     */
    private void initComboBoxesInitACC() {
        JDBCHelper.startDBConnection();
        ContactDAO newContactDAO = createContactDAOACC();
        CustomerDAO newCustomerDAO = createCustomerDAOACC();
        UserDAO newUserDAO = createUserDAOACC();
        newAppointmentCustomerComboBox.setItems(newCustomerDAO.readCustomerAllCusDAO());
        newAppointmentCustomerComboBox.getSelectionModel().select(0);
        newAppointmentUserComboBox.setItems(newUserDAO.readUserAllUserDAO());
        newAppointmentUserComboBox.getSelectionModel().select(0);
        newAppointmentContactComboBox.setItems(newContactDAO.readContactAllConDAO());
        newAppointmentContactComboBox.getSelectionModel().select(0);
    }

    /**
     * Initialize Combo Boxes.
     */
    private void initTimeComboBoxesACC(ZoneId systemTimeZone, ZoneId businessTimeZone, LocalTime businessOpen, int businessHours) {
        newAppointmentStartComboBox.setItems(TimeConversionHelper.listBusinessHours(systemTimeZone, businessTimeZone, businessOpen, businessHours));
        newAppointmentStartComboBox.getSelectionModel().select(0);
        appointmentNewStartDatePicker.setValue(LocalDate.now());
        newAppointmentEndComboBox.setItems(TimeConversionHelper.listBusinessHours(systemTimeZone, businessTimeZone, LocalTime.of(9, 0), businessHours));
        newAppointmentEndComboBox.getSelectionModel().select(0);
        appointmentNewEndDatePicker.setValue(LocalDate.now());
    }

    /**
     * Initialization method for the controller. Business hours are set. Combo boxes and date pickers are populated.
     * Text fields are populated with default values to hasten the requirement testing process.
     *
     * @param url            URL
     * @param resourceBundle Resource Bundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            ZoneId systemTimeZone = ZoneId.systemDefault();
            ZoneId businessTimeZone = ZoneId.of("America/New_York");
            LocalTime businessOpen = LocalTime.of(8, 0);
            int businessHours = 13;
            initComboBoxesInitACC();
            initTimeComboBoxesACC(systemTimeZone, businessTimeZone, businessOpen, businessHours);
        } catch (Exception e) {
            displayErrorACC("Initialization Failed");
        }
    }
}