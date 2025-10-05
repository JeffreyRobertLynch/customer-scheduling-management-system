package jrl.qam2final.DAO;

import java.sql.PreparedStatement;
import jrl.qam2final.Model.ReportTypeMonthlyModel;
import jrl.qam2final.Model.ReportContactMonthlyModel;
import javafx.collections.ObservableList;
import jrl.qam2final.Model.ReportCustomerMonthlyModel;
import javafx.scene.control.Alert;
import jrl.qam2final.Helper.TranslationManagerHelper;
import java.sql.ResultSet;
import javafx.collections.FXCollections;
import java.sql.SQLException;

import static jrl.qam2final.Helper.JDBCHelper.connectDB;

/**
 * Implementation of ReportDAO interface. Provides methods for operations related to generating reports using data from
 * the database. I used a year column for the generated reports to prevent appointments scheduled in different years
 * being counted as in the same month. Upon reflection, it is possible the required "Appointments by Type and Month"
 * report was intended to show appointment scheduling by month independent of year. This would show a simpler
 * representation of which appointment types were most popular at different times of the year, independent of year. My
 * report does show this, but also parses appointments by year. I hope my implementation is appropriate for meeting the
 * requirement. The additional reports follow a similar format.
 *
 * @author Jeffrey Robert Lynch
 */
public class ReportDAOIMPL implements ReportDAO{

    /**
     * Helper method for the ReportDAOIMPL class that displays SQL errors to the user and prints them to the console.
     *
     * @param e SQLException
     */
    private void handleSQLExceptionRepDAO(SQLException e) {
        System.out.println("SQL Error: " + e.getMessage());
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("SQL Error");
        alert.setHeaderText("An error occurred");
        alert.setContentText("SQL Error: " + e.getMessage());
        alert.showAndWait();
    }

    /**
     * Reads values from the database to return an ObservableList of ReportContactMonthlyModel objects. Executes a SQL
     * query to join year, month, contact name, and count the number of appointments for each from the database. This
     * data is used to generate a report of each contact's monthly appointment data. Each row is converted to a
     * ReportContactMonthlyModel object and added to the list. If a SQLException occurs, it is handled by a helper
     * method named handleSQLExceptionRepDAO.
     *
     * @return appByContactMonth ObservableList of ReportContactMonthlyModel objects representing a report on monthly
     * contact popularity.
     */
    @Override
    public ObservableList<ReportContactMonthlyModel> readReportContactMonthlyRepDAO() {
        ObservableList<ReportContactMonthlyModel> appByContactMonth = FXCollections.observableArrayList();
        int reportRowsReturned = 0;
        try {
            String sql = "SELECT monthname(a.start) AS month, YEAR(a.start) AS year, c.Contact_Name AS contact, COUNT(*) AS cnt " +
                    "FROM appointments a " +
                    "JOIN contacts c ON a.Contact_ID = c.Contact_ID " +
                    "GROUP BY month, year, contact " +
                    "ORDER BY year, STR_TO_DATE(CONCAT('01 ', month, ' ', year), '%d %M %Y')";
            PreparedStatement readReportContactMonthStatement = connectDB.prepareStatement(sql);
            ResultSet queryReturn = readReportContactMonthStatement.executeQuery();
            for (; queryReturn.next();) {
                String month_RCoM_Join = queryReturn.getString("month");
                int year_RCoM_Join = queryReturn.getInt("year");
                String contactN_RCoM_Join = queryReturn.getString("contact");
                int count_RCoM_Join = queryReturn.getInt("cnt");
                ReportContactMonthlyModel reportRCoM = new ReportContactMonthlyModel(year_RCoM_Join, month_RCoM_Join, contactN_RCoM_Join, count_RCoM_Join);
                appByContactMonth.add(reportRCoM);
                reportRowsReturned++;
            }
            displayInfoRepDAO("Appointments by Contact and Month Report", "Rows Returned for Report: " + reportRowsReturned);
        } catch (SQLException e) {
            handleSQLExceptionRepDAO(e);
        }
        return appByContactMonth;
    }

