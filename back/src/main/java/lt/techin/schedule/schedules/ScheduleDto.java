package lt.techin.schedule.schedules;

import lt.techin.schedule.group.Group;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.Date;
import java.util.Objects;

public class ScheduleDto {
    private Long id;
    private Group groups;
    private String schoolYear;
    private String semester;
    private LocalDate dateFrom;
    private LocalDate dateUntil;
    private boolean active = true;
    private LocalDateTime createdDate;
    private LocalDateTime modifiedDate;

    public ScheduleDto() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Group getGroups() {
        return groups;
    }

    public void setGroups(Group groups) {
        this.groups = groups;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ScheduleDto that = (ScheduleDto) o;
        return active == that.active && Objects.equals(id, that.id)
                && Objects.equals(groups, that.groups)
                && Objects.equals(schoolYear, that.schoolYear) && Objects.equals(semester, that.semester) && Objects.equals(dateFrom, that.dateFrom) && Objects.equals(dateUntil, that.dateUntil) && Objects.equals(createdDate, that.createdDate) && Objects.equals(modifiedDate, that.modifiedDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id,
                groups,
                schoolYear, semester, dateFrom, dateUntil, active, createdDate, modifiedDate);
    }

    @Override
    public String toString() {
        return "ScheduleDto{" +
                "id=" + id +
                ", groups=" + groups +
                ", schoolYear='" + schoolYear + '\'' +
                ", semester='" + semester + '\'' +
                ", dateFrom=" + dateFrom +
                ", dateUntil=" + dateUntil +
                ", active=" + active +
                ", createdDate=" + createdDate +
                ", modifiedDate=" + modifiedDate +
                '}';
    }
}
