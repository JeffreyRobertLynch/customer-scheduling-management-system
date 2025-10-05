package jrl.qam2final.DAO;

import javafx.collections.ObservableList;
import jrl.qam2final.Model.CountryModel;

/**
 * CountryDAO interface defines methods for interacting with country data in the database.
 *
 * @author Jeffrey Robert Lynch
 */
public interface CountryDAO {

    /**
     * Reads all country data from the database, returning an ObservableList of CountryModel objects. Executes a SQL
     * query that selects all countries from the database. Each row from the country table is converted to a
     * CountryModel object and added to the list. If a SQLException occurs, it is handled by a helper method named
     * handleSQLExceptionCouDAO. Used to populate GUI combo boxes.
     *
     * @return countries_CouDB ObservableList of CountryModel objects representing countries in the database.
     */
    ObservableList<CountryModel> readCountryAllCouDAO();

}