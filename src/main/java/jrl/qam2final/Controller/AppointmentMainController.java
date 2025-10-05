package jrl.qam2final.Controller;

import jrl.qam2final.DAO.AppointmentDAO;
import jrl.qam2final.DAO.AppointmentDAOIMPL;
import jrl.qam2final.Helper.JDBCHelper;
import jrl.qam2final.Helper.LogInTimeCheckHelper;
import java.io.IOException;
import javafx.scene.Parent;
import javafx.scene.Scene;
import java.time.ZoneId;
import java.util.Optional;
import jrl.qam2final.Helper.TranslationManagerHelper;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.FXML;
import javafx.collections.ObservableList;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.Alert;
import java.net.URL;
import java.time.LocalDate;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import jrl.qam2final.Model.AppointmentModel;
import java.util.ResourceBundle;
import java.util.function.Function;

/**
 * Controller class for the Appointment Main Screen. I paid close attention to setting up all FXML elements with clear
 * IDs and annotations. This will make it easier to establish translation keys for use with the TranslationManagerHelper
 * class in the future and achieve full multi-language support for all FXML elements in the GUI. Though this proactive
 * approach will help a great deal towards full multi-language support, translating dynamic values from the database
 * (appointment types, for example) will require more time and effort.
 *
 * @author Jeffrey Robert Lynch
 */
public class AppointmentMainController implements Initializable {

    @FXML
    public RadioButton appointmentWeekRadioButton;
    @FXML
    public RadioButton appointmentMonthRadioButton;
    @FXML
    public RadioButton appointmentDefaultRadioButton;
    @FXML
    public Button onActionCreateAMCToACCButton;
    @FXML
    public Button onActionUpdateAMCToAUCButton;
    @FXML
    public Button onActionAppointmentDeleteButton;
    @FXML
    public TableView<AppointmentModel> appointmentMainTableView;
    @FXML
    public TableColumn<AppointmentModel, String> appointmentMainIdColumn;
    @FXML
    public TableColumn<AppointmentModel, String>  appointmentMainTitleColumn;
    @FXML
    public TableColumn<AppointmentModel, String> appointmentMainDescriptionColumn;
    @FXML
    public TableColumn<AppointmentModel, String> appointmentMainLocationColumn;
    @FXML
    public TableColumn<AppointmentModel, String> appointmentMainTypeColumn;
    @FXML
    public TableColumn<AppointmentModel, String> appointmentMainStartDateColumn;
    @FXML
    public TableColumn<AppointmentModel, String> appointmentMainEndDateColumn;
    @FXML
    public TableColumn<AppointmentModel, String> appointmentMainStartTimeColumn;
    @FXML
    public TableColumn<AppointmentModel, String> appointmentMainEndTimeColumn;
    @FXML
    public TableColumn<AppointmentModel, String> appointmentMainCustomerIdColumn;
    @FXML
    public TableColumn<AppointmentModel, String> appointmentMainUserIdColumn;
    @FXML
    public TableColumn<AppointmentModel, String> appointmentMainContactColumn;
    @FXML
    public Label appointmentMainTitleLabel;
    @FXML
    public Button onActionAMCToCMCButton;
    @FXML
    public Button onActionAMCToRMCButton;
    @FXML
    public Button onActionExitButton;
    @FXML
    public ToggleGroup appointmentRadioToggleGroup;

