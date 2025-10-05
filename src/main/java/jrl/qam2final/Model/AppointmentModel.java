package jrl.qam2final.Model;

import java.time.*;
import java.time.format.DateTimeFormatter;

/**
 * Model class representing appointments in the scheduling system. Attributes are based on the database ERD.
 */
public class AppointmentModel {
    private int appID_IN;
    private String titl_ST;
    private String desc_ST;
    private String loca_ST;
    private String type_ST;
    private LocalDateTime staDT_LDT;
    private LocalDateTime endDT_LDT;
    private LocalDate staD_LD;
    private LocalDate endD_LD;
    private LocalTime staT_LT;
    private LocalTime endT_LT;
    private int custID_IN;
    private int userID_IN;
    private int contID_IN;

    /**
     * Constructor for AppointmentModel objects with attributes matching the ERD.
     *
     * @param appID_IN      Appointment ID for appointment.
     * @param titl_ST       Title of appointment.
     * @param desc_ST       Description of appointment.
     * @param loca_ST       Location of appointment.
     * @param type_ST       Type of appointment.
     * @param staDT_LDT     Start day and time of appointment.
     * @param endDT_LDT     End day and time for appointment.
     * @param staD_LD       Start day of appointment.
     * @param endD_LD       End Day of appointment.
     * @param staT_LT       Start time of appointment.
     * @param endT_LT	    End time of appointment.
     * @param custID_IN     Customer ID for appointment.
     * @param userID_IN     User ID for appointment.
     * @param contID_IN     Contact ID for appointment.
     */
    public AppointmentModel(int appID_IN, String titl_ST, String desc_ST, String loca_ST, String type_ST, LocalDateTime staDT_LDT, LocalDateTime endDT_LDT, LocalDate staD_LD, LocalDate endD_LD, LocalTime staT_LT, LocalTime endT_LT, int custID_IN, int userID_IN, int contID_IN) {
        this.staDT_LDT = staDT_LDT;
        this.staD_LD = staD_LD;
        this.staT_LT = staT_LT;
        this.endD_LD = endD_LD;
        this.endT_LT = endT_LT;
        this.endDT_LDT = endDT_LDT;
        this.titl_ST = titl_ST;
        this.desc_ST = desc_ST;
        this.loca_ST = loca_ST;
        this.type_ST = type_ST;
        this.custID_IN = custID_IN;
        this.userID_IN = userID_IN;
        this.contID_IN = contID_IN;
        this.appID_IN = appID_IN;
    }

    /**
     * Returns a string that represents the appointment and its associated attribute values.
     *
     * @return String representation of the appointment.
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Appointment: [").append(appID_IN).append("] | Customer: [").append(custID_IN).append("] ")
                .append("| Contact: [").append(contID_IN).append("] | Type: ").append(type_ST)
                .append(" | Start: ").append(staDT_LDT.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")))
                .append(" | End: ").append(endDT_LDT.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")));
        return sb.toString();
    }

    /**
     * Getter for titl_ST.
     *
     * @return titl_ST Title of appointment.
     */
    public String getTitl_ST() {
        return titl_ST;
    }

    /**
     * Getter for appID_IN.
     *
     * @return appID_IN Appointment ID for appointment.
     */
    public int getAppID_IN() {
        return appID_IN;
    }

    /**
     * Getter for loca_ST.
     *
     * @return loca_ST Location of appointment.
     */
    public String getLoca_ST() {
        return loca_ST;
    }

    /**
     * Getter for type_ST.
     *
     * @return type_ST Type of appointment.
     */
    public String getType_ST() {
        return type_ST;
    }

    /**
     * Getter for desc_ST.
     *
     * @return desc_ST Description of appointment.
     */
    public String getDesc_ST() {
        return desc_ST;
    }

    /**
     * Getter for staDT_LDT.
     *
     * @return staDT_LDT Start day and time of appointment.
     */
    public LocalDateTime getStaDT_LDT() {
        return staDT_LDT;
    }

    /**
     * Getter for endDT_LDT.
     *
     * @return endDT_LDT End day and time for appointment.
     */
    public LocalDateTime getEndDT_LDT() {
        return endDT_LDT;
    }

