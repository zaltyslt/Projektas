package lt.techin.schedule.shift;

import com.fasterxml.jackson.annotation.JsonFormat;
import lt.techin.schedule.teachers.Teacher;
import lt.techin.schedule.validators.TextValid;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.Set;

public class ShiftDto {

    private Long id;

    @TextValid(textMaximumLength = 50)
    private String name;

    private String shiftStartingTime;

    private String shiftEndingTime;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    private LocalDateTime createdDate;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    private LocalDateTime modifiedDate;

    private Integer startIntEnum;
    private Integer endIntEnum;
    private Boolean isActive;
    private Set<Teacher> teachers;

    public ShiftDto() {
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

    public String getShiftStartingTime() {
        return shiftStartingTime;
    }

    public void setShiftStartingTime(String shiftStartingTime) {
        this.shiftStartingTime = shiftStartingTime;
    }

    public String getShiftEndingTime() {
        return shiftEndingTime;
    }

    public void setShiftEndingTime(String shiftEndingTime) {
        this.shiftEndingTime = shiftEndingTime;
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

    public Integer getStartIntEnum() {
        return startIntEnum;
    }

    public void setStartIntEnum(Integer startIntEnum) {
        this.startIntEnum = startIntEnum;
    }

    public Integer getEndIntEnum() {
        return endIntEnum;
    }

    public void setEndIntEnum(Integer endIntEnum) {
        this.endIntEnum = endIntEnum;
    }

    public Boolean getIsActive() {
        return isActive;
    }

    public void setActive(Boolean active) {
        isActive = active;
    }

    public Set<Teacher> getTeachers() {
        return teachers;
}

    public void setTeachers(Set<Teacher> teachers) {
        this.teachers = teachers;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ShiftDto that = (ShiftDto) o;
        return Objects.equals(id, that.id) && Objects.equals(name, that.name) && Objects.equals(shiftStartingTime, that.shiftStartingTime) && Objects.equals(shiftEndingTime, that.shiftEndingTime) && Objects.equals(createdDate, that.createdDate) && Objects.equals(modifiedDate, that.modifiedDate) && Objects.equals(startIntEnum, that.startIntEnum) && Objects.equals(endIntEnum, that.endIntEnum) && Objects.equals(isActive, that.isActive) && Objects.equals(teachers, that.teachers);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, shiftStartingTime, shiftEndingTime, createdDate, modifiedDate, startIntEnum, endIntEnum, isActive, teachers);
    }

    @Override
    public String toString() {
        return "ShiftDtoNew{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", shiftStartingTime='" + shiftStartingTime + '\'' +
                ", shiftEndingTime='" + shiftEndingTime + '\'' +
                ", createdDate=" + createdDate +
                ", modifiedDate=" + modifiedDate +
                ", startIntEnum=" + startIntEnum +
                ", endIntEnum=" + endIntEnum +
                ", isActive=" + isActive +
                ", teachers=" + teachers +
                '}';
    }
}
