package jrl.qam2final.Model;

/**
 * Model class representing appointments by month and customer in the scheduling system.
 *
 * @author Jeffrey Robert Lynch
 */
public class ReportCustomerMonthlyModel {

    private int year_IN;
    private String month_ST;
    private int custID_IN;
    private String custN_ST;
    private int count_IN;

    /**
     * Constructor for ReportCustomerMonthlyModel object.
     *
     * @param year_IN   Year for report.
     * @param month_ST  Month for report.
     * @param custID_IN Customer ID for report.
     * @param custN_ST  Customer Name for report.
     * @param count_IN  Count for report.
     */
    public ReportCustomerMonthlyModel(int year_IN, String month_ST, int custID_IN, String custN_ST, int count_IN) {
        this.year_IN = year_IN;
        this.month_ST = month_ST;
        this.custID_IN = custID_IN;
        this.custN_ST = custN_ST;
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
     * Getter for custID_IN.
     *
     * @return custID_IN Customer ID for report.
     */
    public int getCustID_IN() {
        return custID_IN;
    }

    /**
     * Setter for custID_IN.
     *
     * @param custID_IN Customer ID for report.
     */
    public void setCustID_IN(int custID_IN) {
        this.custID_IN = custID_IN;
    }

    /**
     * Getter for year_IN.
     *
     * @return getYear Year for report.
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
     * Getter for custN_ST.
     *
     * @return custN_ST Customer name for report.
     */
    public String getCustN_ST() {
        return custN_ST;
    }

    /**
     * Setter for custN_ST.

     * @param custN_ST Customer name for report.
     */
    public void setCustN_ST(String custN_ST) {
        this.custN_ST = custN_ST;
    }

    /**
     * Returns a string that represents the report and its associated attribute values.
     *
     * @return String representation of the report.
     */
    @Override
    public String toString() {
        return "CustomerReport{" +
                "year=" + year_IN +
                ", month='" + month_ST + '\'' +
                ", customerId=" + custID_IN +
                ", customerName='" + custN_ST + '\'' +
                ", count=" + count_IN +
                '}';
    }

    /**
     * Getter for count_IN.
     *
     * @return count_IN count for appointments.
     */
    public int getCount_IN() {
        return count_IN;
    }

    /**
     * Setter for count_IN.
     *
     * @param count_IN Count for appointments.
     */
    public void setCount_IN(int count_IN) {
        this.count_IN = count_IN;
    }
}