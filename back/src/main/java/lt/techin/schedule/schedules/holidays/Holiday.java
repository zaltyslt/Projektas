package lt.techin.schedule.schedules.holidays;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lt.techin.schedule.schedules.Schedule;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity
public class Holiday {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String holidayName;

    @ManyToOne
    @JoinColumn(name = "schedule_id")
    private Schedule schedule;

    @Column(name = "date_from", nullable = false)
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate dateFrom;

    @Column(name = "date_until", nullable = false)
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate dateUntil;

    @CreatedDate
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    private LocalDateTime createdDate;
    @LastModifiedDate
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    private LocalDateTime modifiedDate;

    public Schedule getSchedule() {
        return schedule;
    }

    public void setSchedule(Schedule schedule) {
        this.schedule = schedule;
    }

    @PrePersist
    public void prePersist() {
        createdDate = LocalDateTime.now();
        modifiedDate = LocalDateTime.now();
    }

    @PreUpdate
    public void preUpdate() {
        modifiedDate = LocalDateTime.now();
    }

    public Holiday() {
    }

    public Holiday(String holidayName, Schedule schedule, LocalDate dateFrom, LocalDate dateUntil) {
        this.holidayName = holidayName;
        this.schedule = schedule;
        this.dateFrom = dateFrom;
        this.dateUntil = dateUntil;
    }

    public Holiday(Long id, String holidayName, Schedule schedule,
                   LocalDate dateFrom, LocalDate dateUntil, LocalDateTime createdDate,
                   LocalDateTime modifiedDate) {
        this.id = id;
        this.holidayName = holidayName;
        this.schedule = schedule;
        this.dateFrom = dateFrom;
        this.dateUntil = dateUntil;
        this.createdDate = createdDate;
        this.modifiedDate = modifiedDate;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getHolidayName() {
        return holidayName;
    }

    public void setHolidayName(String holidayName) {
        this.holidayName = holidayName;
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

    @Override
    public int hashCode() {
        return Objects.hash(id, holidayName,  dateFrom, dateUntil, createdDate, modifiedDate);
    }

}