    /**
     * LAMBDA EXPRESSION # 2
     * Method to delete selected appointment when the "Delete Appointment" button is pushed. An error dialogue is
     * displayed if the user has not selected an appointment. When the button is pressed with an appointment selected,
     * the user is prompted, via a confirmation dialog, to confirm deletion. If the user confirms deletion, the
     * selected appointment is removed from the database and the table updates to reflect this change.
     * LAMBDA EXPRESSION # 2: The lambda expression for the delete method defines the behavior depending on whether
     * selectedAppointment is null. ifPresentOrElse takes two arguments, a consumer if an appointment is selected
     * or a runnable if null. If null, an error message is displayed. If not null, deletion occurs. This lambda is
     * beneficial because it is concise and easy to read. The logic is clear whether the value is present or null. This
     * is a good approach for this method, especially if a developer wants to include further functionality later. For
     * example, future functionality might require multiple appointments to be deleted at once. In this case, of course,
     * present or null would no longer work for three or more behaviors, so additional changes to the logic would be
     * required.
     *
     * @param actionEvent Action Event triggered by the delete button.
     */
    public void onActionDeleteAppointmentAMC(ActionEvent actionEvent) {
        Optional<AppointmentModel> selectedAppointment = Optional.ofNullable(appointmentMainTableView.getSelectionModel().getSelectedItem());
        selectedAppointment.ifPresentOrElse(
                appointment -> {
                    int appointmentId = appointment.getAppID_IN();
                    String type = appointment.getType_ST();
                    try {
                        JDBCHelper.startDBConnection();
                        AppointmentDAO newAppointmentDAO = createAppointmentDAOAMC();

                        if (displayConfirmationAMC("Delete Appointment", "This appointment will be deleted forever. Do you wish to delete the appointment?")) {
                            if (newAppointmentDAO.deleteAppointmentAppDAO(appointmentId, type)) {
                                appointmentMainTableView.setItems(newAppointmentDAO.readAppointmentAllAppDAO());
                            } else {
                                displayErrorAMC("Failed to delete appointment.");
                            }
                        }
                    } catch (Exception e) {
                        displayErrorAMC("Failed to delete appointment.");
                    } finally {
                        JDBCHelper.endDBConnection();
                    }
                },
                () -> displayErrorAMC("No appointment selected. Appointments can only be deleted when selected.")
        );
    }

    /**
     * LAMBDA EXPRESSION # 3
     * Displays appointments by week when the radio button is selected using a lambda expression. It calls the
     * handleRadioAppointmentChange method and passes the lambda expression, specifying the method reference for
     * returning appointments scheduled in the upcoming week.
     * LAMBDA EXPRESSION # 3: I chose to use a lambda expression for this method, and onActionRadioMonthAMC, because it
     * allows me to retrieve appointments dynamically based on month or week using the same logic. Encapsulating the
     * logic makes it easy to add new radio buttons in the future since I can reuse my code. Additionally, the lambda
     * expressions are concise and easy to understand within the context of their use.
     *
     * @param actionEvent Action Event triggered by user selecting "Appointment by Week" radio button.
     */
    @FXML
    public void onActionRadioWeekAMC(ActionEvent actionEvent) {
        handleRadioAppointmentChange(appointmentDao -> appointmentDao.radioAppointmentsWeekAppDAO(LocalDate.from(LogInTimeCheckHelper.readUserTimeLITCH())));
    }

    /**
     * LAMBDA EXPRESSION # 3.1
     * Displays appointments by month when the radio button is selected using a lambda expression. It calls the
     * handleRadioAppointmentChange method and passes the lambda expression, specifying the method reference for
     * returning appointments scheduled in the current month.
     * LAMBDA EXPRESSION # 3.1: I chose to use a lambda expression for this method, and onActionRadioWeekAMC, because it
     * allows me to retrieve appointments dynamically based on week or month using the same logic. Encapsulating the
     * logic makes it easy to add new radio buttons in the future since I can reuse my code. Additionally, the lambda
     * expressions are concise and easy to understand within the context of their use.
     *
     * @param actionEvent Action Event triggered by user selecting "Appointment by Month" radio button.
     */
    @FXML
    public void onActionRadioMonthAMC(ActionEvent actionEvent) {
        handleRadioAppointmentChange(appointmentDao -> appointmentDao.radioAppointmentsMonthAppDAO(LocalDate.from(LogInTimeCheckHelper.readUserTimeLITCH())));
    }

    /**
     * Displays all appointments by default when the radio button is selected. Like the other two radio button methods,
     * this method calls handleRadioAppointmentChange. This method does not use a lambda expression, however, because
     * it references a method for returning all appointments in the AppointmentDAO class. For this reason it does not
     * require any additional logic like the week and month radio button methods.
     *
     * @param actionEvent Action Event triggered by user selecting "All Appointments" radio button.
     */
    @FXML
    public void onActionRadioDefaultAMC(ActionEvent actionEvent) {
        handleRadioAppointmentChange(AppointmentDAO::readAppointmentAllAppDAO);
    }

