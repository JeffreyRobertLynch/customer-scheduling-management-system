package jrl.qam2final.DAO;

import javafx.scene.control.Alert;
import jrl.qam2final.Model.AppointmentModel;
import javafx.collections.transformation.FilteredList;
import java.sql.PreparedStatement;
import jrl.qam2final.Helper.TranslationManagerHelper;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.*;
import javafx.collections.FXCollections;
import java.sql.Timestamp;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import javafx.collections.ObservableList;

import static jrl.qam2final.Helper.JDBCHelper.connectDB;

/**
 * Implementation class for the AppointmentDAO interface. Provides methods to interact with appointment data in the
 * database.
 *
 * @author Jeffrey Robert Lynch
 */
public class AppointmentDAOIMPL implements AppointmentDAO {

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
    @Override
    public boolean createAppointmentAppDAO(int customerID_ToDB, int userID_ToDB, int contactID_ToDB, String title_ToDB, String description_ToDB, String location_ToDB, String type_ToDB, LocalDateTime startDT_ToDB, LocalDateTime endDT_ToDB) {
        boolean createSuccessful = false;
        int updatedAppointmentsCount = 0;
        try {
            String sql = "INSERT INTO appointments SET\n" +
                    "Customer_ID = ?,\n" +
                    "User_ID = ?,\n" +
                    "Contact_ID = ?,\n" +
                    "Title = ?,\n" +
                    "Description = ?,\n" +
                    "Location = ?,\n" +
                    "Type = ?,\n" +
                    "Start = ?,\n" +
                    "End = ?";
            PreparedStatement createAppointmentStatement = connectDB.prepareStatement(sql);
            createAppointmentStatement.setInt(1, customerID_ToDB);
            createAppointmentStatement.setInt(2, userID_ToDB);
            createAppointmentStatement.setInt(3, contactID_ToDB);
            createAppointmentStatement.setString(4, title_ToDB);
            createAppointmentStatement.setString(5, description_ToDB);
            createAppointmentStatement.setString(6, location_ToDB);
            createAppointmentStatement.setString(7, type_ToDB);
            createAppointmentStatement.setTimestamp(8, Timestamp.valueOf(startDT_ToDB));
            createAppointmentStatement.setTimestamp(9, Timestamp.valueOf(endDT_ToDB));
            int modifiedRowCount = createAppointmentStatement.executeUpdate();
            if (modifiedRowCount > 0) {
                createSuccessful = true;
                updatedAppointmentsCount = modifiedRowCount;
                displayInfoAppDAO("Create Appointment", updatedAppointmentsCount + " Appointment created.");
            } else {
                displayErrorAppDAO("Create Appointment", "Failed Insert operation.");
            }
        } catch (SQLException e) {
            handleSQLExceptionAppDAO(e);
        }
        return createSuccessful;
    }

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
    @Override
    public boolean createdAppointmentOverlapCheckAppDAO(int custID, LocalDate userAppDayStart, LocalDate userAppDayEnd, LocalTime userAppClockStart, LocalTime userAppClockEnd) {
        AppointmentDAO newAppointmentDAO = createAppointmentDAOIMPLAppDAO();
        ObservableList<AppointmentModel> appointmentFromCustomer = newAppointmentDAO.readAppointmentByCustomerAppDAO(custID);
        for (AppointmentModel appointment : appointmentFromCustomer) {
            if ((appointment.getStaD_LD().isEqual(userAppDayStart) || appointment.getEndD_LD().isEqual(userAppDayEnd))
                    && ((appointment.getStaT_LT().equals(userAppClockStart))
                    || (appointment.getStaT_LT().isAfter(userAppClockStart) && appointment.getStaT_LT().isBefore(userAppClockEnd))
                    || (userAppClockStart.isBefore(appointment.getStaT_LT()) && userAppClockEnd.isAfter(appointment.getStaT_LT())))) {
                return true;
            }
        }
        return false;
    }

