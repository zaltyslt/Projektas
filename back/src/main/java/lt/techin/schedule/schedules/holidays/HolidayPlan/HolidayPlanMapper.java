package lt.techin.schedule.schedules.holidays.HolidayPlan;

import static lt.techin.schedule.schedules.ScheduleMapper.toScheduleEntity;
import static lt.techin.schedule.schedules.ScheduleMapper.toScheduleFromEntity;

public class HolidayPlanMapper {

    public static HolidayPlan toHolidayPlan(HolidayPlanDto holidayPlanDto) {
        HolidayPlan holidayPlan = new HolidayPlan();
        holidayPlan.setId(holidayPlanDto.getId());
        holidayPlan.setHolidayName(holidayPlanDto.getName());
        holidayPlan.setDateFrom(holidayPlanDto.getDateFrom());
        holidayPlan.setDateUntil(holidayPlanDto.getDateUntil());
        holidayPlan.setCreatedDate(holidayPlanDto.getCreatedDate());
        holidayPlan.setModifiedDate(holidayPlanDto.getModifiedDate());
        holidayPlan.setSchedule(toScheduleFromEntity(holidayPlanDto.getSchedule()));
        return holidayPlan;
    }

    public static HolidayPlanDto toHolidayPlanDto(HolidayPlan holidayPlan) {
        HolidayPlanDto holidayPlanDto = new HolidayPlanDto();
        holidayPlanDto.setId(holidayPlan.getId());
        holidayPlanDto.setName(holidayPlan.getHolidayName());
        holidayPlanDto.setDateFrom(holidayPlan.getDateFrom());
        holidayPlanDto.setDateUntil(holidayPlan.getDateUntil());
        holidayPlanDto.setCreatedDate(holidayPlan.getCreatedDate());
        holidayPlanDto.setModifiedDate(holidayPlan.getModifiedDate());
        holidayPlanDto.setSchedule(toScheduleEntity(holidayPlan.getSchedule()));
        return holidayPlanDto;
    }
}
