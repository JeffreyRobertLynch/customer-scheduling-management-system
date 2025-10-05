package jrl.qam2final.Helper;

import jrl.qam2final.Helper.TranslationManagerHelper;
import java.sql.PreparedStatement;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import jrl.qam2final.DAO.AppointmentDAO;
import jrl.qam2final.DAO.AppointmentDAOIMPL;
import jrl.qam2final.Model.AppointmentModel;
import jrl.qam2final.Model.UserModel;
import java.sql.ResultSet;
import java.time.ZoneId;
import java.time.LocalDateTime;
import java.sql.SQLException;

import static jrl.qam2final.Helper.JDBCHelper.connectDB;

/**
 * Helper class for logging into the database, performing a check for upcoming appointments, and displaying upcoming
 * appointments at Log-In.
 *
 * @author Jeffrey Robert Lynch
 */
public class LogInTimeCheckHelper {

    /**
     * Helper method that displays error messages for SQLExceptions.
     *
     * @param e SQLException object.
     */
    private static void handleSQLExceptionLITCH(SQLException e) {
        System.out.println("SQL Error: " + e.getMessage());
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("SQL Error");
        alert.setHeaderText("An error occurred");
        alert.setContentText("SQL Error: " + e.getMessage());
        alert.showAndWait();
    }

    /**
     * Performs a check to determine if there are any appointments within 15 minutes of log-in. If there are imminent
     * appointments, the method displays a message to the user. If there are no imminent appointments, it displays
     * a different message to the user.
     *
     * @param localTimeDate LocalDateTime representing the current time
     */
    public static void checkImminentAppointmentLITCH(LocalDateTime localTimeDate) {
        AppointmentDAO newAppointmentDAO = new AppointmentDAOIMPL();
        ObservableList<AppointmentModel> allAppointments = newAppointmentDAO.readAppointmentAllAppDAO();
        LocalDateTime appointmentStartCheck = localTimeDate.plusMinutes(15);
        ObservableList<AppointmentModel> appointmentImminent = FXCollections.observableArrayList();
        for (AppointmentModel appointment : allAppointments) {
            LocalDateTime appointmentStart = appointment.getStaDT_LDT();
            if (appointmentStart.isAfter(localTimeDate) && appointmentStart.isBefore(appointmentStartCheck)) {
                appointmentImminent.add(appointment);
            }
        }
        if (appointmentImminent.isEmpty()) {
            displayImminentAppointmentWarningLITCH("No Appointments Imminent", "No appointments are scheduled to start within the next 15 minutes.", appointmentImminent);
        } else {
            displayImminentAppointmentWarningLITCH("Imminent Appointments", "Details for appointments scheduled to start within 15 minutes:", appointmentImminent);
        }
    }

    /**
     * Reads the current local time and date for the user's system and saves it.
     *
     * @return localTimeDate The current time and date for the user's locale.
     */
    public static LocalDateTime readUserTimeLITCH(){
        LocalDateTime localTimeDate = LocalDateTime.now(ZoneId.systemDefault());
        return localTimeDate;
    }

    /**
     * Helper method to display an imminent appointment warning, with appointment information for imminent appointments,
     * after the user has successfully logged-in. This method is used to display a warning if appointments are scheduled
     * to begin within 15 minutes of log-in. If not, a custom message and empty list is displayed.
     *
     * @param title Title for alert
     * @param headerText Header for alert
     * @param appointmentImminent List of imminent appointments
     */
    private static void displayImminentAppointmentWarningLITCH(String title, String headerText, ObservableList<AppointmentModel> appointmentImminent) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle(title);
        alert.setHeaderText(headerText);
        StringBuilder alertText = new StringBuilder();
        for (AppointmentModel imminentAppointment : appointmentImminent) {
            alertText.append("Appointment with ID: ").append(imminentAppointment.getAppID_IN()).append(" starting at Time: ").append(imminentAppointment.getStaT_LT())
                    .append(" and Date: ").append(imminentAppointment.getStaD_LD()).append("\n");
        }
        alert.setContentText(alertText.toString());
        alert.showAndWait();
    }

    /**
     * Queries the database to find a match for the username and password input.
     *
     * @param inputUserN The username provided by the user as input.
     * @param inputPass The password provided by the user as input.
     * @return The UserModel object if the username and password match, otherwise null.
     */
    public static UserModel queryUserNamePasswordLITCH(String inputUserN, String inputPass) {
        try {
            String sql = "SELECT \n" +
                    "    User_ID, \n" +
                    "    User_Name, \n" +
                    "    Password \n" +
                    "FROM \n" +
                    "    users \n" +
                    "WHERE \n" +
                    "    User_Name IN (?) \n" +
                    "    AND Password IN (?)";
            PreparedStatement passwordStatement = connectDB.prepareStatement(sql);
            passwordStatement.setString(1, inputUserN);
            passwordStatement.setString(2, inputPass);
            ResultSet queryReturn = passwordStatement.executeQuery();
            UserModel successFailResult = null;
            if (queryReturn.next()) {
                int userId = queryReturn.getInt("User_ID");
                inputUserN = queryReturn.getString("User_Name");
                inputPass = queryReturn.getString("Password");
                successFailResult = new UserModel(userId, inputUserN, inputPass);
            }
            return successFailResult;
        } catch (SQLException e) {
            handleSQLExceptionLITCH(e);
            return null;
        }
    }
}