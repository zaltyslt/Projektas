package lt.techin.schedule.schedules.planner;

import lt.techin.schedule.classrooms.ClassroomSmallDto;
import lt.techin.schedule.schedules.ScheduleEntityDto;
import lt.techin.schedule.subject.SubjectSmallDto;
import lt.techin.schedule.teachers.TeacherEntityDto;

import java.time.LocalDate;
import java.util.Map;
import java.util.Objects;

public class WorkDayDto {
    private Long id;
    private LocalDate date;
    private String lessonStart;
    private String lessonEnd;
    private int startIntEnum;
    private int endIntEnum;
    private Boolean online;
    private TeacherEntityDto teacher;
    private SubjectSmallDto subject;
    private ClassroomSmallDto classroom;
    private ScheduleEntityDto schedule;
    private Map<Long, String> scheduleIdWithClassroomNameConflict;
    private Map<Long, String> scheduleIdWithTeacherNameConflict;
    private boolean hasTeacherConflict;
    private boolean hasClassroomConflict;

    public Map<Long, String> getScheduleIdWithClassroomNameConflict() {
        return scheduleIdWithClassroomNameConflict;
    }

    public void setScheduleIdWithClassroomNameConflict(Map<Long, String> scheduleIdWithClassroomNameConflict) {
        this.scheduleIdWithClassroomNameConflict = scheduleIdWithClassroomNameConflict;
    }

    public Map<Long, String> getScheduleIdWithTeacherNameConflict() {
        return scheduleIdWithTeacherNameConflict;
    }

    public void setScheduleIdWithTeacherNameConflict(Map<Long, String> scheduleIdWithTeacherNameConflict) {
        this.scheduleIdWithTeacherNameConflict = scheduleIdWithTeacherNameConflict;
    }

    public boolean isHasTeacherConflict() {
        return hasTeacherConflict;
    }

    public void setHasTeacherConflict(boolean hasTeacherConflict) {
        this.hasTeacherConflict = hasTeacherConflict;
    }

    public boolean isHasClassroomConflict() {
        return hasClassroomConflict;
    }

    public void setHasClassroomConflict(boolean hasClassroomConflict) {
        this.hasClassroomConflict = hasClassroomConflict;
    }

    public WorkDayDto() {
    }

    public WorkDayDto(Long id, LocalDate date,
                      String lessonStart, String lessonEnd,
                      Boolean online, TeacherEntityDto teacher,
                      SubjectSmallDto subject, ClassroomSmallDto classroom,
                      ScheduleEntityDto schedule) {
        this.id = id;
        this.date = date;
        this.lessonStart = lessonStart;
        this.lessonEnd = lessonEnd;
        this.online = online;
        this.teacher = teacher;
        this.subject = subject;
        this.classroom = classroom;
        this.schedule = schedule;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public String getLessonStart() {
        return lessonStart;
    }

    public void setLessonStart(String lessonStart) {
        this.lessonStart = lessonStart;
    }

    public String getLessonEnd() {
        return lessonEnd;
    }

    public void setLessonEnd(String lessonEnd) {
        this.lessonEnd = lessonEnd;
    }

    public Boolean getOnline() {
        return online;
    }

    public void setOnline(Boolean online) {
        this.online = online;
    }

    public TeacherEntityDto getTeacher() {
        return teacher;
    }

    public void setTeacher(TeacherEntityDto teacher) {
        this.teacher = teacher;
    }

    public SubjectSmallDto getSubject() {
        return subject;
    }

    public void setSubject(SubjectSmallDto subject) {
        this.subject = subject;
    }

    public ClassroomSmallDto getClassroom() {
        return classroom;
    }

    public void setClassroom(ClassroomSmallDto classroom) {
        this.classroom = classroom;
    }

    public ScheduleEntityDto getSchedule() {
        return schedule;
    }

    public void setSchedule(ScheduleEntityDto schedule) {
        this.schedule = schedule;
    }

    public int getStartIntEnum() {
        return startIntEnum;
    }

    public void setStartIntEnum(int startIntEnum) {
        this.startIntEnum = startIntEnum;
    }

    public int getEndIntEnum() {
        return endIntEnum;
    }

    public void setEndIntEnum(int endIntEnum) {
        this.endIntEnum = endIntEnum;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        WorkDayDto that = (WorkDayDto) o;
        return Objects.equals(id, that.id)
                && Objects.equals(date, that.date)
                && Objects.equals(lessonStart, that.lessonStart)
                && Objects.equals(lessonEnd, that.lessonEnd)
                && Objects.equals(online, that.online)
                && Objects.equals(teacher, that.teacher)
                && Objects.equals(subject, that.subject)
                && Objects.equals(classroom, that.classroom)
                && Objects.equals(schedule, that.schedule);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, date, lessonStart, lessonEnd, online, teacher, subject, classroom, schedule);
    }

    @Override
    public String toString() {
        return "WorkDayDto{" +
                "id=" + id +
                ", date=" + date +
                ", lessonStart='" + lessonStart + '\'' +
                ", lessonEnd='" + lessonEnd + '\'' +
                ", online=" + online +
                ", teacher=" + teacher +
                ", subject=" + subject +
                ", classroom=" + classroom +
                ", schedule=" + schedule +
                '}';
    }
}