package jrl.qam2final.Model;

/**
 * Model class representing countries in the scheduling system. Attributes are based on the database ERD.
 */
public class CountryModel {
    private int counID_IN;
    private String counN_ST;

    /**
     * Constructor for CountryModel objects with attributes matching the ERD.
     *
     * @param counID_IN   Country ID for country.
     * @param counN_ST    Country Name for country.
     */
    public CountryModel(int counID_IN, String counN_ST) {
        this.counID_IN = counID_IN;
        this.counN_ST= counN_ST;
    }

    /**
     * Getter for counID_IN.
     *
     * @return counID_IN Country ID for country.
     */
    public int getCounID_IN() {
        return counID_IN;
    }

    /**
     * Setter for counID_IN.
     *
     * @param counID_IN Country ID for country.
     */
    public void setCounID_IN(int counID_IN) {
        this.counID_IN = counID_IN;
    }

    /**
     * Getter for counN_ST.
     *
     * @return counN_ST Country Name for country.
     */
    public String getCounN_ST() {
        return counN_ST;
    }

    /**
     * Setter for counN_ST.
     *
     * @param counN_ST Country Name for country.
     */
    public void setCounN_ST(String counN_ST) {
        this.counN_ST = counN_ST;
    }

    /**
     * Returns a string that represents the country and its associated attribute values.
     *
     * @return String representation of the country.
     */
    @Override
    public String toString() {
        return counN_ST;
    }
}