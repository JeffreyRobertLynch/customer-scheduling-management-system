package jrl.qam2final.Model;

/**
 * Model class representing customers in the scheduling system. Attributes are based on the database ERD.
 */
public class CustomerModel {
    private int custID_IN;
    private int diviID_IN;
    private int counID_IN;
    private String custN_ST;
    private String addr_ST;
    private String postC_ST;
    private String phone_ST;
    private String counN_ST;
    private String diviN_ST;

    /**
     * Constructor for CustomerModel objects with attributes matching the ERD.
     *
     * @param custID_IN   Customer ID for customer.
     * @param diviID_IN   Division ID for customer.
     * @param counID_IN   Country ID for customer.
     * @param custN_ST    Customer Name for customer.
     * @param addr_ST     Address for customer.
     * @param postC_ST    Postal Code for customer.
     * @param phone_ST    Phone Number for customer.
     * @param counN_ST    Country Name for customer.
     * @param diviN_ST    Division Name for customer.
     */
    public CustomerModel(int custID_IN, int diviID_IN, int counID_IN, String custN_ST, String addr_ST, String postC_ST, String phone_ST, String counN_ST , String diviN_ST) {
        this.custID_IN = custID_IN;
        this.custN_ST = custN_ST;
        this.diviID_IN = diviID_IN;
        this.counID_IN = counID_IN;
        this.counN_ST  = counN_ST ;
        this.diviN_ST = diviN_ST;
        this.addr_ST = addr_ST;
        this.postC_ST = postC_ST;
        this.phone_ST = phone_ST;
    }

    /**
     * Getter for custID_IN.
     *
     * @return custID_IN Customer ID for customer.
     */
    public int getCustID_IN() {
        return custID_IN;
    }

    /**
     * Setter for custID_IN.
     *
     * @param custID_IN Customer ID for customer.
     */
    public void setCustID_IN(int custID_IN) {
        this.custID_IN = custID_IN;
    }

    /**
     * Getter for counID_IN.
     *
     * @return counID_IN Country ID for customer.
     */
    public int getCounID_IN() {
        return counID_IN;
    }

    /**
     * Setter for counID_IN.
     *
     * @param counID_IN Country ID for customer.
     */
    public void setCounID_IN(int counID_IN) {
        this.counID_IN = counID_IN;
    }

    /**
     * Getter for diviID_IN.
     *
     * @return diviID_IN Division ID for customer.
     */
    public int getDiviID_IN() {
        return diviID_IN;
    }

    /**
     * Setter for diviID_IN.
     *
     * @param diviID_IN Division ID for customer.
     */
    public void setDiviID_IN(int diviID_IN) {
        this.diviID_IN = diviID_IN;
    }

    /**
     * Getter for custN_ST.
     *
     * @return custN_ST Customer Name for customer.
     */
    public String getCustN_ST() {
        return custN_ST;
    }

    /**
     * Setter for custN_ST.
     *
     * @param custN_ST Customer Name for customer.
     */
    public void setCustN_ST(String custN_ST) {
        this.custN_ST = custN_ST;
    }

    /**
     * Getter for phone_ST.
     *
     * @return phone_ST Phone Number for customer.
     */
    public String getPhone_ST() {
        return phone_ST;
    }

    /**
     * Setter for phone_ST.
     *
     * @param phone_ST Phone Number for customer.
     */
    public void setPhone_ST(String phone_ST) {
        this.phone_ST = phone_ST;
    }

    /**
     * Getter for counN_ST.
     *
     * @return counN_ST Country Name for customer.
     */
    public String getCounN_ST() {
        return counN_ST;
    }

    /**
     * Setter for counN_ST.
     *
     * @param counN_ST  Country Name for customer.
     */
    public void setCounN_ST(String counN_ST) {
        this.counN_ST  = counN_ST ;
    }

    /**
     * Getter for addr_ST.
     *
     * @return addr_ST Address for customer.
     */
    public String getAddr_ST() {
        return addr_ST;
    }

    /**
     * Setter for addr_ST.
     *
     * @param addr_ST Address for customer.
     */
    public void setAddr_ST(String addr_ST) {
        this.addr_ST = addr_ST;
    }

    /**
     * Getter for postC_ST.
     *
     * @return postC_ST Postal Code for customer.
     */
    public String getPostC_ST() {
        return postC_ST;
    }

    /**
     * Setter for postC_ST.
     *
     * @param postC_ST Postal Code for customer.
     */
    public void setPostC_ST(String postC_ST) {
        this.postC_ST = postC_ST;
    }

    /**
     * Getter for diviN_ST.
     *
     * @return diviN_ST Division Name for customer.
     */
    public String getDiviN_ST() {
        return diviN_ST;
    }

    /**
     * Setter for diviN_ST.
     *
     * @param diviN_ST Division Name for customer.
     */
    public void setDiviN_ST(String diviN_ST) {
        this.diviN_ST = diviN_ST;
    }

    /**
     * Returns a string that represents the customer and its associated attribute values.
     *
     * @return String representation of the customer
     */
    @Override
    public String toString() {
        return "[" + custID_IN + "] " + custN_ST;
    }
}