package lt.techin.schedule.schedules;

import lt.techin.schedule.group.Group;
import lt.techin.schedule.schedules.planner.WorkDay;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.LinkedHashSet;
import java.util.Set;

public class ScheduleDto {
    private Long id;
    private Group group;
    private String schoolYear;
    private String semester;

    private LocalDate dateFrom;

    private LocalDate dateUntil;
    private boolean active = true;
    private LocalDateTime createdDate;
    private LocalDateTime modifiedDate;

    private Set<WorkDay> workingDays;

    public ScheduleDto() {
        workingDays = new LinkedHashSet<>();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Group getGroup() {
        return group;
    }

    public void setGroup(Group group) {
        this.group = group;
    }

    public String getSchoolYear() {
        return schoolYear;
    }

    public void setSchoolYear(String schoolYear) {
        this.schoolYear = schoolYear;
    }

    public String getSemester() {
        return semester;
    }

    public void setSemester(String semester) {
        this.semester = semester;
    }

    public LocalDate getDateFrom() {
        return dateFrom;
    }

    public void setDateFrom(LocalDate dateFrom) {
        this.dateFrom = dateFrom;
    }

    public LocalDate getDateUntil() {
        return dateUntil;
    }

    public void setDateUntil(LocalDate dateUntil) {
        this.dateUntil = dateUntil;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public LocalDateTime getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(LocalDateTime createdDate) {
        this.createdDate = createdDate;
    }

    public LocalDateTime getModifiedDate() {
        return modifiedDate;
    }

    public void setModifiedDate(LocalDateTime modifiedDate) {
        this.modifiedDate = modifiedDate;
    }

    public Set<WorkDay> getWorkingDays() {
        return workingDays;
    }

    public void setWorkingDays(Set<WorkDay> workingDays) {
        this.workingDays = workingDays;
    }
}