    /**
     * Reads all appointments by customer from the database. Returns an ObservableList of AppointmentModel objects.
     * Executes a SQL query that selects all appointments by customer ID from the database. Each row from the
     * appointment table is converted to an AppointmentModel object and added to the list. If a SQLException occurs, it
     * is handled by a helper method named handleSQLExceptionAppDAO. Used for overlap check.
     *
     * @param customerID_ApDB Customer ID for customer being checked for overlapping appointments.
     * @return appointmentFromCustomer ObservableList of AppointmentModel objects associated with the customer.
     */
    @Override
    public ObservableList<AppointmentModel> readAppointmentByCustomerAppDAO(int customerID_ApDB) {
        ObservableList<AppointmentModel> appointmentFromCustomer = FXCollections.observableArrayList();
        try {
            String sql = "SELECT \n" +
                    "    Appointment_ID, \n" +
                    "    Customer_ID, \n" +
                    "    User_ID, \n" +
                    "    Contact_ID, \n" +
                    "    Title, \n" +
                    "    Description, \n" +
                    "    Location, \n" +
                    "    Type, \n" +
                    "    Start, \n" +
                    "    End \n" +
                    "FROM \n" +
                    "    appointments \n" +
                    "WHERE \n" +
                    "    Customer_ID=?";
            PreparedStatement readAppointmentCustomer = connectDB.prepareStatement(sql);
            readAppointmentCustomer.setInt(1, customerID_ApDB);
            ResultSet queryReturn = readAppointmentCustomer.executeQuery();
            for (; queryReturn.next();) {
                int appointmentID_ApDB = queryReturn.getInt("Appointment_ID");
                customerID_ApDB = queryReturn.getInt("Customer_ID");
                int userID_ApDB = queryReturn.getInt("User_ID");
                int contactID_ApDB = queryReturn.getInt("Contact_ID");
                String title_ApDB = queryReturn.getString("Title");
                String description_ApDB = queryReturn.getString("Description");
                String location_ApDB = queryReturn.getString("Location");
                String type_ApDB = queryReturn.getString("Type");
                LocalDateTime appStartDT_ApDB = queryReturn.getTimestamp("Start").toLocalDateTime();
                LocalDateTime appEndDT_ApDB = queryReturn.getTimestamp("End").toLocalDateTime();
                LocalDate appStartD_ApDB = appStartDT_ApDB.toLocalDate();
                LocalTime appStartT_ApDB = appStartDT_ApDB.toLocalTime();
                LocalDate appEndD_ApDB = appEndDT_ApDB.toLocalDate();
                LocalTime appEndT_ApDB = appEndDT_ApDB.toLocalTime();
                AppointmentModel appointmentDB = new AppointmentModel(appointmentID_ApDB, title_ApDB, description_ApDB, location_ApDB, type_ApDB, appStartDT_ApDB, appEndDT_ApDB, appStartD_ApDB, appEndD_ApDB, appStartT_ApDB, appEndT_ApDB, customerID_ApDB, userID_ApDB, contactID_ApDB);
                appointmentFromCustomer.add(appointmentDB);
            }
        } catch (SQLException e) {
            handleSQLExceptionAppDAO(e);
        }
        return appointmentFromCustomer;
    }

