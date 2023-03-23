package lt.techin.schedule.schedules.holidays;

public class HolidayMapper {
    public static Holiday toHoliday(HolidayDto holidayDto) {
        var holiday = new Holiday();
        holiday.setId(holidayDto.getId());
        holiday.setHolidayName(holidayDto.getHolidayName());
        holiday.setDateFrom(holidayDto.getDateFrom());
        holiday.setDateUntil(holidayDto.getDateUntil());
//        holiday.setGroup(holidayDto.getGroup());
        holiday.setCreatedDate(holidayDto.getCreatedDate());
        holiday.setModifiedDate(holidayDto.getModifiedDate());
        return holiday;
    }

    public static HolidayDto toHolidayDto(Holiday holiday) {
        var holidayDto = new HolidayDto();
        holidayDto.setId(holiday.getId());
        holidayDto.setHolidayName(holiday.getHolidayName());
        holidayDto.setDateFrom(holiday.getDateFrom());
        holidayDto.setDateUntil(holiday.getDateUntil());
//        holidayDto.setGroup(holiday.getGroup());
        holidayDto.setCreatedDate(holiday.getCreatedDate());
        holidayDto.setModifiedDate(holiday.getModifiedDate());
        return holidayDto;
    }
}
