package jrl.qam2final.Controller;

import javafx.fxml.FXML;
import jrl.qam2final.DAO.*;
import jrl.qam2final.Helper.JDBCHelper;
import javafx.scene.Parent;
import javafx.fxml.Initializable;
import java.time.LocalDate;
import jrl.qam2final.Helper.TranslationManagerHelper;
import java.time.LocalDateTime;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.*;
import java.util.Optional;
import javafx.scene.Scene;
import javafx.stage.Stage;
import jrl.qam2final.Model.*;
import jrl.qam2final.Helper.TimeConversionHelper;
import java.io.IOException;
import java.net.URL;
import javafx.scene.control.*;
import java.util.ResourceBundle;

/**
 * Controller class for Update Appointment Screen. I paid close attention to setting up all FXML elements with clear IDs
 * and annotations. This will make it easier to establish translation keys for use with the TranslationManagerHelper
 * class in the future and achieve full multi-language support for all FXML elements in the GUI. Though this proactive
 * approach will help a great deal towards full multi-language support, translating dynamic values from the database
 * (appointment types, for example) will require more time and effort.
 *
 * @author Jeffrey Robert Lynch
 */
public class AppointmentUpdateController implements Initializable {

    @FXML
    public Label appointmentUpdateIdLabel;
    @FXML
    public Label appointmentUpdateTitleLabel;
    public String titl_ST;
    @FXML
    public Label appointmentUpdateDescriptionLabel;
    public String desc_ST;
    @FXML
    public Label appointmentUpdateLocationLabel;
    public String loca_ST;
    @FXML
    public Label appointmentUpdateTypeLabel;
    public String type_ST;
    @FXML
    public Label appointmentUpdateStartDateLabel;
    public LocalDate staD_LD;
    public LocalDate endD_LD;
    @FXML
    public Label appointmentStartTimeLabelUpdate;
    public LocalTime staT_LT;
    public LocalTime endT_LT;
    @FXML
    public Label appointmentUpdateEndDateLabel;
    @FXML
    public Label appointmentUpdateEndTimeLabel;
    @FXML
    public Label appointmentUpdateCustomerLabel;
    @FXML
    public Label appointmentUpdateUserLabel;
    @FXML
    public Label appointmentUpdateContactLabel;
    @FXML
    public TextField appointmentUpdateIdTextField;
    @FXML
    public TextField appointmentUpdateTitleTextField;
    @FXML
    public TextField appointmentUpdateDescriptionTextField;
    @FXML
    public TextField appointmentUpdateLocationTextField;
    @FXML
    public TextField appointmentUpdateTypeTextField;
    @FXML
    public DatePicker appointmentUpdateStartDatePicker;
    public LocalDateTime staDT_LDT;
    public LocalDateTime endDT_LDT;
    @FXML
    public ComboBox<LocalTime>  appointmentUpdateStartComboBox;
    @FXML
    public DatePicker appointmentUpdateEndDatePicker;
    @FXML
    public ComboBox<LocalTime>  appointmentUpdateEndComboBox;
    @FXML
    public ComboBox<CustomerModel> appointmentUpdateCustomerComboBox;
    @FXML
    public ComboBox<UserModel> appointmentUpdateUserComboBox;
    @FXML
    public ComboBox<ContactModel> appointmentUpdateContactComboBox;
    public int custID_IN;
    public int userID_IN;
    public int contID_IN;
    @FXML
    public Button onActionSaveUpdateButton;
    @FXML
    public Button onActionCancelUpdateButton;
    @FXML
    public Label appointmentUpdateHeaderLabel;
    AppointmentModel selectedAppointment = null;

    /**
     * This method triggers when the "save" button is clicked. It validates the user's input by calling
     * validateAppointmentUpdateAUC to ensure the user's input is valid. If all validation checks are passed, it saves
     * the updated information to the database by calling saveAppointmentUpdateAUC. After the updates are saved, the
     * method redirects the user to Appointment Main.
     *
     * @param actionEvent Action Event triggered by the user clicking the "save" button.
     */
    public void onActionSaveUpdatedAppointmentAUC(ActionEvent actionEvent) {
        try {
            if (validateAppointmentUpdateAUC()) {
                saveAppointmentUpdateAUC();
                navigateAppointmentMainAUC(actionEvent);
            }
        } catch (Exception e) {
            displayErrorAUC("Failed to load FXML");
        }
    }

    /**
     * Method to handle navigation to the Appointment Main Screen after the save button is clicked. An error is
     * displayed if the FXML fails to load.
     *
     * @param actionEvent Action Event triggered by the user pressing the "Save" button.
     */
    private void navigateAppointmentMainAUC(ActionEvent actionEvent) {
        try {
            String fxmlFileName = "/jrl/qam2final/AppointmentMainFXML.fxml";
            URL fxmlURL = getClass().getResource(fxmlFileName);
            if (fxmlURL == null) {
                throw new IOException("FXML file not found: " + fxmlFileName);
            }
            Stage stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
            Parent root = FXMLLoader.load(fxmlURL);
            stage.setScene(new Scene(root));
            System.out.println("Loaded FXML: " + fxmlFileName);
            stage.show();
            JDBCHelper.endDBConnection();
        } catch (IOException e) {
            displayErrorAUC("Failed to load FXML");
        }
    }

