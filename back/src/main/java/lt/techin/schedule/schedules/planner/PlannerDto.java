package lt.techin.schedule.schedules.planner;

import lt.techin.schedule.classrooms.Classroom;
import lt.techin.schedule.teachers.Teacher;
import lt.techin.schedule.teachers.TeacherEntityDto;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Set;

public class PlannerDto {

    private TeacherEntityDto teacher;

    private Classroom classroom;

    private int assignedHours;

    private LocalDate dateFrom;

    private int startIntEnum;

    private int endIntEnum;

    private Boolean online;

    public PlannerDto() {
    }

    public TeacherEntityDto getTeacher() {
        return teacher;
    }

    public void setTeacher(TeacherEntityDto teacher) {
        this.teacher = teacher;
    }

    public Classroom getClassroom() {
        return classroom;
    }

    public void setClassroom(Classroom classroom) {
        this.classroom = classroom;
    }

    public int getAssignedHours() {
        return assignedHours;
    }

    public void setAssignedHours(int assignedHours) {
        this.assignedHours = assignedHours;
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

    public LocalDate getDateFrom() {
        return dateFrom;
    }

    public void setDateFrom(LocalDate dateFrom) {
        this.dateFrom = dateFrom;
    }

    public Boolean getOnline() {
        return online;
    }

    public void setOnline(Boolean online) {
        this.online = online;
    }
}
