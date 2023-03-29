package lt.techin.schedule.schedules.holidays;

import lt.techin.schedule.config.LithuanianHolidays;
import lt.techin.schedule.schedules.Schedule;
import lt.techin.schedule.schedules.planner.LocalDateComparator;

import java.time.LocalDate;
import java.util.LinkedHashSet;

public class LithuanianHolidaySetup {

    public static LinkedHashSet<Holiday> setupHolidays(Schedule schedule) {
        LinkedHashSet<Holiday> convertedHolidays = new LinkedHashSet<>();
        for (LithuanianHolidayDto holidayDto : LithuanianHolidays.LITHUANIAN_HOLIDAYS) {
            convertedHolidays.add(new Holiday(holidayDto.getName(), schedule, holidayDto.getDate()));
        }
        return convertedHolidays;
    }

    public static LinkedHashSet<Holiday> setupHolidaysInRange(LocalDate fromDate, LocalDate untilDate, Schedule schedule) {
        LinkedHashSet<Holiday> convertedHolidays = new LinkedHashSet<>();
        for (LithuanianHolidayDto holidayDto : LithuanianHolidays.LITHUANIAN_HOLIDAYS) {
            LocalDate holidayDate = holidayDto.getDate();
            if (isInRange(fromDate, untilDate, holidayDate)) {
                convertedHolidays.add(new Holiday(holidayDto.getName(), schedule, holidayDate));
            }
        }
        return convertedHolidays;
    }

    /*
    Checks whether localDate passed is a lithuanian holiday, returns false if it is
    Custom comparator is necessary to avoid comparison of the year - year defined in lithuanian holidays is redundant
    If it's 0, dates are even
    */
    public static boolean isItNotAnLithuanianHolidayDate (LocalDate dateToCheck) {
        return LithuanianHolidays.LITHUANIAN_HOLIDAYS.stream().noneMatch(lithuanianHoliday -> 0 == new LocalDateComparator().compare(lithuanianHoliday.getDate(), dateToCheck));
    }

    /*
    These methods are here to avoid the comparison of the year.
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

    private static boolean isInRange (LocalDate fromDate, LocalDate toDate, LocalDate dateToFind) {
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