    /**
     * Reads all appointments by contact from the database. Returns an ObservableList of AppointmentModel objects.
     * Executes a SQL query that selects all appointments by contact ID from the database. Each row from the appointment
     * table is converted to an AppointmentModel object and added to the list. If a SQLException occurs, it is handled
     * by a helper method named handleSQLExceptionAppDAO. Used to generate contact schedules.
     *
     * @param contactID_ApDB Contact ID
     * @return appointmentFromContact ObservableList of AppointmentModel objects associated with the contact.
     */
    @Override
    public ObservableList<AppointmentModel> readAppointmentByContactAppDAO(int contactID_ApDB) {
        ObservableList<AppointmentModel> appointmentFromContact = FXCollections.observableArrayList();
        try {
            String sql = "SELECT \n" +
                    "    Appointment_ID, \n" +
                    "    Customer_ID, \n" +
                    "    User_ID, \n" +
                    "    Contact_ID, \n" +
                    "    Title, \n" +
                    "    Description, \n" +
                    "    Location, \n" +
                    "    Type, \n" +
                    "    Start, \n" +
                    "    End \n" +
                    "FROM \n" +
                    "    appointments \n" +
                    "WHERE \n" +
                    "    Contact_ID=?";
            PreparedStatement readAppointmentContact = connectDB.prepareStatement(sql);
            readAppointmentContact.setInt(1, contactID_ApDB);
            ResultSet queryReturn = readAppointmentContact.executeQuery();
            for (; queryReturn.next();) {
                int appointmentID_ApDB = queryReturn.getInt("Appointment_ID");
                int customerID_ApDB = queryReturn.getInt("Customer_ID");
                int userID_ApDB = queryReturn.getInt("User_ID");
                contactID_ApDB = queryReturn.getInt("Contact_ID");
                String title_ApDB = queryReturn.getString("Title");
                String description_ApDB = queryReturn.getString("Description");
                String location_ApDB = queryReturn.getString("Location");
                String type_ApDB = queryReturn.getString("Type");
                LocalDateTime appStartDT_ApDB = queryReturn.getTimestamp("Start").toLocalDateTime();
                LocalDateTime appEndDT_ApDB = queryReturn.getTimestamp("End").toLocalDateTime();
                LocalDate appStartD_ApDB = appStartDT_ApDB.toLocalDate();
                LocalTime appStartT_ApDB = appStartDT_ApDB.toLocalTime();
                LocalDate appEndD_ApDB = appEndDT_ApDB.toLocalDate();
                LocalTime appEndT_ApDB = appEndDT_ApDB.toLocalTime();
                AppointmentModel appointmentDB = new AppointmentModel(appointmentID_ApDB, title_ApDB, description_ApDB, location_ApDB, type_ApDB, appStartDT_ApDB, appEndDT_ApDB, appStartD_ApDB, appEndD_ApDB, appStartT_ApDB, appEndT_ApDB, customerID_ApDB, userID_ApDB, contactID_ApDB);
                appointmentFromContact.add(appointmentDB);
            }
        } catch (SQLException e) {
            handleSQLExceptionAppDAO(e);
        }
        return appointmentFromContact;
    }

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
    @Override
    public boolean updateAppointmentAppDAO(int appointmentID_ToDB, int customerID_ToDB, int userID_ToDB, int contactID_ToDB, String title_ToDB, String description_ToDB, String location_ToDB, String type_ToDB, LocalDateTime startDT_ToDB, LocalDateTime endDT_ToDB) {
        boolean updateSuccessful = false;
        int updatedAppointmentsCount = 0;
        try {
            String sql = "UPDATE appointments\n" +
                    "SET \n" +
                    "    Customer_ID = ?,\n" +
                    "    User_ID = ?,\n" +
                    "    Contact_ID = ?,\n" +
                    "    Title = ?,\n" +
                    "    Description = ?,\n" +
                    "    Location = ?,\n" +
                    "    Type = ?,\n" +
                    "    Start = ?,\n" +
                    "    End = ?\n" +
                    "WHERE\n" +
                    "    Appointment_ID = ?";
            PreparedStatement updateAppointmentStatement = connectDB.prepareStatement(sql);
            updateAppointmentStatement.setInt(1, customerID_ToDB);
            updateAppointmentStatement.setInt(2, userID_ToDB);
            updateAppointmentStatement.setInt(3, contactID_ToDB);
            updateAppointmentStatement.setString(4, title_ToDB);
            updateAppointmentStatement.setString(5, description_ToDB);
            updateAppointmentStatement.setString(6, location_ToDB);
            updateAppointmentStatement.setString(7, type_ToDB);
            updateAppointmentStatement.setTimestamp(8, Timestamp.valueOf(startDT_ToDB));
            updateAppointmentStatement.setTimestamp(9, Timestamp.valueOf(endDT_ToDB));
            updateAppointmentStatement.setInt(10, appointmentID_ToDB);
            int modifiedRowCount = updateAppointmentStatement.executeUpdate();
            if (modifiedRowCount > 0) {
                updateSuccessful = true;
                updatedAppointmentsCount = modifiedRowCount;
                displayInfoAppDAO("Update Appointment", updatedAppointmentsCount + " Appointment with Appointment ID: " + appointmentID_ToDB + " updated.");
            } else {
                displayErrorAppDAO("Update Appointment", "Failed Update operation.");
            }
        } catch (SQLException e) {
            handleSQLExceptionAppDAO(e);
        }
        return updateSuccessful;
    }

