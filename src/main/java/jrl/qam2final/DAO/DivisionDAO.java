package jrl.qam2final.DAO;

import javafx.collections.ObservableList;
import jrl.qam2final.Model.DivisionModel;

/**
 * DivisionDAO interface defines methods for interacting with division data in the database.
 *
 * @author Jeffrey Robert Lynch
 */
public interface DivisionDAO {

    /**
     * Reads division data from the database, returning an ObservableList of DivisionModel objects. Executes a SQL
     * query that selects and joins division and country data. Each row from the division table is converted to a
     * DivisionModel object and added to the list. If a SQLException occurs, it is handled by a helper method named
     * handleSQLExceptionDivDAO.
     *
     * @return divisionsByCountry ObservableList of DivisionModel objects.
     */
    ObservableList<DivisionModel> readDivisionFromCountryDivDAO(int countryId);

}