package lt.techin.schedule.schedules;

public class ScheduleMapper {
    public static ScheduleDto toScheduleDto(Schedule schedule) {
        var scheduleDto = new ScheduleDto();
        scheduleDto.setId(schedule.getId());
        scheduleDto.setGroups(schedule.getGroups());
        scheduleDto.setSchoolYear(schedule.getSchoolYear());
        scheduleDto.setSemester(schedule.getSemester());
        scheduleDto.setDateFrom(schedule.getDateFrom());
        scheduleDto.setDateUntil(schedule.getDateUntil());
        scheduleDto.setActive(schedule.isActive());
        scheduleDto.setCreatedDate(schedule.getCreatedDate());
        scheduleDto.setModifiedDate(schedule.getModifiedDate());
        scheduleDto.setWorkingDays(schedule.getWorkingDays());
        scheduleDto.setSubjectIdWithUnassignedTime(schedule.getSubjectIdWithUnassignedTime());
        return scheduleDto;
    }

    public static Schedule toSchedule(ScheduleDto scheduleDto) {
        var schedule = new Schedule();
        schedule.setId(scheduleDto.getId());
        schedule.setGroups(scheduleDto.getGroups());
        schedule.setSchoolYear(scheduleDto.getSchoolYear());
        schedule.setSemester(scheduleDto.getSemester());
        schedule.setDateFrom(scheduleDto.getDateFrom());
        schedule.setDateUntil(scheduleDto.getDateUntil());
        schedule.setActive(scheduleDto.isActive());
        schedule.setCreatedDate(scheduleDto.getCreatedDate());
        schedule.setModifiedDate(scheduleDto.getModifiedDate());
        schedule.setWorkingDays(scheduleDto.getWorkingDays());
        schedule.setSubjectIdWithUnassignedTime(scheduleDto.getSubjectIdWithUnassignedTime());
        return schedule;
    }

    public static Schedule toScheduleFromCreateDto(ScheduleCreateDto scheduleCreateDto) {
        var schedule = new Schedule();
        schedule.setSemester(scheduleCreateDto.getSemester());
        schedule.setSchoolYear(scheduleCreateDto.getSchoolYear());
        schedule.setDateFrom(scheduleCreateDto.getDateFrom());
        schedule.setDateUntil(scheduleCreateDto.getDateUntil());
        return schedule;
    }

    public static ScheduleCreateDto toScheduleCreateDto(Schedule schedule) {
        var scheduleCreateDto = new ScheduleCreateDto();
        scheduleCreateDto.setSemester(schedule.getSemester());
        scheduleCreateDto.setSchoolYear(schedule.getSchoolYear());
        scheduleCreateDto.setDateFrom(schedule.getDateFrom());
        scheduleCreateDto.setDateUntil(schedule.getDateUntil());
        return scheduleCreateDto;
    }

    public static ScheduleEntityDto toScheduleEntity(Schedule schedule) {
        ScheduleEntityDto scheduleEntityDto = new ScheduleEntityDto();

        scheduleEntityDto.setId(schedule.getId());
        return scheduleEntityDto;
    }

    public static Schedule toScheduleFromEntity(ScheduleEntityDto scheduleEntityDto) {
        Schedule schedule = new Schedule();

        schedule.setId(scheduleEntityDto.getId());
        return schedule;
    }
}
