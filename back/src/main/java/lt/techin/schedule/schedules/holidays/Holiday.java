package lt.techin.schedule.schedules.holidays;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lt.techin.schedule.group.Group;
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

    @Column(name = "date_from", nullable = false)
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate dateFrom;

    @Column(name = "date_until", nullable = false)
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate dateUntil;

//    @ManyToOne
//    @JoinColumn(name = "group_id")
//    private Group group;

    @CreatedDate
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    private LocalDateTime createdDate;
    @LastModifiedDate
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    private LocalDateTime modifiedDate;
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

//    public Group getGroup() {
//        return group;
//    }
//
//    public void setGroup(Group group) {
//        this.group = group;
//    }

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
        Holiday holiday = (Holiday) o;
        return Objects.equals(id, holiday.id)
                && Objects.equals(holidayName, holiday.holidayName)
                && Objects.equals(dateFrom, holiday.dateFrom)
                && Objects.equals(dateUntil, holiday.dateUntil)
//                && Objects.equals(group, holiday.group)
                && Objects.equals(createdDate, holiday.createdDate)
                && Objects.equals(modifiedDate, holiday.modifiedDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, holidayName, dateFrom, dateUntil, createdDate, modifiedDate);
//        return Objects.hash(id, holidayName, dateFrom, dateUntil, group, createdDate, modifiedDate);
    }

    @Override
    public String toString() {
        return "Holiday{" +
                "id=" + id +
                ", holidayName='" + holidayName + '\'' +
                ", dateFrom=" + dateFrom +
                ", dateUntil=" + dateUntil +
//                ", group=" + group +
                ", createdDate=" + createdDate +
                ", modifiedDate=" + modifiedDate +
                '}';
    }
}