    /**
     * Reads values from the appointment database table to return an ObservableList of ReportTypeMonthlyModel objects.
     * Executes a SQL query to group by year, month, type, and count the number of appointments for each type from the
     * appointment table. This data is used to generate a report of each appointment type's monthly popularity. Each row
     * is converted to a ReportTypeMonthlyModel object and added to the list. If a SQLException occurs, it is handled
     * by a helper method named handleSQLExceptionRepDAO.
     *
     * @return appByTypeMonth ObservableList of ReportTypeMonthlyModel objects representing a report on monthly
     * appointment type popularity.
     */
    @Override
    public ObservableList<ReportTypeMonthlyModel> readReportTypeMonthlyRepDAO() {
        ObservableList<ReportTypeMonthlyModel> appByTypeMonth = FXCollections.observableArrayList();
        int reportRowsReturned = 0;
        try {
            String sql = "SELECT YEAR(start) AS year, monthname(start) AS month, type, COUNT(*) AS cnt " +
                    "FROM appointments " +
                    "GROUP BY year, month, type " +
                    "ORDER BY year, STR_TO_DATE(CONCAT('01 ', month, ' ', year), '%d %M %Y')";
            PreparedStatement readReportTypeMonthStatement = connectDB.prepareStatement(sql);
            ResultSet queryReturn = readReportTypeMonthStatement.executeQuery();
            for (; queryReturn.next();) {
                int year_RTM_Grp = queryReturn.getInt("year");
                String month_RTM_Grp = queryReturn.getString("month");
                String type_RTM_Grp = queryReturn.getString("type");
                int count_RTM_Grp = queryReturn.getInt("cnt");
                ReportTypeMonthlyModel reportRTM = new ReportTypeMonthlyModel(year_RTM_Grp, month_RTM_Grp, type_RTM_Grp, count_RTM_Grp);
                appByTypeMonth.add(reportRTM);
                reportRowsReturned++;
            }
            displayInfoRepDAO("Appointments by Type and Month Report", "Rows Returned for Report: " + reportRowsReturned);
        } catch (SQLException e) {
            handleSQLExceptionRepDAO(e);
        }
        return appByTypeMonth;
    }

    /**
     * Reads values from the database to return an ObservableList of ReportCustomerMonthlyModel objects. Executes a SQL
     * query to join year, month, customer ID, customer Name, and count the number of appointments for each from the
     * database. This data is used to generate a report of each customer's monthly appointment data. Each row is
     * converted to a ReportCustomerMonthlyModel object and added to the list. If a SQLException occurs, it is handled
     * by a helper method named handleSQLExceptionRepDAO.
     *
     * @return appByCustomerMonth ObservableList of ReportCustomerMonthlyModel objects representing a report on monthly
     * customer activity.
     */
    @Override
    public ObservableList<ReportCustomerMonthlyModel> readReportCustomerMonthlyRepDAO() {
        ObservableList<ReportCustomerMonthlyModel> appByCustomerMonth = FXCollections.observableArrayList();
        int reportRowsReturned = 0;
        try {
            String sql = "SELECT YEAR(a.start) AS year, MONTHNAME(a.start) AS month, " +
                    "c.Customer_ID AS customerId, c.Customer_Name AS customerName, " +
                    "COUNT(*) AS cnt " +
                    "FROM appointments a " +
                    "JOIN customers c ON a.Customer_ID = c.Customer_ID " +
                    "GROUP BY YEAR(a.start), MONTHNAME(a.start), " +
                    "c.Customer_ID, c.Customer_Name " +
                    "ORDER BY YEAR(a.start), STR_TO_DATE(CONCAT('01 ', month, ' ', year), '%d %M %Y')";
            PreparedStatement readReportCustomerMonthStatement = connectDB.prepareStatement(sql);
            ResultSet queryReturn = readReportCustomerMonthStatement.executeQuery();
            for (;queryReturn.next();) {
                int year_RCuM_Join = queryReturn.getInt("year");
                String month_RCuM_Join = queryReturn.getString("month");
                int customerID_RCuM_Join = queryReturn.getInt("customerId");
                String customerN_RCuM_Join = queryReturn.getString("customerName");
                int count_RCuM_Join = queryReturn.getInt("cnt");
                ReportCustomerMonthlyModel reportRCoM = new ReportCustomerMonthlyModel(year_RCuM_Join, month_RCuM_Join, customerID_RCuM_Join, customerN_RCuM_Join, count_RCuM_Join);
                appByCustomerMonth.add(reportRCoM);
                reportRowsReturned++;
            }
            displayInfoRepDAO("Appointments by Customer and Month Report", "Rows Returned for Report: " + reportRowsReturned);
        } catch (SQLException e) {
            handleSQLExceptionRepDAO(e);
        }
        return appByCustomerMonth;
    }

    /**
     * Helper method that displays information messages for methods in the reportDAOIMPL class.
     *
     * @param title Title for the alert.
     * @param message Message shown to the user.
     */
    private void displayInfoRepDAO(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}