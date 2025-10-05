package jrl.qam2final.Controller;

import javafx.collections.ObservableList;
import jrl.qam2final.DAO.*;
import jrl.qam2final.Helper.JDBCHelper;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import jrl.qam2final.Helper.TranslationManagerHelper;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import jrl.qam2final.Model.AppointmentModel;
import jrl.qam2final.Model.ContactModel;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.scene.Parent;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.fxml.FXML;

/**
 * Controller class for the Contact Schedule Screen. I paid close attention to setting up all FXML elements with clear
 * IDs and annotations. This will make it easier to establish translation keys for use with the TranslationManagerHelper
 * class in the future and achieve full multi-language support for all FXML elements in the GUI. Though this proactive
 * approach will help a great deal towards full multi-language support, translating dynamic values from the database
 * (appointment types, for example) will require more time and effort.
 *
 * @author Jeffrey Robert Lynch
 */
public class ContactScheduleController implements Initializable {

    @FXML
    public Label contactScheduleTitleLabel;
    @FXML
    public ComboBox<ContactModel> contactScheduleComboBox;
    @FXML
    public TableView<AppointmentModel> contactScheduleTableView;
    @FXML
    public TableColumn<AppointmentModel, String> scheduleAppointmentIdColumn;
    @FXML
    public TableColumn<AppointmentModel, String> scheduleTitleColumn;
    @FXML
    public TableColumn<AppointmentModel, String> scheduleDescriptionColumn;
    @FXML
    public TableColumn<AppointmentModel, String> scheduleTypeColumn;
    @FXML
    public TableColumn<AppointmentModel, String> scheduleLocationColumn;
    @FXML
    public TableColumn<AppointmentModel, String> scheduleStartDateColumn;
    @FXML
    public TableColumn<AppointmentModel, String> scheduleEndDateColumn;
    @FXML
    public TableColumn<AppointmentModel, String> scheduleStartTimeColumn;
    @FXML
    public TableColumn<AppointmentModel, String> scheduleCustomerColumn;
    @FXML
    public TableColumn<AppointmentModel, String> scheduleEndTimeColumn;
    @FXML
    public TableColumn<AppointmentModel, String> scheduleUserIdColumn;
    @FXML
    public TableColumn<AppointmentModel, String> scheduleContactColumn;
    @FXML
    public Button onActionCSCToRMC;

    /**
     * Method to populate the table view when a contact is selected. When a contact is selected, the table view is
     * populated with all appointments associated with the contact. This generates a schedule of appointments for a
     * contact. A count of appointments is displayed for the selected contact, along with their schedule.
     *
     * @param actionEvent Action Event triggered by user choosing a contact to generate a schedule for.
     */
    @FXML
    public void onActionPopulateScheduleTableCSC(ActionEvent actionEvent) {
        JDBCHelper.startDBConnection();
        AppointmentDAO newAppointmentDAO = createAppointmentDAOCSC();
        int ContID_IN = contactScheduleComboBox.getSelectionModel().getSelectedItem().getContID_IN();
        ObservableList<AppointmentModel> appointmentsC = newAppointmentDAO.readAppointmentByContactAppDAO(ContID_IN);
        int appointmentCount = appointmentsC.size();
        contactScheduleTableView.setItems(appointmentsC);
        displayInfoCSC("Contact Appointments", "Contact with ID: " + ContID_IN + " has " + appointmentCount + " appointments scheduled with customers.");
    }

    /**
     * Method to handle navigation to the Reports Main Screen. An error is displayed if the FXML fails to load.
     *
     * @param actionEvent Action Event triggered by the user pressing the "Reports Main" button.
     */
    @FXML
    private void onActionNavigateReportMainCSC(ActionEvent actionEvent) {
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
            displayErrorCSC("Failed to load FXML");
        }
    }

    /**
     * Helper method that displays information messages for methods in the ContactScheduleController class.
     *
     * @param title Title for the alert.
     * @param message Message shown to the user.
     */
    private void displayInfoCSC(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    /**
     * Helper method that displays error messages for methods in the ContactScheduleController class. It takes
     * customMessage strings as a parameter and displays the specifics of the error to the user.
     *
     * @param customMessage Custom error message displayed to user
     */
    private void displayErrorCSC(String customMessage) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error Alert");
        alert.setHeaderText("Error Encountered");
        alert.setContentText(customMessage);
        alert.showAndWait();
    }

    /**
     * This method is used to create new data access objects.
     *
     * @return New instance of AppointmentDAOIMPL.
     */
    private AppointmentDAO createAppointmentDAOCSC() {
        return new AppointmentDAOIMPL();
    }

    /**
     * This method is used to create new data access objects.
     *
     * @return New instance of ContactDAOIMPL.
     */
    private ContactDAO createContactDAOCSC() {
        return new ContactDAOIMPL();
    }

    /**
     * Initialization for the ContactScheduleController class.
     *
     * @param url            URL
     * @param resourceBundle Resource Bundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        JDBCHelper.startDBConnection();
        ContactDAO newContactDAO = createContactDAOCSC();
        contactScheduleComboBox.setItems(newContactDAO.readContactAllConDAO());
        scheduleAppointmentIdColumn.setCellValueFactory(new PropertyValueFactory<>("appID_IN"));
        scheduleTitleColumn.setCellValueFactory(new PropertyValueFactory<>("titl_ST"));
        scheduleDescriptionColumn.setCellValueFactory(new PropertyValueFactory<>("desc_ST"));
        scheduleLocationColumn.setCellValueFactory(new PropertyValueFactory<>("loca_ST"));
        scheduleTypeColumn.setCellValueFactory(new PropertyValueFactory<>("type_ST"));
        scheduleStartDateColumn.setCellValueFactory(new PropertyValueFactory<>("staD_LD"));
        scheduleEndDateColumn.setCellValueFactory(new PropertyValueFactory<>("endD_LD"));
        scheduleStartTimeColumn.setCellValueFactory(new PropertyValueFactory<>("staT_LT"));
        scheduleEndTimeColumn.setCellValueFactory(new PropertyValueFactory<>("endT_LT"));
        scheduleCustomerColumn.setCellValueFactory(new PropertyValueFactory<>("custID_IN"));
        scheduleUserIdColumn.setCellValueFactory(new PropertyValueFactory<>("userID_IN"));
        scheduleContactColumn.setCellValueFactory(new PropertyValueFactory<>("contID_IN"));
    }
}