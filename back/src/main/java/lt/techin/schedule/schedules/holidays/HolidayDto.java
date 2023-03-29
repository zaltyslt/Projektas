package lt.techin.schedule.schedules.holidays;

import lt.techin.schedule.schedules.ScheduleEntityDto;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Objects;

public class HolidayDto {

    private Long id;

    private String name;

    private LocalDate holidayDate;

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

    public LocalDate getDate() {
        return holidayDate;
    }

    public void setDate(LocalDate holidayDate) {
        this.holidayDate = holidayDate;
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
        if (!(o instanceof HolidayDto that)) return false;
        return Objects.equals(id, that.id) && Objects.equals(name, that.name) && Objects.equals(holidayDate, that.holidayDate) && Objects.equals(createdDate, that.createdDate) && Objects.equals(modifiedDate, that.modifiedDate) && Objects.equals(schedule, that.schedule);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, holidayDate, createdDate, modifiedDate, schedule);
    }

    @Override
    public String toString() {
        return "HolidayDto{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", holidayDate=" + holidayDate +
                ", createdDate=" + createdDate +
                ", modifiedDate=" + modifiedDate +
                ", schedule=" + schedule +
                '}';
    }
}
