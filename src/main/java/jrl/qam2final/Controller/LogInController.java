package jrl.qam2final.Controller;

import javafx.scene.text.Text;
import jrl.qam2final.Helper.JDBCHelper;
import jrl.qam2final.Helper.LogInTimeCheckHelper;
import jrl.qam2final.Helper.TranslationManagerHelper;
import javafx.event.ActionEvent;
import java.time.ZoneId;
import java.util.Locale;
import javafx.fxml.FXMLLoader;
import java.io.FileWriter;
import javafx.scene.Parent;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.stage.Stage;
import jrl.qam2final.Model.UserModel;
import javafx.scene.control.Alert;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import java.io.IOException;
import javafx.scene.control.*;
import java.io.PrintWriter;
import java.net.URL;
import java.util.function.BiConsumer;
import java.time.LocalDateTime;

import static jrl.qam2final.Helper.LogInTimeCheckHelper.queryUserNamePasswordLITCH;

/**
 * Controller class for the Log-In Screen. I paid close attention to setting up all FXML elements with clear IDs and
 * annotations. This made it easy to establish translation keys for use with the TranslationManagerHelper class and
 * achieve full multi-language support for all FXML elements for this GUI screen.
 *
 * @author Jeffrey Robert Lynch
 */
public class LogInController implements Initializable {

    @FXML
    public ScrollPane welcomeMessageScrollPane;
    @FXML
    public Text welcomeMessageText;
    @FXML
    public Label logInTitleLabel;
    @FXML
    public Label userNameLabel;
    @FXML
    public Label passwordLabel;
    @FXML
    public Label timeZoneLabel;
    @FXML
    public TextField userNameTextField;
    @FXML
    public PasswordField passwordPasswordField;
    @FXML
    public Label systemTimeZoneLabel;
    @FXML
    public Button onActionLogInButton;
    @FXML
    public Button onActionExitButton;

    /**
     * LAMBDA EXPRESSION #1
     * Method for handling the result of a user log-in attempt and logging the details of the attempt in a text file
     * named "login_activity.txt". If the attempt is successful, the user navigates to the Appointment Main Screen. If
     * the attempt is unsuccessful, an error message is displayed to the user. The method uses a lambda expression to
     * dynamically record log-in activity to "login_activity.txt" based on the result.
     * LAMBDA EXPRESSION #1: The lambda expressions uses two parameters, 'info' and 'status'. It constructs and writes a
     * log-in attempt record containing the username, user language system setting, log-in attempt status, timestamp,
     * and time zone. The lambda expression in this method encapsulates the file writing logic into a single expression.
     * This approach is concise, easy to understand, and improves readability for an integral part of the application,
     * recording log-in attempts. It is also flexible if a developer later wants to record different types of
     * information depending on the specific outcome of the log-in attempt.
     *
     * @param successFailResult   Result of the log-in attempt, success or failure.
     * @param inputUserN     Username provided as user input for the login attempt.
     * @param time         Timestamp when user attempted log-in.
     * @param actionEvent  Action Event triggered by user log-in attempt
     */
    @FXML
    private void logInSuccessFailLogicLIC(UserModel successFailResult, String inputUserN , LocalDateTime time, ActionEvent actionEvent) {
        String fileName = "login_activity.txt";
        try {
            FileWriter writeRecordLog = new FileWriter(fileName, true);
            PrintWriter printRecordLog = new PrintWriter(writeRecordLog);
            BiConsumer<String, String> writeToFile = (info, status) -> {
                printRecordLog.println("User: " + inputUserN  + " | " + info + " | " + TranslationManagerHelper.getString("languageSettingKey") + " | " + "Timestamp: " + time + " | " + "Time Zone: " + ZoneId.systemDefault());
            };
            if (successFailResult != null) {
                successLogInNavigateAppointmentMainLIC(actionEvent);
                writeToFile.accept("Log-in Successful", "Success");
            } else {
                failLogInErrorLIC();
                writeToFile.accept("Log-in Failed", "Failure");
            }
            printRecordLog.close();
        } catch (IOException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("errorTitleKey");
            alert.setHeaderText("errorHeaderKey");
            alert.setContentText("fileWriteErrorKey");
            alert.showAndWait();
        }
    }

