package lt.techin.schedule.schedules.planner;

import static lt.techin.schedule.classrooms.ClassroomMapper.toClassroomFromSmallDto;
import static lt.techin.schedule.classrooms.ClassroomMapper.toClassroomSmallDto;
import static lt.techin.schedule.subject.SubjectMapper.toSubjectFromSmallDto;
import static lt.techin.schedule.subject.SubjectMapper.toSubjectSmallDto;
import static lt.techin.schedule.teachers.TeacherMapper.toTeacherEntityDto;
import static lt.techin.schedule.teachers.TeacherMapper.toTeacherFromEntityDto;

public class WorkDayMapper {

    public static WorkDay toWorkDay(WorkDayDto workDayDto) {
        var workDay = new WorkDay();

        workDay.setId(workDayDto.getId());
        workDay.setDate(workDayDto.getDate());
        workDay.setOnline(workDayDto.getOnline());
        workDay.setLessonStart(workDayDto.getLessonStart());
        workDay.setLessonEnd(workDayDto.getLessonEnd());
        workDay.setTeacher(toTeacherFromEntityDto(workDayDto.getTeacher()));
        workDay.setClassroom(toClassroomFromSmallDto(workDayDto.getClassroom()));
        workDay.setSubject(toSubjectFromSmallDto(workDayDto.getSubject()));

        return workDay;
    }

    public static WorkDayDto toWorkDayDto(WorkDay workDay) {
        var workDayDto = new WorkDayDto();

        workDayDto.setId(workDay.getId());
        workDayDto.setDate(workDay.getDate());
        workDayDto.setOnline(workDay.getOnline());
        workDayDto.setLessonStart(workDay.getLessonStart());
        workDayDto.setLessonEnd(workDay.getLessonEnd());
        workDayDto.setTeacher(toTeacherEntityDto(workDay.getTeacher()));
        workDayDto.setClassroom(toClassroomSmallDto(workDay.getClassroom()));
        workDayDto.setSubject(toSubjectSmallDto(workDay.getSubject()));
        workDayDto.setHasClassroomConflict(workDay.isHasClassroomConflict());
        workDayDto.setHasTeacherConflict(workDay.isHasTeacherConflict());
        workDayDto.setScheduleIdWithClassroomNameConflict(workDay.getScheduleIdWithClassroomNameConflict());
        workDayDto.setScheduleIdWithTeacherNameConflict(workDay.getScheduleIdWithTeacherNameConflict());

        return workDayDto;
    }
}
