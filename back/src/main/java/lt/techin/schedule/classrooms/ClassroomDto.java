package lt.techin.schedule.classrooms;

import java.util.Objects;

public class ClassroomDto {
    private Long id;
    private String classroomName;
    private String description;
    private boolean active;

    public ClassroomDto(){}

    public ClassroomDto(Long id, String classroomNumber, String description) {
        this.id = id;
        this.classroomName = classroomNumber;
        this.description = description;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ClassroomDto that = (ClassroomDto) o;
        return classroomName == that.classroomName
                && Objects.equals(id, that.id)
                && Objects.equals(description, that.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, classroomName, description);
    }

    @Override
    public String toString() {
        return "ClassroomDto{" +
                "id=" + id +
                ", classroomNumber=" + classroomName +
                ", description='" + description + '\'' +
                '}';
    }
}
