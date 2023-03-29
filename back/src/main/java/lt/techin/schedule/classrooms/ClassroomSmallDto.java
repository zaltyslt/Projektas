package lt.techin.schedule.classrooms;

import java.util.Objects;

public class ClassroomSmallDto {
    private Long id;
    private String classroomName;
    public ClassroomSmallDto() {
    }

    public ClassroomSmallDto(Long id, String classroomName) {
        this.id = id;
        this.classroomName = classroomName;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ClassroomSmallDto that = (ClassroomSmallDto) o;
        return Objects.equals(id, that.id) && Objects.equals(classroomName, that.classroomName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, classroomName);
    }

    @Override
    public String toString() {
        return "ClassroomSmallDto{" +
                "id=" + id +
                ", classroomName='" + classroomName + '\'' +
                '}';
    }
}
