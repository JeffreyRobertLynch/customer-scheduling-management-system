package jrl.qam2final.Model;

/**
 * Model class representing users in the scheduling system. Attributes are based on the database ERD.
 *
 * @author Jeffrey Robert Lynch
 */
public class UserModel {

    private int userID_IN;
    private String userN_ST;
    private String pass_ST;

    /**
     * Constructor for UserModel objects with attributes matching the ERD.
     *
     * @param userID_IN User ID of user.
     * @param userN_ST  User Name of user.
     * @param pass_ST   Password of user.
     */
    public UserModel(int userID_IN, String userN_ST, String pass_ST) {
        this.pass_ST = pass_ST;
        this.userID_IN = userID_IN;
        this.userN_ST = userN_ST;
    }

    /**
     * Getter for Pass_ST.
     *
     * @return Pass_ST Password of user.
     */
    public String getPass_ST() {
        return pass_ST;
    }

    /**
     * Setter for pass_ST.
     *
     * @param pass_ST Password of user.
     */
    public void setPass_ST(String pass_ST) {
        this.pass_ST = pass_ST;
    }

    /**
     * Getter for userN_ST.
     *
     * @return userN_ST User Name of user.
     */
    public String getUserN_ST() {
        return userN_ST;
    }

    /**
     * Setter for userN_ST.
     *
     * @param userN_ST User Name of user.
     */
    public void setUserN_ST(String userN_ST) {
        this.userN_ST = userN_ST;
    }

    /**
     * Getter for userID_IN.
     *
     * @return userID_IN User ID of user.
     */
    public int getUserID_IN() {
        return userID_IN;
    }

    /**
     * Setter for userID_IN.
     *
     * @param userID_IN User ID of user.
     */
    public void setUserID_IN(int userID_IN) {
        this.userID_IN= userID_IN;
    }

    /**
     * Returns a string that represents the user and its associated attribute values.
     *
     * @return String representation of the user.
     */
    @Override
    public String toString() {
        return "[" + userID_IN + "] " + userN_ST;
    }
}