    /**
     * Reads appointments from the database. Returns an ObservableList of AppointmentModel objects. Executes a SQL
     * query that selects all appointments from the database. Each row from the appointment table is converted to an
     * AppointmentModel object and added to the list. If a SQLException occurs, it is handled by a helper method named
     * handleSQLExceptionAppDAO. used to populate GUI with appointments.
     *
     * @return appointmentFromDB ObservableList of AppointmentModel objects representing appointments in the database.
     */
    @Override
    public ObservableList<AppointmentModel> readAppointmentAllAppDAO() {
        ObservableList<AppointmentModel> appointmentFromDB = FXCollections.observableArrayList();
        try {
            String sql = "SELECT \n" +
                    "    Appointment_ID, \n" +
                    "    Customer_ID, \n" +
                    "    User_ID, \n" +
                    "    Contact_ID, \n" +
                    "    Title, \n" +
                    "    Description, \n" +
                    "    Location, \n" +
                    "    Type, \n" +
                    "    Start, \n" +
                    "    End \n" +
                    "FROM \n" +
                    "    appointments";
            PreparedStatement readAppointmentAllStatement = connectDB.prepareStatement(sql);
            ResultSet queryReturn = readAppointmentAllStatement.executeQuery();
            for (; queryReturn.next();) {
                int appointmentID_ApDB = queryReturn.getInt("Appointment_ID");
                int customerID_ApDB = queryReturn.getInt("Customer_ID");
                int userID_ApDB = queryReturn.getInt("User_ID");
                int contactID_ApDB = queryReturn.getInt("Contact_ID");
                String title_ApDB = queryReturn.getString("Title");
                String description_ApDB = queryReturn.getString("Description");
                String location = queryReturn.getString("Location");
                String type_ApDB = queryReturn.getString("Type");
                LocalDateTime appStartDT_ApDB = queryReturn.getTimestamp("Start").toLocalDateTime();
                LocalDateTime appEndDT_ApDB = queryReturn.getTimestamp("End").toLocalDateTime();
                LocalDate appStartD_ApDB = appStartDT_ApDB.toLocalDate();
                LocalTime appStartT_ApDB = appStartDT_ApDB.toLocalTime();
                LocalDate appEndD_ApDB = appEndDT_ApDB.toLocalDate();
                LocalTime appEndT_ApDB = appEndDT_ApDB.toLocalTime();
                AppointmentModel appointmentDB = new AppointmentModel(appointmentID_ApDB, title_ApDB, description_ApDB,
                        location, type_ApDB, appStartDT_ApDB, appEndDT_ApDB, appStartD_ApDB, appEndD_ApDB, appStartT_ApDB, appEndT_ApDB, customerID_ApDB, userID_ApDB, contactID_ApDB);
                appointmentFromDB.add(appointmentDB);
            }
        } catch (SQLException e) {
            handleSQLExceptionAppDAO(e);
        }
        return appointmentFromDB;
    }

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
    @Override
    public boolean deleteAppointmentAppDAO(int appointmentID_DelDB, String type_DelDB) {
        boolean deleteSuccessful = false;
        int deletedAppointmentsCount = 0;
        try {
            String sql = "DELETE FROM appointments\n" +
                    "WHERE \n" +
                    "    Appointment_ID = ?\n" +
                    "    AND Type = ?";
            PreparedStatement deleteAppointmentStatement = connectDB.prepareStatement(sql);
            deleteAppointmentStatement.setInt(1, appointmentID_DelDB);
            deleteAppointmentStatement.setString(2, type_DelDB);
            int modifiedRowCount  = deleteAppointmentStatement.executeUpdate();
            if (modifiedRowCount  > 0) {
                deleteSuccessful = true;
                deletedAppointmentsCount = modifiedRowCount;
                displayInfoAppDAO("Delete Appointment", deletedAppointmentsCount + " Appointment with ID: " + appointmentID_DelDB + " and Type: " + type_DelDB + " deleted successfully.");
            } else {
                displayErrorAppDAO("Delete Appointment", "Appointment with ID: " + appointmentID_DelDB + " and Type: " + type_DelDB + " failed deletion.");
            }
        } catch (SQLException e) {
            handleSQLExceptionAppDAO(e);
        }
        return deleteSuccessful;
    }

