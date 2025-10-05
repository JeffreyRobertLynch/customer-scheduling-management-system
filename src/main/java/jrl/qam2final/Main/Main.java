package jrl.qam2final.Main;

import jrl.qam2final.Helper.JDBCHelper;
import javafx.application.Application;
import javafx.scene.control.Alert;
import jrl.qam2final.Helper.TranslationManagerHelper;
import java.util.Locale;
import java.util.TimeZone;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;

/**
 * Project: C195 - QAM2
 * Name: Jeffrey Robert Lynch
 * Submission Date: 4/14/2024
 * Thank you for checking out my application! Requirements were tested in the VM on 4/12/24, prior to submission.
 * Everything checked out across different Time Zone and Language settings. The 3 LAMBDA EXPRESSIONS I commented are in
 * the following methods: "logInSuccessFailLogicLIC" in the LogInController, "onActionDeleteAppointmentAMC" in the
 * AppointmentMainController, and "onActionRadioWeekAMC" in the AppointmentMainController.
 * The Main class contains methods to load the log-In screen, set the title for the application GUI, and set the default
 * language using the TranslationManagerHelper class to enable translation.
 *
 * @author Jeffrey Robert Lynch
 */
public class Main extends Application {

    /**
     * This method starts the application by loading the log-In FXML file. It also sets the language for translation
     * keys using the TranslationManagerHelper class. A translated error message is displayed to the user if an
     * exception occurs loading the first screen.
     *
     * @param stage The first screen of the application.
     * @throws Exception If there is an error loading the first screen.
     */
    @Override
    public void start(Stage stage) throws Exception {
        try {
            TranslationManagerHelper.setLanguage(Locale.getDefault());
            Parent root = FXMLLoader.load(getClass().getResource("/jrl/qam2final/LogInFXML.fxml"));
            stage.setTitle(TranslationManagerHelper.getString("applicationTitleKey"));
            stage.setScene(new Scene(root, 1180, 440));
            stage.show();
        } catch (Exception e) {
            displayErrorMain("FailedToLoadFXMLKey");
        }
    }

