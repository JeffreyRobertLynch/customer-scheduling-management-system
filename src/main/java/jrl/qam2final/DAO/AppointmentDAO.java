package jrl.qam2final.DAO;

import javafx.collections.ObservableList;
import jrl.qam2final.Model.AppointmentModel;
import java.time.LocalDateTime;
import java.time.LocalDate;
import java.time.LocalTime;

/**
 * AppointmentDAO interface defines methods for interacting with appointment data in the database.
 *
 * @author Jeffrey Robert Lynch
 */
public interface AppointmentDAO {

    /**
     * Reads all appointments by contact from the database. Returns an ObservableList of AppointmentModel objects.
     * Executes a SQL query that selects all appointments by contact ID from the database. Each row from the appointment
     * table is converted to an AppointmentModel object and added to the list. If a SQLException occurs, it is handled
     * by a helper method named handleSQLExceptionAppDAO. Used to generate contact schedules.
     *
     * @param contactID_ApDB Contact ID
     * @return appointmentFromContact ObservableList of AppointmentModel objects associated with the contact.
     */
    ObservableList<AppointmentModel> readAppointmentByContactAppDAO(int contactID_ApDB);

    /**
     * Reads all appointments by customer from the database. Returns an ObservableList of AppointmentModel objects.
     * Executes a SQL query that selects all appointments by customer ID from the database. Each row from the
     * appointment table is converted to an AppointmentModel object and added to the list. If a SQLException occurs, it
     * is handled by a helper method named handleSQLExceptionAppDAO. Used for overlap check.
     *
     * @param customerID_ApDB Customer ID for customer being checked for overlapping appointments.
     * @return appointmentFromCustomer ObservableList of AppointmentModel objects associated with the customer.
     */
    ObservableList<AppointmentModel> readAppointmentByCustomerAppDAO(int customerID_ApDB);

    /**
     * Reads appointments from the database. Returns an ObservableList of AppointmentModel objects. Executes a SQL
     * query that selects all appointments from the database. Each row from the appointment table is converted to an
     * AppointmentModel object and added to the list. If a SQLException occurs, it is handled by a helper method named
     * handleSQLExceptionAppDAO. used to populate GUI with appointments.
     *
     * @return appointmentFromDB ObservableList of AppointmentModel objects representing appointments in the database.
     */
    ObservableList<AppointmentModel> readAppointmentAllAppDAO();

    /**
     * Updates the information for an appointment in the database. If a SQLException occurs, it is handled by a helper
     * method named handleSQLExceptionAppDAO. If the update is successful, a message is displayed to the user using the
     * helper method displayInfoAppDAO. If the update is not successful an error message is displayed to the user using
     * the helper method displayErrorAppDAO.
     *
     * @param appointmentID_ToDB Appointment ID for updated appointment.
     * @param customerID_ToDB Customer ID for updated appointment.
     * @param userID_ToDB User ID for updated appointment.
     * @param contactID_ToDB Contact ID for updated appointment.
     * @param title_ToDB Title for updated appointment.
     * @param description_ToDB Description for updated appointment.
     * @param location_ToDB Location for updated appointment.
     * @param type_ToDB Type for updated appointment.
     * @param startDT_ToDB Start Date and Time for updated appointment.
     * @param endDT_ToDB End Date and Time for updated appointment.
     * @return True if update succeeds, false otherwise.
     */
    boolean updateAppointmentAppDAO(int appointmentID_ToDB, int customerID_ToDB, int userID_ToDB, int contactID_ToDB, String title_ToDB, String description_ToDB, String location_ToDB, String type_ToDB, LocalDateTime startDT_ToDB, LocalDateTime endDT_ToDB);

    /**
     * When called by the onActionDeleteAppointmentAMC controller method, this method deletes an appointment from the
     * database. If a SQLException occurs, it is handled by a helper method named handleSQLExceptionAppDAO. If the
     * deletion is successful, a message is displayed to the user using the helper method displayInfoAppDAO. If the
     * deletion is not successful, an error message is displayed to the user using the helper method displayErrorAppDAO.
     *
     * @param appointmentID_DelDB Appointment ID
     * @param type_DelDB Type
     * @return True if deletion succeeds, otherwise false.
     */
    boolean deleteAppointmentAppDAO(int appointmentID_DelDB, String type_DelDB);