    /**
     * When called by the deleteCustomerCusDAO method, this method deletes a customer's appointments by customer ID
     * before the customer itself is deleted by deleteCustomerCusDAO method. If a SQLException occurs, it is handled by
     * a helper method named handleSQLExceptionAppDAO. If appointments are deleted, a message displays the customer ID
     * of the customer and the number of appointments deleted. If the customer had no appointments to delete, a message
     * confirms no appointments were found to delete.
     *
     * @param customerID_DelDB Customer ID
     * @return True if deletion succeeds, false otherwise.
     */
    public boolean deleteAppointmentsByCustomerIdAppDAO(int customerID_DelDB) {
        boolean deletionSuccess = false;
        int deletedAppointmentsCount = 0;
        try {
            String sql = "DELETE FROM appointments " +
                    "WHERE Customer_ID=?";
            PreparedStatement deleteAppointmentCustomerIdStatement = connectDB.prepareStatement(sql);
            deleteAppointmentCustomerIdStatement.setInt(1, customerID_DelDB);
            int modifiedRowCount = deleteAppointmentCustomerIdStatement.executeUpdate();
            if (modifiedRowCount > 0) {
                deletionSuccess = true;
                deletedAppointmentsCount = modifiedRowCount;
                displayInfoAppDAO("Delete Customer Appointments", "A total of " + deletedAppointmentsCount + " appointments for Customer ID: " + customerID_DelDB + " have been deleted.");
            } else {
                displayInfoAppDAO("Delete Customer Appointments", "No scheduled appointments found for Customer ID: " + customerID_DelDB);
            }
        } catch (SQLException e) {
            handleSQLExceptionAppDAO(e);
        }
        return deletionSuccess;
    }

    /**
     * Filters appointments by only displaying appointments scheduled for the upcoming week. Corresponds to the "by Week
     * " radio button on the Appointment Main Screen.
     *
     * @param logInDS Log-In date
     * @return FilteredList of appointments scheduled for the upcoming week
     */
    @Override
    public FilteredList<AppointmentModel> radioAppointmentsWeekAppDAO(LocalDate logInDS) {
        ObservableList<AppointmentModel> appointmentsToCheck;
        appointmentsToCheck = readAppointmentAllAppDAO();
        Predicate<AppointmentModel> filterCon = appointment -> {
            LocalDate appointmentStart = appointment.getStaD_LD();
            LocalDate endWeek = logInDS.plusDays(7);
            return appointmentStart.equals(logInDS) || (appointmentStart.isAfter(logInDS) && appointmentStart.isBefore(endWeek));
        };
        List<AppointmentModel> AppointmentsWeek = appointmentsToCheck.stream()
                .filter(filterCon)
                .collect(Collectors.toList());
        return new FilteredList<>(FXCollections.observableArrayList(AppointmentsWeek));
    }

