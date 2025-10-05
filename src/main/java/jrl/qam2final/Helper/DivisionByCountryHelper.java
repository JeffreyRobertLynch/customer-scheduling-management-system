package jrl.qam2final.Helper;

import jrl.qam2final.DAO.DivisionDAO;
import jrl.qam2final.DAO.DivisionDAOIMPL;
import javafx.collections.ObservableList;
import jrl.qam2final.Model.DivisionModel;

/**
 * A helper class that manages divisions filtered by country for populating combo boxes.
 *
 * @author Jeffrey Robert Lynch
 */
public class DivisionByCountryHelper {

    /**
     * Retrieves a list of divisions filtered by country ID.
     *
     * @param counID_IN Country ID
     * @return List of divisions for the country.
     */
    public static ObservableList<DivisionModel> getFilteredDivisions(int counID_IN){
        DivisionDAO newDivisionDAO = new DivisionDAOIMPL();
        return newDivisionDAO.readDivisionFromCountryDivDAO(counID_IN);
    }
}