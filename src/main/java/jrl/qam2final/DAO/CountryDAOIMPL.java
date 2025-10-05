package jrl.qam2final.DAO;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import jrl.qam2final.Model.CountryModel;
import javafx.scene.control.Alert;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import jrl.qam2final.Helper.TranslationManagerHelper;
import java.sql.SQLException;

import static jrl.qam2final.Helper.JDBCHelper.connectDB;

/**
 * Implementation of the CountryDAO interface. Provides methods to interact with country data in the database.
 *
 * @author Jeffrey Robert Lynch
 */
public class CountryDAOIMPL implements CountryDAO{

    ObservableList<CountryModel> countries_CouDB = FXCollections.observableArrayList();

    /**
     * Helper method for the CountryDAOIMPL class that displays SQL errors to the user and prints them to the console.
     *
     * @param e SQLException
     */
    private void handleSQLExceptionCouDAO(SQLException e) {
        System.out.println("SQL Error: " + e.getMessage());
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("SQL Error");
        alert.setHeaderText("An error occurred");
        alert.setContentText("SQL Error: " + e.getMessage());
        alert.showAndWait();
    }

    /**
     * Reads all country data from the database, returning an ObservableList of CountryModel objects. Executes a SQL
     * query that selects all countries from the database. Each row from the country table is converted to a
     * CountryModel object and added to the list. If a SQLException occurs, it is handled by a helper method named
     * handleSQLExceptionCouDAO. Used to populate GUI combo boxes.
     *
     * @return countries_CouDB ObservableList of CountryModel objects representing countries in the database.
     */
    @Override
    public ObservableList<CountryModel> readCountryAllCouDAO() {
        try {
            String sql = "SELECT Country_ID, Country FROM countries";
            PreparedStatement readCountryAllStatement = connectDB.prepareStatement(sql);
            ResultSet queryReturn = readCountryAllStatement.executeQuery();
            for (; queryReturn.next();) {
                int countryID_CouDB = queryReturn.getInt("Country_ID");
                String countryN_CouDB = queryReturn.getString("Country");
                CountryModel countryDB = new CountryModel(countryID_CouDB, countryN_CouDB);
                countries_CouDB.add(countryDB);
            }
        } catch (SQLException e) {
            handleSQLExceptionCouDAO(e);
        }
        return countries_CouDB;
    }
}