package lt.techin.schedule.schedules;

import lt.techin.schedule.classrooms.Classroom;
import lt.techin.schedule.shift.Shift;
import lt.techin.schedule.teachers.Teacher;

import java.time.LocalDate;
import java.util.Set;

public class PlannerDto {

    private Teacher teacher;

    private Classroom classroom;

    int assignedHours;

    int unassignedHours;

    private Set<LocalDate> dates;

    private Shift shift;

    public PlannerDto() {
    }

    public PlannerDto(Teacher teacher, Classroom classroom, int assignedHours, int unassignedHours, Set<LocalDate> dates, Shift shift) {
        this.teacher = teacher;
        this.classroom = classroom;
        this.assignedHours = assignedHours;
        this.unassignedHours = unassignedHours;
        this.dates = dates;
        this.shift = shift;
    }

    public Teacher getTeacher() {
        return teacher;
    }

    public void setTeacher(Teacher teacher) {
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

    public int getUnassignedHours() {
        return unassignedHours;
    }

    public void setUnassignedHours(int unassignedHours) {
        this.unassignedHours = unassignedHours;
    }

    public Set<LocalDate> getDates() {
        return dates;
    }

    public void setDates(Set<LocalDate> dates) {
        this.dates = dates;
    }

    public Shift getShift() {
        return shift;
    }

    public void setShift(Shift shift) {
        this.shift = shift;
    }
}
