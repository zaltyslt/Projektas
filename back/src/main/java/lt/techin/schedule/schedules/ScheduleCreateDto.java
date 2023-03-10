package lt.techin.schedule.schedules;

import java.time.LocalDate;
import java.util.Objects;

public class ScheduleCreateDto {

    private String schoolYear;

    private String semester;

    private LocalDate dateFrom;

    private LocalDate dateUntil;

    public ScheduleCreateDto() {
    }

    public ScheduleCreateDto(String schoolYear, String semester, LocalDate dateFrom, LocalDate dateUntil) {
        this.schoolYear = schoolYear;
        this.semester = semester;
        this.dateFrom = dateFrom;
        this.dateUntil = dateUntil;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ScheduleCreateDto scheduleCreateDto = (ScheduleCreateDto) o;
        return Objects.equals(schoolYear, scheduleCreateDto.schoolYear) && Objects.equals(semester, scheduleCreateDto.semester) && Objects.equals(dateFrom, scheduleCreateDto.dateFrom) && Objects.equals(dateUntil, scheduleCreateDto.dateUntil);
    }

    @Override
    public int hashCode() {
        return Objects.hash(schoolYear, semester, dateFrom, dateUntil);
    }

    @Override
    public String toString() {
        return "ScheduleCreateDto{" +
                "schoolYear='" + schoolYear + '\'' +
                ", semester='" + semester + '\'' +
                ", dateFrom=" + dateFrom +
                ", dateUntil=" + dateUntil +
                '}';
    }
}
