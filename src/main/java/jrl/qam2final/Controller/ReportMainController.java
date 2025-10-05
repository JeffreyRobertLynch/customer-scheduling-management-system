package jrl.qam2final.Controller;

import javafx.fxml.FXMLLoader;
import java.util.Optional;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import jrl.qam2final.Helper.TranslationManagerHelper;
import java.util.ResourceBundle;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.Scene;
import javafx.fxml.FXML;
import javafx.stage.Stage;
import java.io.IOException;
import java.net.URL;

/**
 * Controller class for the Report Main Screen. I paid close attention to setting up all FXML elements with clear IDs
 * and annotations. This will make it easier to establish translation keys for use with the TranslationManagerHelper
 * class in the future and achieve full multi-language support for all FXML elements in the GUI.
 *
 * @author Jeffrey Robert Lynch
 */
public class ReportMainController implements Initializable {

    @FXML
    public Label reportMainTitleLabel;
    @FXML
    public Button onActionRMCToContactScheduleButton;
    @FXML
    public Button onActionRMCToContactMonthButton;
    @FXML
    public Button onActionRMCToTypeMonthButton;
    @FXML
    public Button onActionRMCToCustomerMonthButton;
    @FXML
    public Button onActionRMCToAppointmentMainButton;
    @FXML
    public Button onActionExitRMCButton;
    @FXML
    public Button onActionRMCToCustomerMainButton;
    @FXML
    public Label comingSoonScrollPaneLabel;
    @FXML
    public ScrollPane comingSoonScrollPane;
    @FXML
    public Label customerMonthScrollPaneLabel;
    @FXML
    public ScrollPane customerMonthScrollPane;
    @FXML
    public Label typeMonthScrollPaneLabel;
    @FXML
    public ScrollPane typeMonthScrollPane;
    @FXML
    public Label contactMonthScrollPaneLabel;
    @FXML
    public ScrollPane contactMonthScrollPane;
    @FXML
    public Label contactScheduleScrollPaneLabel;
    @FXML
    public ScrollPane contactScheduleScrollPane;

    /**
     * Helper method that displays error messages for methods in the ReportMainController class. It takes
     * customMessage strings as a parameter and displays the specifics of the error to the user.
     *
     * @param customMessage Custom error message displayed to user
     */
    public void displayErrorRMC(String customMessage) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error Alert");
        alert.setHeaderText("Error Encountered");
        alert.setContentText(customMessage);
        alert.showAndWait();
    }

    /**
     * Method to handle navigation to the Contact Schedule Screen. An error is displayed if the FXML fails to load.
     *
     * @param actionEvent Action Event triggered by the user pressing the "Contact Schedule" button.
     */
    @FXML
    private void onActionNavigateReportContactScheduleRMC(ActionEvent actionEvent) {
        try {
            String fxmlFileName = "/jrl/qam2final/ContactScheduleFXML.fxml";
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
            displayErrorRMC("Failed to load FXML");
        }
    }

    /**
     * Method to handle navigation to the Contact and Month Screen. An error is displayed if the FXML fails to load.
     *
     * @param actionEvent Action Event triggered by the user pressing the "Contact and Month" button.
     */
    @FXML
    private void onActionNavigateReportContactMonthlyRMC(ActionEvent actionEvent) {
        try {
            String fxmlFileName = "/jrl/qam2final/ReportContactMonthlyFXML.fxml";
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
            displayErrorRMC("Failed to load FXML");
        }
    }

    /**
     * Method to handle navigation to the Customer and Month Screen. An error is displayed if the FXML fails to load.
     *
     * @param actionEvent Action Event triggered by the user pressing the "Customer and Month" button.
     */
    @FXML
    private void onActionNavigateReportCustomerMonthlyRMC(ActionEvent actionEvent) {
        try {
            String fxmlFileName = "/jrl/qam2final/ReportCustomerMonthlyFXML.fxml";
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
            displayErrorRMC("Failed to load FXML");
        }
    }

    /**
     * Method to handle navigation to the Type and Month Screen. An error is displayed if the FXML fails to load.
     *
     * @param actionEvent Action Event triggered by the user pressing the "Type and Month" button.
     */
    @FXML
    private void onActionNavigateReportTypeMonthlyRMC(ActionEvent actionEvent) {
        try {
            String fxmlFileName = "/jrl/qam2final/ReportTypeMonthlyFXML.fxml";
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
            displayErrorRMC("Failed to load FXML");
        }
    }

    /**
     * Method to handle navigation to the Appointment Main Screen. An error is displayed if the FXML fails to load.
     *
     * @param actionEvent Action Event triggered by the user pressing the "Appointment Main" button.
     */
    @FXML
    private void onActionNavigateAppointmentMainRMC(ActionEvent actionEvent) {
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
            displayErrorRMC("Failed to load FXML");
        }
    }

    /**
     * Method to handle navigation to the Customer Main Screen. An error is displayed if the FXML fails to load.
     *
     * @param actionEvent Action Event triggered by the user pressing the "Customer Main" button.
     */
    @FXML
    private void onActionNavigateCustomerMainRMC(ActionEvent actionEvent) {
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
            displayErrorRMC("Failed to load FXML");
        }
    }

    /**
     * Method for exiting the application via a button press. With the aid of a helper method, a confirmation dialog to
     * is displayed to the user to confirm exit.
     *
     * @param actionEvent ActionEvent trigger is the user pressing the "exit" button.
     */
    @FXML
    public void onActionExitRMC(ActionEvent actionEvent) {
        if (displayConfirmationRMC("Exit Application", "Do you wish to exit the Customer Appointment Management System?")) {
            System.exit(0);
        }
    }

    /**
     * Helper method that displays confirmation dialogues for methods in the ReportMainController class.
     *
     * @param title       Title for confirmation dialog
     * @param contentText Content for confirmation dialog.
     * @return True if user confirms by pressing "ok", otherwise false.
     */
    private boolean displayConfirmationRMC(String title, String contentText) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle(title);
        alert.setContentText(contentText);
        Optional<ButtonType> result = alert.showAndWait();
        return result.isPresent() && result.get() == ButtonType.OK;
    }

    /**
     * Initialization for the ReportMainController class.
     *
     * @param url            URL
     * @param resourceBundle Resource Bundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
    }
}