     /**
     * Performs a check to determine if an updated appointment will cause an overlapping appointment situation for a
     * customer. It calls a helper method, updatedAppointmentOverlapHelperAppDAO, that assists with the overlap check.
     *
     * @param custID Customer ID for appointment being checked for overlap.
     * @param userAppDayStart Start Date for the appointment being checked for overlap.
     * @param userAppDayEnd End Date for the appointment being checked for overlap.
     * @param userAppClockStart Start Time for the appointment being checked for overlap.
     * @param userAppClockEnd End Time for the appointment being checked for overlap.
     * @param appID Appointment ID for the appointment
     * @return True if an overlap exists, otherwise false
     */
    boolean updatedAppointmentOverlapCheckAppDAO(int custID, LocalDate userAppDayStart, LocalDate userAppDayEnd, LocalTime userAppClockStart, LocalTime userAppClockEnd, int appID );

    /**
     * Creates a new appointment entry in the database with values provided by the onActionSaveCreatedAppointmentACC
     * controller method. If a SQLException occurs, it is handled by a helper method named handleSQLExceptionAppDAO. If
     * the insert is successful, a message is displayed to the user using the helper method displayInfoAppDAO. If the
     * insert is not successful an error message is displayed to the user using the helper method displayErrorAppDAO.
     *
     * @param customerID_ToDB Customer ID for new appointment.
     * @param userID_ToDB User ID for new appointment.
     * @param contactID_ToDB Contact ID for new appointment.
     * @param title_ToDB Title for new appointment.
     * @param description_ToDB Description for new appointment.
     * @param location_ToDB Location for new appointment.
     * @param type_ToDB Type for new appointment.
     * @param startDT_ToDB Start Date Time for new appointment.
     * @param endDT_ToDB End Date Time for new appointment.
     * @return True if insert succeeds, otherwise false.
     */
    boolean createAppointmentAppDAO(int customerID_ToDB, int userID_ToDB, int contactID_ToDB, String title_ToDB, String description_ToDB, String location_ToDB, String type_ToDB, LocalDateTime startDT_ToDB, LocalDateTime endDT_ToDB);

    /**
     * When called by the deleteCustomerCusDAO method, this method deletes a customer's appointments by customer ID
     * before the customer itself is deleted by deleteCustomerCusDAO method. If a SQLException occurs, it is handled by
     * a helper method named handleSQLExceptionAppDAO. If appointments are deleted, a message is displayed to the user
     * using the helper method displayInfoAppDAO. If the customer had no appointments to delete, a message is displayed
     * to the user using the helper method displayInfoAppDAO.
     *
     * @param customerID_DelDB Customer ID
     * @return True if deletion succeeds, false otherwise.
     */
    boolean deleteAppointmentsByCustomerIdAppDAO(int customerID_DelDB);

    /**
     * Performs a check to determine if a scheduled appointment violates defined business hours.
     *
     * @param appointmentStart Start Time for appointment
     * @return True if the appointment starts within business hours, otherwise false
     */
    boolean checkAppointmentStartAppDAO(LocalDateTime appointmentStart);

    /**
     * Filters appointments by only displaying appointments scheduled for the upcoming week. Corresponds to the "by Week
     * " radio button on the Appointment Main Screen.
     *
     * @param logInDS Log-in date
     * @return FilteredList of appointments scheduled for the upcoming week
     */
    ObservableList<AppointmentModel> radioAppointmentsWeekAppDAO(LocalDate logInDS);

    /**
     * Filters appointments by only displaying appointments scheduled for the current calendar month. Corresponds to the
     * "by Month" radio button on the Appointment Main Screen.
     *
     * @param logInDS Log-in date
     * @return FilteredList of appointments scheduled for the current calendar month
     */
    ObservableList<AppointmentModel> radioAppointmentsMonthAppDAO(LocalDate logInDS);

    /**
     * Performs a check to determine if a scheduled appointment violates defined business hours.
     *
     * @param appointmentEnd End Time for appointment
     * @return True if the appointment ends within business hours, otherwise false
     */
    boolean checkAppointmentEndAppDAO(LocalDateTime appointmentEnd);

    /**
     * Performs a check to determine if a created appointment will cause an overlapping appointment situation for a
     * customer.
     *
     * @param custID Customer ID for appointment being checked for overlap.
     * @param userAppDayStart Start Date for the appointment being checked for overlap.
     * @param userAppDayEnd End Date for the appointment being checked for overlap.
     * @param userAppClockStart Start Time for the appointment being checked for overlap.
     * @param userAppClockEnd End Time for the appointment being checked for overlap.
     * @return True if an overlap exists, otherwise false
     */
    boolean createdAppointmentOverlapCheckAppDAO(int custID, LocalDate userAppDayStart, LocalDate userAppDayEnd, LocalTime userAppClockStart, LocalTime userAppClockEnd);

}