    /**
     * The main method of the application. The database connection is initialized when launched and closed upon exiting
     * the application.
     *
     * @param args Launch
     */
    public static void main(String[] args) {
        // Set time zone for testing OS time zone recognition and appointment time conversion. Uncomment line to test.
        // Time Zone: Pacific/Pago_Pago (American Samoa)
        // TimeZone.setDefault(TimeZone.getTimeZone("Pacific/Pago_Pago"));
        // Time Zone: Pacific/Honolulu (Hawaii)
        // TimeZone.setDefault(TimeZone.getTimeZone("Pacific/Honolulu"));
        // Time Zone: Pacific/Marquesas (Marquesas Islands)
        // TimeZone.setDefault(TimeZone.getTimeZone("Pacific/Marquesas"));
        // Time Zone: Pacific/Gambier (Gambier Islands)
        // TimeZone.setDefault(TimeZone.getTimeZone("Pacific/Gambier"));
        // Time Zone: America/Anchorage (Alaska)
        // TimeZone.setDefault(TimeZone.getTimeZone("America/Anchorage"));
        // Time Zone: America/Adak (Hawaii-Aleutian Time - HAST)
        // TimeZone.setDefault(TimeZone.getTimeZone("America/Adak"));
        // Time Zone: America/Los_Angeles (Pacific Time - PT)
        // TimeZone.setDefault(TimeZone.getTimeZone("America/Los_Angeles"));
        // Time Zone: America/Denver (Mountain Time - MT)
        // TimeZone.setDefault(TimeZone.getTimeZone("America/Denver"));
        // Time Zone: America/Phoenix (Mountain Standard Time - MST)
        // TimeZone.setDefault(TimeZone.getTimeZone("America/Phoenix"));
        // Time Zone: America/Chicago (Central Time - CT)
        // TimeZone.setDefault(TimeZone.getTimeZone("America/Chicago"));
        // Time Zone: America/New_York (Eastern Time - ET)
        // TimeZone.setDefault(TimeZone.getTimeZone("America/New_York"));
        // Time Zone: America/Sao_Paulo (Brasília Time - BRT)
        // TimeZone.setDefault(TimeZone.getTimeZone("America/Sao_Paulo"));
        // Time Zone: America/Noronha (Fernando de Noronha Time - FNT)
        // TimeZone.setDefault(TimeZone.getTimeZone("America/Noronha"));
        // Time Zone: Atlantic/Azores (Azores Time - AZOT)
        // TimeZone.setDefault(TimeZone.getTimeZone("Atlantic/Azores"));
        // Time Zone: Atlantic/Cape_Verde (Cape Verde Time - CVT)
        // TimeZone.setDefault(TimeZone.getTimeZone("Atlantic/Cape_Verde"));
        // Time Zone: Africa/Casablanca (Western European Time - WET)
        // TimeZone.setDefault(TimeZone.getTimeZone("Africa/Casablanca"));
        // Time Zone: Europe/London (Greenwich Mean Time - GMT)
        // TimeZone.setDefault(TimeZone.getTimeZone("Europe/London"));
        // Time Zone: Europe/Paris (Central European Time - CET)
        // TimeZone.setDefault(TimeZone.getTimeZone("Europe/Paris"));
        // Time Zone: Europe/Moscow (Moscow Standard Time - MSK)
        // TimeZone.setDefault(TimeZone.getTimeZone("Europe/Moscow"));
        // Time Zone: Asia/Dubai (Gulf Standard Time - GST)
        // TimeZone.setDefault(TimeZone.getTimeZone("Asia/Dubai"));
        // Time Zone: Asia/Kolkata (India Standard Time - IST)
        // TimeZone.setDefault(TimeZone.getTimeZone("Asia/Kolkata"));
        // Time Zone: Asia/Hong_Kong (Hong Kong Time - HKT)
        // TimeZone.setDefault(TimeZone.getTimeZone("Asia/Hong_Kong"));
        // Time Zone: Asia/Tokyo (Japan Standard Time - JST)
        // TimeZone.setDefault(TimeZone.getTimeZone("Asia/Tokyo"));
        // Time Zone: Australia/Sydney (Australian Eastern Time - AEST)
        // TimeZone.setDefault(TimeZone.getTimeZone("Australia/Sydney"));
        // Time Zone: Pacific/Auckland (New Zealand Standard Time - NZST)
        // TimeZone.setDefault(TimeZone.getTimeZone("Pacific/Auckland"));
        // Time Zone: Pacific/Niue (Niue Time - NUT)
        // TimeZone.setDefault(TimeZone.getTimeZone("Pacific/Niue"));
        // Time Zone: Pacific/Palau (Palau Time - PWT)
        // TimeZone.setDefault(TimeZone.getTimeZone("Pacific/Palau"));
        // Time Zone: Pacific/Chatham (Chatham Island Time - CHADT)
        // TimeZone.setDefault(TimeZone.getTimeZone("Pacific/Chatham"));

        // Set language for testing OS language setting recognition and translation bundles. Uncomment line to test.
        // Danish
        // Locale.setDefault(new Locale("da"));
        // Dutch
        // Locale.setDefault(new Locale("nl"));
        // English
        // Locale.setDefault(new Locale("en"));
        // Finnish
        // Locale.setDefault(new Locale("fi"));
        // French
        // Locale.setDefault(new Locale("fr"));
        // German
        // Locale.setDefault(new Locale("de"));
        // Icelandic
        // Locale.setDefault(new Locale("is"));
        // Irish
        // Locale.setDefault(new Locale("ga"));
        // Italian
        // Locale.setDefault(new Locale("it"));
        // Norwegian
        // Locale.setDefault(new Locale("no"));
        // Portuguese
        // Locale.setDefault(new Locale("pt"));
        // Scottish Gaelic
        // Locale.setDefault(new Locale("gd"));
        // Spanish
        // Locale.setDefault(new Locale("es"));
        // Swedish
        // Locale.setDefault(new Locale("sv"));
        // Welsh
        // Locale.setDefault(new Locale("cy"));
        JDBCHelper.startDBConnection();
        launch(args);
        JDBCHelper.endDBConnection();
    }

    /**
     * This helper method displays an error alert to the user.
     *
     * @param messageKey The key for the translated error message.
     */
    private static void displayErrorMain(String messageKey) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(TranslationManagerHelper.getString("errorTitleKey"));
        alert.setHeaderText(TranslationManagerHelper.getString("errorHeaderKey"));
        alert.setContentText(TranslationManagerHelper.getString(messageKey));
        alert.showAndWait();
    }
}