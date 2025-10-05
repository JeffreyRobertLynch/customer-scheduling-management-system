package jrl.qam2final.Model;

/**
 * Model class representing appointments by month and contact in the scheduling system.
 *
 * @author Jeffrey Robert Lynch
 */
public class ReportContactMonthlyModel {

    private int year_IN;
    private String month_ST;
    private String contN_ST;
    private int count_IN;

    /**
     * Constructor for ReportContactMonthlyModel object.
     *
     * @param year_IN   Year for report.
     * @param month_ST  Month for report.
     * @param contN_ST  Contact Name for report.
     * @param count_IN  Count for report.
     */
    public ReportContactMonthlyModel (int year_IN, String month_ST, String contN_ST, int count_IN) {
        this.year_IN = year_IN;
        this.month_ST = month_ST;
        this.contN_ST = contN_ST;
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
     * Getter for year_IN.
     *
     * @return year_IN Year for report.
     */
    public int getYear_IN() {
        return year_IN;
    }

    /**
     * Setter for year_IN.
     *
     * @param year_IN Year for report.
     */
    public void setYear_IN(int year_IN) {
        this.year_IN = year_IN;
    }

    /**
     * Getter for contN_ST.
     *
     * @return contN_ST Contact Name for report.
     */
    public String getContN_ST() {
        return contN_ST;
    }

    /**
     * Setter for contN_ST.
     *
     * @param contN_ST contN_ST Contact Name for report.
     */
    public void setContN_ST(String contN_ST) {
        this.contN_ST = contN_ST;
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
     * Setter for count_IN.
     *
     * @param count_IN Count for report.
     */
    public void setCount_IN(int count_IN) {
        this.count_IN = count_IN;
    }

    /**
     * Returns a string that represents the report and its associated attribute values.
     *
     * @return String representation of the report.
     */
    @Override
    public String toString() {
        return "ContactReport{" +
                "year='" + year_IN + '\'' +
                ", month='" + month_ST + '\'' +
                ", contact='" + contN_ST + '\'' +
                ", count=" + count_IN +
                '}';
    }
}