    /**
     * Method for handling log-in attempts from users. The username and password provided by the user are used to query
     * the database and find a match using a helper class method. The result of the query, the username input, and the
     * time of the log-in attempt are passed to another method, logInSuccessFailLogicLIC.
     *
     * @param actionEvent Action Event triggered by user clicking the log-in button.
     */
    @FXML
    public void onActionLogInLIC(ActionEvent actionEvent) {
        try {
            String inputUserN = userNameTextField.getText();
            String inputPass = String.valueOf(passwordPasswordField.getText());
            UserModel successFailResult = queryUserNamePasswordLITCH(inputUserN, inputPass);
            LocalDateTime time = LocalDateTime.now();
            logInSuccessFailLogicLIC(successFailResult, inputUserN, time, actionEvent);
        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("errorTitleKey");
            alert.setHeaderText("errorHeaderKey");
            alert.setContentText("logInErrorKey");
            alert.showAndWait();
        }
    }

    /**
     * Method for exiting the application via a button press. With the aid of a helper method, a confirmation dialog to
     * is displayed to the user to confirm exit.
     *
     * @param actionEvent ActionEvent trigger is the user pressing the "exit" button.
     */
    @FXML
    public void onActionExitLIC(ActionEvent actionEvent) {
        if (displayConfirmationLIC(TranslationManagerHelper.getString("exitApplicationKey"), TranslationManagerHelper.getString("exitConfirmationKey"))) {
            System.exit(0);
        }
    }

    /**
     * Helper method that displays confirmation dialogues for methods in the LogInController class.
     *
     * @param title       Title for confirmation dialog
     * @param contentText Content for confirmation dialog.
     * @return True if user confirms by pressing "ok", otherwise false.
     */
    private boolean displayConfirmationLIC(String title, String contentText) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle(title);
        alert.setContentText(contentText);
        Optional<ButtonType> result = alert.showAndWait();
        return result.isPresent() && result.get() == ButtonType.OK;
    }

    /**
     * Method for navigating to the Appointment Main Screen after a successful log-in attempt. Opens a connection to the
     * database and uses a method in the AppointmentDAO to check whether there are any appointments within 15 minutes of
     * log on. Displays an error message to the user if the FXML file fails to load.
     *
     * @param actionEvent Action Event triggered by a successful user log-in.
     */
    @FXML
    private void successLogInNavigateAppointmentMainLIC(ActionEvent actionEvent) throws IOException {
        try {
            String fxmlFileName = "/jrl/qam2final/AppointmentMainFXML.fxml";
            URL fxmlURL = getClass().getResource(fxmlFileName);
            FXMLLoader loader = new FXMLLoader(fxmlURL);
            Parent root = loader.load();
            Stage stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
            System.out.println("Loaded FXML: " + fxmlFileName);
            JDBCHelper.startDBConnection();
            LocalDateTime logInTS = LogInTimeCheckHelper.readUserTimeLITCH();
            LogInTimeCheckHelper.checkImminentAppointmentLITCH(logInTS);
        } finally {
            JDBCHelper.endDBConnection();
        }
    }

    /**
     * Helper method to display alert dialogs to the user from methods in the LogInController class. It takes
     * errorMessage strings as a parameter and displays the specifics of the error to the user.
     *
     * @param errorMessage The error message to be displayed.
     */
    private void displayErrorLIC(String errorMessage) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setContentText(errorMessage);
        alert.showAndWait();
    }

    /**
     * Handles the error message and displays an error alert for a failed log-in attempt.
     *
     */
    private void failLogInErrorLIC() {
        String errorMessage = TranslationManagerHelper.getString("invalidCredentialsKey");
        displayErrorLIC(errorMessage);
    }

    /**
     * Initializes log-in interface with localized labels, messages, and truncated README information. Sets system time
     * zone label based users system setting.
     *
     * @param url            URL
     * @param resourceBundle Resource Bundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        systemTimeZoneLabel.setText(String.valueOf(ZoneId.systemDefault()));
        try {
            logInTitleLabel.setText(TranslationManagerHelper.getString("logInTitleKey"));
            userNameLabel.setText(TranslationManagerHelper.getString("userNameKey"));
            passwordLabel.setText(TranslationManagerHelper.getString("passwordKey"));
            timeZoneLabel.setText(TranslationManagerHelper.getString("timeZoneKey"));
            welcomeMessageText.setText(TranslationManagerHelper.getString("welcomeMessageTextKey"));
            onActionExitButton.setText(TranslationManagerHelper.getString("exitKey"));
            onActionLogInButton.setText(TranslationManagerHelper.getString("logInKey"));
            String welcomeMessage = TranslationManagerHelper.getString("welcomeMessageTextKey");
            welcomeMessageText.setText(welcomeMessage);
            welcomeMessageText.setWrappingWidth(700);
        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle(TranslationManagerHelper.getString("errorTitleKey"));
            alert.setHeaderText(TranslationManagerHelper.getString("errorHeaderKey"));
            alert.setContentText(TranslationManagerHelper.getString("failedToInitializeKey"));
            alert.showAndWait();
        }
    }
}