    /**
     * This method performs validation for appointment update fields before they can be saved to the database. It builds
     * a string of error messages to display to the user. Validation checks to ensure appointment times are valid,
     * including appointment overlaps, are also performed. Each check, if failed, provides a specific message to the
     * user.
     *
     * @return true If the user has not left null fields or invalid times, otherwise false.
     */
    private boolean validateAppointmentUpdateAUC() {
        boolean invalidInput = false;
        StringBuilder errorMessageBuilder = new StringBuilder();
        int appointmentId = selectedAppointment.getAppID_IN();
        custID_IN = appointmentUpdateCustomerComboBox.getSelectionModel().getSelectedItem().getCustID_IN();
        userID_IN = appointmentUpdateUserComboBox.getSelectionModel().getSelectedItem().getUserID_IN();
        contID_IN = appointmentUpdateContactComboBox.getSelectionModel().getSelectedItem().getContID_IN();
        desc_ST = appointmentUpdateDescriptionTextField.getText();
        loca_ST = appointmentUpdateLocationTextField.getText();
        titl_ST = appointmentUpdateTitleTextField.getText();
        type_ST = appointmentUpdateTypeTextField.getText();
        staD_LD = appointmentUpdateStartDatePicker.getValue();
        staT_LT = appointmentUpdateStartComboBox.getSelectionModel().getSelectedItem();
        staDT_LDT = LocalDateTime.of(staD_LD, staT_LT);
        endD_LD = appointmentUpdateEndDatePicker.getValue();
        endT_LT = appointmentUpdateEndComboBox.getSelectionModel().getSelectedItem();
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
                AppointmentDAO newAppointmentDAO = createAppointmentDAOAUC();
                if (staDT_LDT.isAfter(endDT_LDT)) {
                    errorMessageBuilder.append("Appointment start must be before appointment end.");
                    invalidInput = true;
                } else if (!newAppointmentDAO.checkAppointmentStartAppDAO(staDT_LDT) || !newAppointmentDAO.checkAppointmentEndAppDAO(endDT_LDT)) {
                    errorMessageBuilder.append("Appointments cannot be scheduled outside of regular business hours.");
                    invalidInput = true;
                } else if (newAppointmentDAO.updatedAppointmentOverlapCheckAppDAO(custID_IN, staD_LD, endD_LD, staT_LT, endT_LT, appointmentId)) {
                    errorMessageBuilder.append("Customer cannot have appointments that overlap. Please select a different time to schedule.");
                    invalidInput = true;
                }
            }
        }
        if (invalidInput) {
            displayErrorAUC(errorMessageBuilder.toString());
        }
        return !invalidInput;
    }

    /**
     * Saves the updated appointment to the database with user initiated changes.
     */
    private void saveAppointmentUpdateAUC() {
        int appointmentId = selectedAppointment.getAppID_IN();
        AppointmentDAO newAppointmentDAO = createAppointmentDAOAUC();
        newAppointmentDAO.updateAppointmentAppDAO(appointmentId, custID_IN, userID_IN, contID_IN, titl_ST, desc_ST, loca_ST,
                type_ST, staDT_LDT, endDT_LDT);
    }

    /**
     * Helper method that displays error messages for methods in the AppointmentUpdateController class. It takes
     * customMessage strings as a parameter and displays the specifics of the error to the user.
     *
     * @param customMessage Custom error message displayed to user
     */
    private void displayErrorAUC(String customMessage) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error Alert");
        alert.setHeaderText("Error Encountered");
        alert.setContentText(customMessage);
        alert.showAndWait();
    }

    /**
     * This method cancels the updating of an appointment. With the aid of a helper method, a confirmation dialogue
     * informs the user that all unsaved changes will be lost. If the user confirms that they wish to cancel, the user
     * returns to the Appointment Main Screen.
     *
     * @param actionEvent ActionEvent triggered by the user pressing the "cancel" button.
     */
    public void onActionCancelUpdatedAppointmentAUC(ActionEvent actionEvent) {
        try {
            if (displayConfirmationAUC("Cancel Appointment Update", "Changes have not been saved. Do you wish to cancel and return to Appointment Main?")) {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/jrl/qam2final/AppointmentMainFXML.fxml"));
                Parent root = loader.load();
                Stage stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
                stage.setScene(new Scene(root));
                stage.show();
                System.out.println("Loaded FXML: AppointmentMainFXML.fxml");
            }
        } catch (Exception e) {
            displayErrorAUC("Failed to load FXML");
        }
    }

    /**
     * Helper method that displays confirmation dialogues for methods in the AppointmentUpdateController class. It takes
     * title strings and contentText strings as parameters.
     *
     * @param title       Title for confirmation dialog
     * @param contentText Content for confirmation dialog.
     * @return True if user confirms by pressing "ok", otherwise false.
     */
    private boolean displayConfirmationAUC(String title, String contentText) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle(title);
        alert.setContentText(contentText);
        Optional<ButtonType> result = alert.showAndWait();
        return result.isPresent() && result.get() == ButtonType.OK;
    }

    /**
     * This method is used to create new data access objects.
     *
     * @return New instance of AppointmentDAOIMPL.
     */
    private AppointmentDAO createAppointmentDAOAUC() {
        return new AppointmentDAOIMPL();
    }

    /**
     * This method is used to create new data access objects.
     *
     * @return New instance of ContactDAOIMPL.
     */
    private ContactDAO createContactDAOAUC() {
        return new ContactDAOIMPL();
    }

    /**
     * This method is used to create new data access objects.
     *
     * @return New instance of CustomerIMPL.
     */
    private CustomerDAO createCustomerDAOAUC() {
        return new CustomerDAOIMPL();
    }

    /**
     * This method is used to create new data access objects.
     *
     * @return New instance of UserDAOIMPL.
     */
    private UserDAO createUserDAOAUC() {
        return new UserDAOIMPL();
    }


    /**
     * A method that calls methods to populate fields, date pickers, and combo boxes with the values of the appointment
     * selected for update.
     *
     * @param selection AppointmentModel for the appointment selected for update.
     */
    public void updateAppointmentPopulateAUC(AppointmentModel selection) {
        JDBCHelper.startDBConnection();
        selectedAppointment = selection;
        populateDatePickersAUC();
        populateComboBoxesAUC();
        populateTextFieldsAUC();
    }

    /**
     * Populates Date Pickers.
     */
    private void populateDatePickersAUC() {
        appointmentUpdateStartDatePicker.setValue(selectedAppointment.getStaD_LD());
        appointmentUpdateStartComboBox.getSelectionModel().select(selectedAppointment.getStaT_LT());
        appointmentUpdateEndDatePicker.setValue(selectedAppointment.getEndD_LD());
        appointmentUpdateEndComboBox.getSelectionModel().select(selectedAppointment.getEndT_LT());
    }

    /**
     * Populates Combo Boxes.
     */
    private void populateComboBoxesAUC() {
        CustomerDAO newCustomerDAO = createCustomerDAOAUC();
        UserDAO newUserDAO = createUserDAOAUC();
        ContactDAO newContactDAO = createContactDAOAUC();
        appointmentUpdateCustomerComboBox.getSelectionModel().select(
                newCustomerDAO.readCustomerAllCusDAO()
                        .stream()
                        .filter(customer -> customer.getCustID_IN() == selectedAppointment.getCustID_IN())
                        .findFirst()
                        .orElse(null)
        );
        appointmentUpdateUserComboBox.getSelectionModel().select(
                newUserDAO.readUserAllUserDAO()
                        .stream()
                        .filter(user -> user.getUserID_IN() == selectedAppointment.getUserID_IN())
                        .findFirst()
                        .orElse(null)
        );
        appointmentUpdateContactComboBox.getSelectionModel().select(
                newContactDAO.readContactAllConDAO()
                        .stream()
                        .filter(contact -> contact.getContID_IN() == selectedAppointment.getContID_IN())
                        .findFirst()
                        .orElse(null)
        );
    }

    /**
     * Populates Text Fields.
     */
    private void populateTextFieldsAUC() {
        appointmentUpdateDescriptionTextField.setText(selectedAppointment.getDesc_ST());
        appointmentUpdateLocationTextField.setText(selectedAppointment.getLoca_ST());
        appointmentUpdateTitleTextField.setText(selectedAppointment.getTitl_ST());
        appointmentUpdateTypeTextField.setText(selectedAppointment.getType_ST());
    }

    /**
     * Initialize Combo Boxes.
     */
    private void initComboBoxesAUC(ZoneId systemTimeZone, ZoneId businessTimeZone, LocalTime businessOpen, int businessHours) {
        JDBCHelper.startDBConnection();
        ContactDAO newContactDAO = createContactDAOAUC();
        CustomerDAO newCustomerDAO = createCustomerDAOAUC();
        UserDAO newUserDAO = createUserDAOAUC();
        appointmentUpdateStartComboBox.setItems(TimeConversionHelper.listBusinessHours(systemTimeZone, businessTimeZone, businessOpen, businessHours));
        appointmentUpdateEndComboBox.setItems(TimeConversionHelper.listBusinessHours(systemTimeZone, businessTimeZone, LocalTime.of(9, 0), businessHours));
        appointmentUpdateCustomerComboBox.setItems(newCustomerDAO.readCustomerAllCusDAO());
        appointmentUpdateUserComboBox.setItems(newUserDAO.readUserAllUserDAO());
        appointmentUpdateContactComboBox.setItems(newContactDAO.readContactAllConDAO());
    }

    /**
     *  Initialization method for the controller. Business hours are set. The population of fields, date pickers, and
     *  combo boxes with the selected appointment's values takes place in the updateAppointmentPopulateAUC method.
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
            initComboBoxesAUC(systemTimeZone, businessTimeZone, businessOpen, businessHours);
        } catch (Exception e) {
            displayErrorAUC("Initialization Failed.");
        }
    }
}