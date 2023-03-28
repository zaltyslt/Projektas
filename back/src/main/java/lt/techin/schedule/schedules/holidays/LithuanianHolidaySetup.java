package lt.techin.schedule.schedules.holidays;

import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import lt.techin.schedule.config.LithuanianHolidays;
import lt.techin.schedule.schedules.Schedule;

import java.time.LocalDate;
import java.util.LinkedHashSet;

public class LithuanianHolidaySetup {

    public static LinkedHashSet<Holiday> SetupHolidays(Schedule schedule) {
        LinkedHashSet<Holiday> convertedHolidays = new LinkedHashSet<>();
        for (LithuanianHolidayDto holidayDto : LithuanianHolidays.LITHUANIAN_HOLIDAYS) {
            convertedHolidays.add(new Holiday(holidayDto.getName(), schedule, holidayDto.getDate()));
        }
        return convertedHolidays;
    }

    public static LinkedHashSet<Holiday> SetupHolidaysInRange(LocalDate fromDate, LocalDate untilDate, Schedule schedule) {
        LinkedHashSet<Holiday> convertedHolidays = new LinkedHashSet<>();
        for (LithuanianHolidayDto holidayDto : LithuanianHolidays.LITHUANIAN_HOLIDAYS) {
            LocalDate holidayDate = holidayDto.getDate();
            if (IsInRange(fromDate, untilDate, holidayDate)) {
                convertedHolidays.add(new Holiday(holidayDto.getName(), schedule, holidayDate));
            }
        }
        return convertedHolidays;
    }
    /*
    These methods are here to avoid comparison of the year.
    Year defined in config directory might be faulty.
    */
    private static boolean IsAfterOrEqual (LocalDate localDate, LocalDate isAfterDate) {
        if (localDate.getMonthValue() < isAfterDate.getMonthValue()) {
            return true;
        }
        if (localDate.getMonthValue() > isAfterDate.getMonthValue()) {
            return false;
        }
        return localDate.getDayOfMonth() <= localDate.getDayOfMonth();
   }

    private static boolean IsBeforeOrEqual (LocalDate localDate, LocalDate isBeforeDate) {
       if (localDate.getMonthValue() > isBeforeDate.getMonthValue()) {
           return true;
       }
       if (localDate.getMonthValue() < isBeforeDate.getMonthValue()) {
           return false;
       }
       return localDate.getDayOfMonth() >= localDate.getDayOfMonth();
   }

    private static boolean IsInRange (LocalDate fromDate, LocalDate toDate, LocalDate dateToFind) {
        int month = dateToFind.getMonthValue();
        int day = dateToFind.getDayOfMonth();

        if (fromDate.getMonthValue() < month && toDate.getMonthValue() > month) {
            return true;
        }
        if (fromDate.getMonthValue() > month || toDate.getMonthValue() < month) {
            return false;
        }
        return fromDate.getDayOfMonth() <= day && toDate.getDayOfMonth() >= day;
   }
}
