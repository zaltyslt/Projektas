package lt.techin.schedule.schedules;

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

//    int unassignedHours;

//    private Set<LocalDate> dates;
    private LocalDate dateFrom;

    private int startIntEnum;

    private int endIntEnum;


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

//    public int getUnassignedHours() {
//        return unassignedHours;
//    }
//
//    public void setUnassignedHours(int unassignedHours) {
//        this.unassignedHours = unassignedHours;
//    }
//
//    public Set<LocalDate> getDates() {
//        return dates;
//    }
//
//    public void setDates(Set<LocalDate> dates) {
//        this.dates = dates;
//    }

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
}
