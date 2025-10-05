package jrl.qam2final.Model;

/**
 * Model class representing divisions in the scheduling system. Attributes are based on the database ERD.
 *
 * @author Jeffrey Robert Lynch
 */
public class DivisionModel {
    private int diviID_IN;
    private int counID_IN;
    private String counN_ST;
    private String diviN_ST;

    /**
     * Constructor for DivisionModel objects with attributes matching the ERD.
     *
     * @param diviID_IN   Division ID for division.
     * @param counID_IN   Country ID for division.
     * @param diviN_ST    Division Name for division.
     * @param counN_ST    Country name for division.
     */
    public DivisionModel(int diviID_IN, int counID_IN, String diviN_ST, String counN_ST) {
        this.diviN_ST = diviN_ST;
        this.diviID_IN = diviID_IN;
        this.counN_ST = counN_ST;
        this.counID_IN = counID_IN;
    }

    /**
     * Returns a string that represents the division and its associated attribute values.
     *
     * @return String representation of the division.
     */
    @Override
    public String toString() {
        return diviN_ST;
    }

    /**
     * Getter for diviID_IN.
     *
     * @return diviID_IN Division ID for division.
     */
    public int getDiviID_IN() {
        return diviID_IN;
    }

    /**
     * Setter for diviID_IN.
     *
     * @param diviID_IN Division ID for division.
     */
    public void setDiviID_IN(int diviID_IN) {
        this.diviID_IN = diviID_IN;
    }

    /**
     * Getter for diviN_ST.
     *
     * @return diviN_ST Division Name for division.
     */
    public String getDiviN_ST() {
        return diviN_ST;
    }

    /**
     * Setter for diviN_ST.
     *
     * @param diviN_ST Division Name for division.
     */
    public void setDiviN_ST(String diviN_ST) {
        this.diviN_ST = diviN_ST;
    }

    /**
     * Getter for counID_IN.
     *
     * @return counID_IN Country ID for division.
     */
    public int counID_IN() {
        return counID_IN;
    }

    /**
     * Setter for counID_IN.
     *
     * @param counID_IN Country ID for division.
     */
    public void setCounID_IN(int counID_IN) {
        this.counID_IN = counID_IN;
    }

    /**
     * Getter for counN_ST.
     *
     * @return counN_ST Country Name for division.
     */
    public String getCounN_ST() {
        return counN_ST;
    }

    /**
     * Setter for counN_ST.
     *
     * @param counN_ST Country Name for division.
     */
    public void setCounN_ST(String counN_ST) {
        this.counN_ST = counN_ST;
    }
}