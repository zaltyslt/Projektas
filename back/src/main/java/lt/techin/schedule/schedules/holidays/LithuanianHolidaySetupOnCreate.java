package lt.techin.schedule.schedules.holidays;

import lt.techin.schedule.schedules.Schedule;

import java.util.LinkedHashSet;

public class LithuanianHolidaySetupOnCreate {

    public static LinkedHashSet<Holiday> SetupHolidays(LinkedHashSet<LithuanianHolidayDto> holidaysDto, Schedule schedule) {
        LinkedHashSet<Holiday> convertedHolidays = new LinkedHashSet<>();
        for (LithuanianHolidayDto holidayDto : holidaysDto) {
            convertedHolidays.add(new Holiday(holidayDto.getName(), schedule, holidayDto.getDate(), holidayDto.getDate()));
        }
        return convertedHolidays;
    }

}
