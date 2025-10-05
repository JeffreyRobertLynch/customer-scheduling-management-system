package jrl.qam2final.Helper;

import javafx.collections.ObservableList;
import javafx.collections.FXCollections;
import java.time.*;

/**
 * Helper class for time conversions.
 *
 * @author Jeffrey Robert Lynch
 */
public class TimeConversionHelper {

    /**
     * Creates a list of business hours adjusted for the user's time zone.
     *
     * @param systemTimeZone Time zone for user's system.
     * @param businessTimeZone Time zone for the business, ET.
     * @param businessOpen Time that the business opens, 8:00am, in ET.
     * @param businessHours Time that the business is open and available for appointments to be scheduled.
     * @return ObservableList that represents business hours.
     */
    public static ObservableList<LocalTime> listBusinessHours(ZoneId systemTimeZone, ZoneId businessTimeZone, LocalTime businessOpen, int businessHours) {
        ObservableList<LocalTime> timeListBusinessHours = FXCollections.observableArrayList();
        LocalDateTime businessStartTime = LocalDate.now().atTime(businessOpen).atZone(businessTimeZone).toLocalDateTime();
        for (int i = 0; i < businessHours; i++) {
            LocalDateTime localTime = businessStartTime.atZone(businessTimeZone).withZoneSameInstant(systemTimeZone).toLocalDateTime();
            timeListBusinessHours.add(localTime.toLocalTime());
            businessStartTime = businessStartTime.plusHours(1);
        }
        return timeListBusinessHours;
    }
}