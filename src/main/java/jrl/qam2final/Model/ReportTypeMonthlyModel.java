package jrl.qam2final.Model;

/**
 * Model class representing appointments by month and type in the scheduling system.
 *
 * @author Jeffrey Robert Lynch
 */
public class ReportTypeMonthlyModel {

    private int count_IN;
    private int year_IN;
    private String month_ST;
    private String type_ST;

    /**
     * Constructor for ReportTypeMonthlyModel object.
     *
     * @param year_IN  Year for report.
     * @param month_ST Month for report.
     * @param type_ST  Type for report.
     * @param count_IN Count for report.
     */
    public ReportTypeMonthlyModel(int year_IN, String month_ST, String type_ST, int count_IN) {
        this.year_IN = year_IN;
        this.count_IN = count_IN;
        this.month_ST= month_ST;
        this.type_ST = type_ST;
    }

    /**
     * Getter for count_IN.
     *
     * @return count_IN Count for report.
     */
    public int getCount_IN() {
        return count_IN;
    }

    /**
     * Setter for count.
     *
     * @param count_IN Count for report.
     */
    public void setCount_IN(int count_IN) {
        this.count_IN = count_IN;
    }

    /**
     * Getter for month_ST.
     *
     * @return month_ST Month for report.
     */
    public String getMonth_ST() {
        return month_ST;
    }

    /**
     * Setter for month_ST.
     *
     * @param month_ST Month for report.
     */
    public void setMonth_ST(String month_ST) {
        this.month_ST = month_ST;
    }

    /**
     * Getter for type_ST.
     *
     * @return type_ST Type for report.
     */
    public String getType_ST() {
        return type_ST;
    }

    /**
     * Setter for type_ST.
     *
     * @param type_ST Type for report.
     */
    public void setType_ST(String type_ST) {
        this.type_ST= type_ST;
    }

    /**
     * Returns a string that represents the report and its associated attribute values.
     *
     * @return String representation of the report.
     */
    @Override
    public String toString() {
        return "Report{" +
                "year=" + year_IN +
                ", month='" + month_ST + '\'' +
                ", type='" + type_ST + '\'' +
                ", count=" + count_IN +
                '}';
    }

    /**
     * Getter for year_IN.
     *
     * @return year_IN Year of appointment.
     */
    public int getYear_IN() {
        return year_IN;
    }

    /**
     * Setter for year_IN.
     *
     * @param year_IN Year of appointment.
     */
    public void setYear_IN(int year_IN) {
        this.year_IN = year_IN;
    }
}