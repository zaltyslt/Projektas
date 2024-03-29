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

    @Column(name = "holiday_date", nullable = false)
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate holidayDate;

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

    public Holiday(String holidayName, Schedule schedule, LocalDate holidayDate) {
        this.holidayName = holidayName;
        this.schedule = schedule;
        this.holidayDate = holidayDate;
    }

    public Holiday(Long id, String holidayName, Schedule schedule,
                   LocalDate holidayDate, LocalDateTime createdDate,
                   LocalDateTime modifiedDate) {
        this.id = id;
        this.holidayName = holidayName;
        this.schedule = schedule;
        this.holidayDate = holidayDate;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Holiday holiday)) return false;
        return Objects.equals(holidayDate, holiday.holidayDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(holidayDate);
    }
}