    /**
     * Filters appointments by only displaying appointments scheduled for the current calendar month. Corresponds to the
     * "by Month" radio button on the Appointment Main Screen.
     *
     * @param logInDS Log-In date
     * @return FilteredList of appointments scheduled for the current calendar month
     */
    @Override
    public FilteredList<AppointmentModel> radioAppointmentsMonthAppDAO(LocalDate logInDS) {
        ObservableList<AppointmentModel> appointmentsToCheck;
        appointmentsToCheck = readAppointmentAllAppDAO();
        Predicate<AppointmentModel> filterCon = appointment -> {
            LocalDate appointmentStart = appointment.getStaD_LD();
            return appointmentStart.isAfter(logInDS.minusDays(1)) && appointmentStart.getMonth().equals(logInDS.getMonth());
        };
        List<AppointmentModel> appointmentsMonth = appointmentsToCheck.stream()
                .filter(filterCon)
                .collect(Collectors.toList());
        return new FilteredList<>(FXCollections.observableArrayList(appointmentsMonth));
    }

    /**
     * Performs a check to determine if a scheduled appointment violates defined business hours.
     *
     * @param appointmentStart Start Time for appointment
     * @return True if the appointment starts within business hours, otherwise false
     */
    @Override
    public boolean checkAppointmentStartAppDAO(LocalDateTime appointmentStart) {
        ZonedDateTime appointmentTimeZone = appointmentStart.atZone(ZoneId.systemDefault());
        appointmentTimeZone = appointmentTimeZone.withZoneSameInstant(ZoneId.of("US/Eastern"));
        appointmentStart = appointmentTimeZone.toLocalDateTime();
        LocalTime beginBusinessDay = LocalTime.of(8, 0);
        LocalTime endBusinessDay = LocalTime.of(22, 0);
        LocalTime appointmentTime = appointmentStart.toLocalTime();
        return appointmentTime.compareTo(beginBusinessDay) >= 0 && appointmentTime.compareTo(endBusinessDay) < 0;
    }

    /**
     * Performs a check to determine if a scheduled appointment violates defined business hours.
     *
     * @param appointmentEnd End Time for appointment
     * @return True if the appointment ends within business hours, otherwise false
     */
    @Override
    public boolean checkAppointmentEndAppDAO(LocalDateTime appointmentEnd) {
        ZonedDateTime appointmentTimeZone = appointmentEnd.atZone(ZoneId.systemDefault());
        appointmentTimeZone = appointmentTimeZone.withZoneSameInstant(ZoneId.of("US/Eastern"));
        appointmentEnd = appointmentTimeZone.toLocalDateTime();
        LocalTime beginBusinessDay = LocalTime.of(8, 0);
        LocalTime endBusinessDay = LocalTime.of(22, 0);
        LocalTime appointmentTime = appointmentEnd.toLocalTime();
        return appointmentTime.compareTo(beginBusinessDay) >= 0 && appointmentTime.compareTo(endBusinessDay) < 0;
    }

