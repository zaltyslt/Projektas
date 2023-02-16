package lt.techin.schedule.classrooms;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDateTime;
import java.util.Objects;

public class ClassroomDto {
    private Long id;
    private String classroomName;
    private String description;
    private BuildingType building;
    private boolean active = true;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    private LocalDateTime createdDate;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    private LocalDateTime modifiedDate;

    public ClassroomDto() {
    }

    public ClassroomDto(Long id, String classroomName, String description,
                        BuildingType building, boolean active, LocalDateTime createdDate, LocalDateTime modifiedDate) {
        this.id = id;
        this.classroomName = classroomName;
        this.description = description;
        this.building = building;
        this.active = active;
        this.createdDate = createdDate;
        this.modifiedDate = modifiedDate;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getClassroomName() {
        return classroomName;
    }

    public void setClassroomName(String classroomName) {
        this.classroomName = classroomName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public BuildingType getBuilding() {
        return building;
    }

    public void setBuilding(BuildingType building) {
        this.building = building;
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
        ClassroomDto that = (ClassroomDto) o;
        return active == that.active && Objects.equals(id, that.id)
                && Objects.equals(classroomName, that.classroomName)
                && Objects.equals(description, that.description)
                && building == that.building;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, classroomName, description, building, active);
    }

    @Override
    public String toString() {
        return "ClassroomDto{" +
                "id=" + id +
                ", classroomName='" + classroomName + '\'' +
                ", description='" + description + '\'' +
                ", building=" + building +
                ", active=" + active +
                '}';
    }
}