    /**
     * Method to handle navigation to the Update Appointment Screen. If an appointment has not been selected for update,
     * an error is displayed to the user and navigation does not occur. An error is displayed if the FXML fails to load.
     * The values of the selected appointment are saved for modification on the Update Appointment Screen.
     *
     * @param actionEvent Action Event triggered by the user pressing the "Update Appointment" button.
     */
    @FXML
    public void onActionNavigateAppointmentUpdateAMC(ActionEvent actionEvent) {
        try {
            String fxmlFileName = "/jrl/qam2final/AppointmentUpdateFXML.fxml";
            URL fxmlURL = getClass().getResource(fxmlFileName);
            if (fxmlURL == null) {
                throw new IOException("FXML file not found: " + fxmlFileName);
            }
            FXMLLoader loader = new FXMLLoader(fxmlURL);
            Parent scene = loader.load();
            AppointmentUpdateController AUC = loader.getController();
            AppointmentModel selectedAppointment = appointmentMainTableView.getSelectionModel().getSelectedItem();
            if (selectedAppointment == null) {
                displayErrorAMC("No appointment selected. Appointments can only be updated when selected.");
                return;
            }
            AUC.updateAppointmentPopulateAUC(selectedAppointment);
            Stage stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
            stage.setScene(new Scene(scene));
            stage.show();
            System.out.println("Loaded FXML: " + fxmlFileName);
        } catch (Exception e) {
            displayErrorAMC("Failed to load FXML");
        }
    }

    /**
     * Method for retrieving appointments based on specific radio button selection. The appointmentProvider parameter
     * handles lambda expressions passed from the methods onActionRadioMonthAMC and onActionRadioWeekAMC. Because this
     * method effectively handles lambda expressions it can easily be reused if more radio buttons are added in the
     * future.
     *
     * @param appointmentProvider Allows dynamic appointment retrieval based on provider.
     */
    private void handleRadioAppointmentChange(Function<AppointmentDAO, ObservableList<AppointmentModel>> appointmentProvider) {
        JDBCHelper.startDBConnection();
        AppointmentDAO newAppointmentDAO = createAppointmentDAOAMC();
        appointmentMainTableView.setItems(appointmentProvider.apply(newAppointmentDAO));
    }

