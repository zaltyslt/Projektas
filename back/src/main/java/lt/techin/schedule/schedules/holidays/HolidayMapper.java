package lt.techin.schedule.schedules.holidays;

import static lt.techin.schedule.schedules.ScheduleMapper.toScheduleEntity;
import static lt.techin.schedule.schedules.ScheduleMapper.toScheduleFromEntity;

public class HolidayMapper {
    public static Holiday toHoliday(HolidayDto holidayDto) {
        var holiday = new Holiday();
        holiday.setId(holidayDto.getId());
        holiday.setHolidayName(holidayDto.getName());
        holiday.setDateFrom(holidayDto.getDateFrom());
        holiday.setDateUntil(holidayDto.getDateUntil());
        holiday.setCreatedDate(holidayDto.getCreatedDate());
        holiday.setModifiedDate(holidayDto.getModifiedDate());
        holiday.setSchedule(toScheduleFromEntity(holidayDto.getSchedule()));
        return holiday;
    }

    public static HolidayDto toHolidayDto(Holiday holiday) {
        var holidayDto = new HolidayDto();
        holidayDto.setId(holiday.getId());
        holidayDto.setName(holiday.getHolidayName());
        holidayDto.setDateFrom(holiday.getDateFrom());
        holidayDto.setDateUntil(holiday.getDateUntil());
        holidayDto.setCreatedDate(holiday.getCreatedDate());
        holidayDto.setModifiedDate(holiday.getModifiedDate());
        holidayDto.setSchedule(toScheduleEntity(holiday.getSchedule()));
        return holidayDto;
    }
}
