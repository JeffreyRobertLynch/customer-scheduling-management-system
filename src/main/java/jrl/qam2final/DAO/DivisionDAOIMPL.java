package jrl.qam2final.DAO;

import javafx.collections.ObservableList;
import jrl.qam2final.Model.DivisionModel;
import javafx.scene.control.Alert;
import java.sql.ResultSet;
import jrl.qam2final.Helper.TranslationManagerHelper;
import java.sql.SQLException;
import javafx.collections.FXCollections;
import java.sql.PreparedStatement;

import static jrl.qam2final.Helper.JDBCHelper.connectDB;

/**
 * Implementation of DivisionDAO interface. Provides methods for operations related to division data in the database.
 *
 * @author Jeffrey Robert Lynch
 */
public class DivisionDAOIMPL implements DivisionDAO {

    ObservableList<DivisionModel> divisionsByCountry = FXCollections.observableArrayList();

    /**
     * Helper method for the DivisionDAOIMPL class that displays SQL errors to the user and prints them to the console.
     *
     * @param e SQLException
     */
    private void handleSQLExceptionDivDAO(SQLException e) {
        System.out.println("SQL Error: " + e.getMessage());

        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("SQL Error");
        alert.setHeaderText("An error occurred");
        alert.setContentText("SQL Error: " + e.getMessage());
        alert.showAndWait();
    }

    /**
     * Reads division data from the database, returning an ObservableList of DivisionModel objects. Executes a SQL
     * query that selects and joins division and country data. Each row from the division table is converted to a
     * DivisionModel object and added to the list. If a SQLException occurs, it is handled by a helper method named
     * handleSQLExceptionDivDAO.
     *
     * @return divisionsByCountry ObservableList of DivisionModel objects.
     */
    @Override
    public ObservableList<DivisionModel> readDivisionFromCountryDivDAO(int countryID_DiDB) {
        try {
            String sql = "SELECT d.Division_ID, d.Country_ID, d.Division, c.Country " +
                    "FROM first_level_divisions d " +
                    "JOIN countries c ON d.Country_ID = c.Country_ID " +
                    "WHERE d.Country_ID=?";
            PreparedStatement readDivisionCountryIdStatement = connectDB.prepareStatement(sql);
            readDivisionCountryIdStatement.setInt(1, countryID_DiDB);

            ResultSet queryReturn = readDivisionCountryIdStatement.executeQuery();
            for (; queryReturn.next();) {
                int divisionID_DiDB = queryReturn.getInt("Division_ID");
                int upCountryID = queryReturn.getInt("Country_ID");
                String divisionN_DiDB = queryReturn.getString("Division");
                String countryN_DiDB = queryReturn.getString("Country");
                DivisionModel divisionDB = new DivisionModel(divisionID_DiDB, upCountryID, divisionN_DiDB, countryN_DiDB);
                divisionsByCountry.add(divisionDB);
            }
        } catch (SQLException e) {
            handleSQLExceptionDivDAO(e);
        }
        return divisionsByCountry;
    }
}