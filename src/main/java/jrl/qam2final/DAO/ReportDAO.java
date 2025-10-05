package jrl.qam2final.DAO;

import javafx.collections.ObservableList;
import jrl.qam2final.Model.ReportTypeMonthlyModel;
import jrl.qam2final.Model.ReportContactMonthlyModel;
import jrl.qam2final.Model.ReportCustomerMonthlyModel;

/**
 * ReportDAO interface defines methods for the ReportDAOIMPL used to generate reports from database data.
 *
 * @author Jeffrey Robert Lynch
 */
public interface ReportDAO {

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
    ObservableList<ReportTypeMonthlyModel> readReportTypeMonthlyRepDAO();

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
    ObservableList<ReportContactMonthlyModel> readReportContactMonthlyRepDAO();

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
    ObservableList<ReportCustomerMonthlyModel> readReportCustomerMonthlyRepDAO();
}