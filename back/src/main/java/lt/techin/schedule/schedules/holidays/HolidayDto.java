package lt.techin.schedule.schedules.holidays;

import com.fasterxml.jackson.annotation.JsonFormat;
import lt.techin.schedule.group.Group;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Objects;

public class HolidayDto {

    private Long id;

    private String holidayName;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate dateFrom;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate dateUntil;

//    private Group group;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    private LocalDateTime createdDate;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    private LocalDateTime modifiedDate;

    public HolidayDto() {
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
        HolidayDto that = (HolidayDto) o;
        return Objects.equals(id, that.id)
                && Objects.equals(holidayName, that.holidayName)
                && Objects.equals(dateFrom, that.dateFrom)
                && Objects.equals(dateUntil, that.dateUntil)
//                && Objects.equals(group, that.group)
                && Objects.equals(createdDate, that.createdDate)
                && Objects.equals(modifiedDate, that.modifiedDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, holidayName, dateFrom, dateUntil, createdDate, modifiedDate);
//        return Objects.hash(id, holidayName, dateFrom, dateUntil, group, createdDate, modifiedDate);
    }

    @Override
    public String toString() {
        return "HolidayDto{" +
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