    /**
     * Getter for staD_LD.
     *
     * @return staD_LD Start day of appointment.
     */
    public LocalDate getStaD_LD() {
        return staD_LD;
    }

    /**
     * Getter for endD_LD.
     *
     * @return endD_LD End Day of appointment.
     */
    public LocalDate getEndD_LD() {
        return endD_LD;
    }

    /**
     * Getter for endT_LT.
     *
     * @return endT_LT End time of appointment.
     */
    public LocalTime getEndT_LT() {
        return endT_LT;
    }

    /**
     * Getter for staT_LT.
     *
     * @return staT_LT Start time of appointment.
     */
    public LocalTime getStaT_LT() {
        return staT_LT;
    }

    /**
     * Getter for custID_IN.
     *
     * @return custID_IN Customer ID for appointment.
     */
    public int getCustID_IN() {
        return custID_IN;
    }

    /**
     * Getter for userID_IN.
     *
     * @return userID_IN User ID for appointment.
     */
    public int getUserID_IN() {
        return userID_IN;
    }

    /**
     * Getter for contID_IN.
     *
     * @return contID_IN Contact ID for appointment.
     */
    public int getContID_IN() {
        return contID_IN;
    }

    /**
     * Setter for appID_IN.
     *
     * @param appID_IN Appointment ID for appointment.
     */
    public void setAppID_IN(int appID_IN) {
        this.appID_IN = appID_IN;
    }

    /**
     * Setter for titl_ST.
     *
     * @param titl_ST Title of appointment.
     */
    public void setTitl_ST (String titl_ST) {
        this.titl_ST = titl_ST;
    }

    /**
     * Setter for loca_ST.
     *
     * @param loca_ST Location of appointment.
     */
    public void setLoca_ST(String loca_ST) {
        this.loca_ST = loca_ST;
    }

    /**
     * Setter for type_ST.
     *
     * @param type_ST Type of appointment.
     */
    public void setType_ST(String type_ST) {
        this.type_ST = type_ST;
    }

    /**
     * Setter for desc_ST.
     *
     * @param desc_ST Description of appointment.
     */
    public void setDesc_ST(String desc_ST) {
        this.desc_ST = desc_ST;
    }

    /**
     * Setter for staDT_LDT.
     *
     * @param staDT_LDT Start day and time of appointment.
     */
    public void setStaDT_LDT(LocalDateTime staDT_LDT) {
        this.staDT_LDT = staDT_LDT;
    }

    /**
     * Setter for endDT_LDT.
     *
     * @param endDT_LDT End day and time for appointment.
     */
    public void setEndDT_LDT(LocalDateTime endDT_LDT) {
        this.endDT_LDT = endDT_LDT;
    }

    /**
     * Setter for staD_LD.
     *
     * @param staD_LD Start day of appointment.
     */
    public void setStaD_LD(LocalDate staD_LD) {
        this.staD_LD = staD_LD;
    }

    /**
     * Setter for endD_LD.
     *
     * @param endD_LD End day of appointment.
     */
    public void setEndD_LD(LocalDate endD_LD) {
        this.endD_LD = endD_LD;
    }

    /**
     * Setter for endT_LT.
     *
     * @param endT_LT End time of appointment.
     */
    public void setEndT_LT(LocalTime endT_LT) {
        this.endT_LT = endT_LT;
    }

    /**
     * Setter for staT_LT.
     *
     * @param staT_LT Start time of appointment.
     */
    public void setStaT_LT(LocalTime staT_LT) {
        this.staT_LT = staT_LT;
    }

    /**
     * Setter for custID_IN .
     *
     * @param custID_IN Customer ID for appointment.
     */
    public void setCustID_IN(int custID_IN) {
        this.custID_IN = custID_IN;
    }

    /**
     * Setter for userID_IN.
     *
     * @param userID_IN User ID for appointment.
     */
    public void setUserID_IN(int userID_IN) {
        this.userID_IN = userID_IN;
    }

    /**
     * Setter for contID_IN.
     *
     * @param contID_IN Contact ID for appointment.
     */
    public void setContID_IN(int contID_IN) {
        this.contID_IN = contID_IN;
    }
}