    /**
     * Helper method that displays error messages for methods in the AppointmentMainController class. It takes
     * customMessage strings as a parameter and displays the specifics of the error to the user.
     *
     * @param customMessage Custom error message displayed to user
     */
    private void displayErrorAMC(String customMessage) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error Alert");
        alert.setHeaderText("Error Encountered");
        alert.setContentText(customMessage);
        alert.showAndWait();
    }

    /**
     * Method to handle navigation to the New Appointment Screen. An error is displayed if the FXML fails to load.
     *
     * @param actionEvent Action Event triggered by the user pressing the "New Appointment" button.
     */
    @FXML
    public void onActionNavigateAppointmentCreateAMC(ActionEvent actionEvent) {
        try {
            String fxmlFileName = "/jrl/qam2final/AppointmentCreateFXML.fxml";
            URL fxmlURL = getClass().getResource(fxmlFileName);
            if (fxmlURL == null) {
                throw new IOException("FXML file not found: " + fxmlFileName);
            }
            Stage stage = (Stage)((Button)actionEvent.getSource()).getScene().getWindow();
            Parent scene = FXMLLoader.load(fxmlURL);
            stage.setScene(new Scene(scene));
            stage.show();
            System.out.println("Loaded FXML: " + fxmlFileName);
        } catch (Exception e) {
            displayErrorAMC("Failed to load FXML");
        }
    }

    /**
     * Method to handle navigation to the Customer Main Screen. An error is displayed if the FXML fails to load.
     *
     * @param actionEvent Action Event triggered by the user pressing the "Customer Main" button.
     */
    @FXML
    public void onActionNavigateCustomerMainAMC(ActionEvent actionEvent) {
        try {
            String fxmlFileName = "/jrl/qam2final/CustomerMainFXML.fxml";
            URL fxmlURL = getClass().getResource(fxmlFileName);
            if (fxmlURL == null) {
                throw new IOException("FXML file not found: " + fxmlFileName);
            }
            Stage stage = (Stage)((Button)actionEvent.getSource()).getScene().getWindow();
            Parent scene = FXMLLoader.load(fxmlURL);
            stage.setScene(new Scene(scene));
            stage.show();
            System.out.println("Loaded FXML: " + fxmlFileName);
        } catch (Exception e) {
            displayErrorAMC("Failed to load FXML");
        }
    }

    /**
     * Method to handle navigation to the Reports Main Screen. An error is displayed if the FXML fails to load.
     *
     * @param actionEvent Action Event triggered by the user pressing the "Reports Main" button.
     */
    @FXML
    public void onActionNavigateReportMainAMC(ActionEvent actionEvent) {
        try {
            String fxmlFileName = "/jrl/qam2final/ReportMainFXML.fxml";
            URL fxmlURL = getClass().getResource(fxmlFileName);
            if (fxmlURL == null) {
                throw new IOException("FXML file not found: " + fxmlFileName);
            }
            Stage stage = (Stage)((Button)actionEvent.getSource()).getScene().getWindow();
            Parent scene = FXMLLoader.load(fxmlURL);
            stage.setScene(new Scene(scene));
            stage.show();
            System.out.println("Loaded FXML: " + fxmlFileName);
        } catch (Exception e) {
            displayErrorAMC("Failed to load FXML");
        }
    }

    /**
     * Method for exiting the application via a button press. With the aid of a helper method, a confirmation dialog to
     * is displayed to the user to confirm exit.
     *
     * @param actionEvent ActionEvent trigger is the user pressing the "exit" button.
     */
    public void onActionExitAMC(ActionEvent actionEvent) {
        if (displayConfirmationAMC("Exit Application", "Do you wish to exit the Customer Appointment Management System?")) {
            System.exit(0);
        }
    }

    /**
     * Helper method that displays confirmation dialogues for methods in the AppointmentMainController class. It takes
     * title strings and customMessage strings as parameters.
     *
     * @param title       Title for confirmation dialog
     * @param contentText Content for confirmation dialog.
     * @return True if user confirms by pressing "ok", otherwise false.
     */
    private boolean displayConfirmationAMC(String title, String contentText) {
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
    private AppointmentDAO createAppointmentDAOAMC() {
        return new AppointmentDAOIMPL();
    }

    /**
     * Initialization for the AppointmentMainController. Populates the table with appointments from the database, so
     * they can be viewed, searched, filtered, updated, or deleted. System time zone label is set for display.
     *
     * @param url            URL
     * @param resourceBundle Resource Bundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        JDBCHelper.startDBConnection();
        AppointmentDAO newAppointmentDAO = createAppointmentDAOAMC();
        appointmentMainTableView.setItems(newAppointmentDAO.readAppointmentAllAppDAO());
        appointmentMainIdColumn.setCellValueFactory(new PropertyValueFactory<>("appID_IN"));
        appointmentMainTitleColumn.setCellValueFactory(new PropertyValueFactory<>("titl_ST"));
        appointmentMainDescriptionColumn.setCellValueFactory(new PropertyValueFactory<>("desc_ST"));
        appointmentMainLocationColumn.setCellValueFactory(new PropertyValueFactory<>("loca_ST"));
        appointmentMainTypeColumn.setCellValueFactory(new PropertyValueFactory<>("type_ST"));
        appointmentMainStartDateColumn.setCellValueFactory(new PropertyValueFactory<>("staD_LD"));
        appointmentMainEndDateColumn.setCellValueFactory(new PropertyValueFactory<>("endD_LD"));
        appointmentMainStartTimeColumn.setCellValueFactory(new PropertyValueFactory<>("staT_LT"));
        appointmentMainEndTimeColumn.setCellValueFactory(new PropertyValueFactory<>("endT_LT"));
        appointmentMainCustomerIdColumn.setCellValueFactory(new PropertyValueFactory<>("custID_IN"));
        appointmentMainUserIdColumn.setCellValueFactory(new PropertyValueFactory<>("userID_IN"));
        appointmentMainContactColumn.setCellValueFactory(new PropertyValueFactory<>("contID_IN"));
    }
}