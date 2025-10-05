package jrl.qam2final.Model;

/**
 * Model class representing contacts in the scheduling system. Attributes are based on the database ERD.
 */
public class ContactModel {
    private int contID_IN;
    private String contN_ST;
    private String email_ST;

    /**
     * Constructor for ContactModel objects with attributes matching the ERD.
     *
     * @param contID_IN   Contact ID for contact.
     * @param contN_ST    Contact Name for contact.
     * @param email_ST    Email for contact.
     */
    public ContactModel(int contID_IN, String contN_ST, String email_ST) {
        this.contID_IN = contID_IN;
        this.contN_ST = contN_ST;
        this.email_ST = email_ST;
    }

    /**
     * Getter for contID_IN.
     *
     * @return contID_IN Contact ID for contact.
     */
    public int getContID_IN() {
        return contID_IN;
    }

    /**
     * Setter for contID_IN.
     *
     * @param contID_IN Contact ID for contact.
     */
    public void setContID_IN(int contID_IN) {
        this.contID_IN = contID_IN;
    }

    /**
     * Getter for email_ST.
     *
     * @return email_ST Email for contact.
     */
    public String getEmail_ST() {
        return email_ST;
    }

    /**
     * Setter for email_ST.
     *
     * @param email_ST Email for contact.
     */
    public void setEmail_ST(String email_ST) {
        this.email_ST = email_ST;
    }

    /**
     * Getter for contN_ST.
     *
     * @return contN_ST Contact Name for contact.
     */
    public String getContN_ST() {
        return contN_ST;
    }

    /**
     * Setter for contN_ST.
     *
     * @param contN_ST Contact Name for contact.
     */
    public void setContN_ST(String contN_ST) {
        this.contN_ST = contN_ST;
    }

    /**
     * Returns a string that represents the contact and its associated attribute values.
     *
     * @return String representation of the contact
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("[").append(contID_IN).append("] ").append(contN_ST).append(" (").append(email_ST).append(")");
        return sb.toString();
    }
}