    /**
     * Performs a check to determine if an updated appointment will cause an overlapping appointment situation for a
     * customer. It calls a helper method, updatedAppointmentOverlapHelperAppDAO, that assists with the overlap check.
     *
     * @param custID  Customer ID for appointment being checked for overlap.
     * @param userAppDayStart Start Date for the appointment being checked for overlap.
     * @param userAppDayEnd End Date for the appointment being checked for overlap.
     * @param userAppClockStart Start Time for the appointment being checked for overlap.
     * @param userAppClockEnd End Time for the appointment being checked for overlap.
     * @param appID Appointment ID for the appointment
     * @return True if an overlap exists, otherwise false
     */
    @Override
    public boolean updatedAppointmentOverlapCheckAppDAO(int custID, LocalDate userAppDayStart, LocalDate userAppDayEnd, LocalTime userAppClockStart, LocalTime userAppClockEnd, int appID) {
        AppointmentDAO newAppointmentDAO = createAppointmentDAOIMPLAppDAO();
        ObservableList<AppointmentModel> appointmentFromCustomer = newAppointmentDAO.readAppointmentByCustomerAppDAO(custID);
        for (AppointmentModel appointment : appointmentFromCustomer) {
            if (appointment.getAppID_IN() != appID) {
                if (updatedAppointmentOverlapHelperAppDAO(userAppDayStart, userAppDayEnd, userAppClockStart, userAppClockEnd ,
                        appointment.getStaD_LD(), appointment.getEndD_LD(), appointment.getStaT_LT(), appointment.getEndT_LT())) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Helper method for updatedAppointmentOverlapCheckAppDAO. I created this helper method because my original method
     * was incorrectly giving an appointment overlap error if an updated appointment was scheduled for the same time and
     * date as the original appointment. Contains print statements.
     *
     * @param startDateX Start Date for appointment 1
     * @param endDateX End Date for appointment 1
     * @param startTimeX Start Time for appointment 1
     * @param endTimeX End Time for appointment 1
     * @param startDateY Start Date for appointment 2
     * @param endDateY End Date for appointment 2
     * @param startTimeY Start Time for appointment 2
     * @param endTimeY End Time for appointment 2
     * @return True if and overlap exists between the two appointments, otherwise false
     */
    private boolean updatedAppointmentOverlapHelperAppDAO(LocalDate startDateX, LocalDate endDateX, LocalTime startTimeX, LocalTime endTimeX, LocalDate startDateY, LocalDate endDateY, LocalTime startTimeY, LocalTime endTimeY) {
        System.out.println("StartDateTimeX: " + LocalDateTime.of(startDateX, startTimeX));
        System.out.println("EndDateTimeX: " + LocalDateTime.of(endDateX, endTimeX));
        System.out.println("StartDateTimeY: " + LocalDateTime.of(startDateY, startTimeY));
        System.out.println("EndDateTimeY: " + LocalDateTime.of(endDateY, endTimeY));
        LocalDateTime startDateTime1 = LocalDateTime.of(startDateX, startTimeX);
        LocalDateTime endDateTime1 = LocalDateTime.of(endDateX, endTimeX);
        LocalDateTime startDateTime2 = LocalDateTime.of(startDateY, startTimeY);
        LocalDateTime endDateTime2 = LocalDateTime.of(endDateY, endTimeY);
        boolean scheduleOverlap = (startDateTime1.isBefore(endDateTime2) && endDateTime1.isAfter(startDateTime2)) ||
                (startDateTime2.isBefore(endDateTime1) && endDateTime2.isAfter(startDateTime1));
        return scheduleOverlap;
    }

    /**
     * This method is used to create new data access objects.
     *
     * @return New instance of AppointmentDAOIMPL.
     */
    private AppointmentDAO createAppointmentDAOIMPLAppDAO() {
        return new AppointmentDAOIMPL();
    }

    /**
     * Helper method for the AppointmentDAOIMPL class that displays SQL errors to the user and prints them to the
     * console.
     *
     * @param e SQLException
     */
    private void handleSQLExceptionAppDAO(SQLException e) {
        System.out.println("SQL Error: " + e.getMessage());
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("SQL Error");
        alert.setHeaderText("An error occurred");
        alert.setContentText("SQL Error: " + e.getMessage());
        alert.showAndWait();
    }

    /**
     * Helper method that displays information messages for methods in the AppointmentDAOIMPL class.
     *
     * @param title Title for the alert.
     * @param message Message shown to the user.
     */
    private void displayInfoAppDAO(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    /**
     * Helper method that displays error messages for methods in the AppointmentDAOIMPL class.
     *
     * @param title Title for the alert.
     * @param message Message displayed to user.
     */
    private void displayErrorAppDAO(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}