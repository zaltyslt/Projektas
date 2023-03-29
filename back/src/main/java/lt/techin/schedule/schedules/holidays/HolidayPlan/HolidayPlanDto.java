package lt.techin.schedule.schedules.holidays.HolidayPlan;

import lt.techin.schedule.schedules.ScheduleEntityDto;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Objects;

public class HolidayPlanDto {

    private Long id;

    private String name;

    private LocalDate dateFrom;

    private LocalDate dateUntil;

    private LocalDateTime createdDate;

    private LocalDateTime modifiedDate;

    private ScheduleEntityDto schedule;

    public HolidayPlanDto() {
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


}
