package lt.techin.schedule.schedules.holidays;

import lt.techin.schedule.group.Group;
import lt.techin.schedule.group.GroupEntityDto;

public class HolidayMapper {
    public static Holiday toHoliday(HolidayDto holidayDto) {
        var holiday = new Holiday();
        holiday.setId(holidayDto.getId());
        holiday.setHolidayName(holidayDto.getHolidayName());
        holiday.setDateFrom(holidayDto.getDateFrom());
        holiday.setDateUntil(holidayDto.getDateUntil());
        holiday.setGroup(toGroupEntityDto(holidayDto.getGroup()));
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
        holidayDto.setGroup(toGroupEntityDto2(holiday.getGroup()));
        holidayDto.setCreatedDate(holiday.getCreatedDate());
        holidayDto.setModifiedDate(holiday.getModifiedDate());
        return holidayDto;
    }

    public static Group toGroupEntityDto(GroupEntityDto groupEntityDto) {
        var group = new Group();
        if (groupEntityDto != null) {
            group.setId(groupEntityDto.getId());
            group.setName(groupEntityDto.getName());
            return group;
        } else {
            return null;
        }
    }

    public static GroupEntityDto toGroupEntityDto2(Group group) {
        var groupEntityDto = new GroupEntityDto();
        if(group != null) {
            groupEntityDto.setId(group.getId());
            groupEntityDto.setName(group.getName());
            return groupEntityDto;
        } else {
            return null;
        }
    }
}
