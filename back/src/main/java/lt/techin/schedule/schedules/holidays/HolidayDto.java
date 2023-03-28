package lt.techin.schedule.schedules.holidays;

import lt.techin.schedule.schedules.ScheduleEntityDto;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Objects;

public class HolidayDto {

    private Long id;

    private String name;

    private LocalDate dateFrom;

    private LocalDate dateUntil;

    private LocalDateTime createdDate;

    private LocalDateTime modifiedDate;

    private ScheduleEntityDto schedule;

    public HolidayDto() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public ScheduleEntityDto getSchedule() {
        return schedule;
    }

    public void setSchedule(ScheduleEntityDto schedule) {
        this.schedule = schedule;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        HolidayDto that = (HolidayDto) o;
        return Objects.equals(id, that.id)
                && Objects.equals(name, that.name)
                && Objects.equals(dateFrom, that.dateFrom)
                && Objects.equals(dateUntil, that.dateUntil)
                && Objects.equals(createdDate, that.createdDate)
                && Objects.equals(modifiedDate, that.modifiedDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, dateFrom, dateUntil, createdDate, modifiedDate);
    }

    @Override
    public String toString() {
        return "HolidayDto{" +
                "id=" + id +
                ", holidayName='" + name + '\'' +
                ", dateFrom=" + dateFrom +
                ", dateUntil=" + dateUntil +
                ", createdDate=" + createdDate +
                ", modifiedDate=" + modifiedDate +
                '}';
    }
}
