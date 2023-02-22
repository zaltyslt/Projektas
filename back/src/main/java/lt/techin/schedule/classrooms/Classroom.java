package lt.techin.schedule.classrooms;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lt.techin.schedule.validators.TextValid;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDateTime;
import java.util.Objects;

@Entity
public class Classroom {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
    @Column(name = "classroomName")
    @TextValid(textMaximumLength = 50)
    private String classroomName;
    @TextValid(textMaximumLength = 1000)
    private String description;
    private BuildingType building;
    @CreatedDate
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createdDate;
    @LastModifiedDate
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime modifiedDate;
    private boolean active = true;

    @PrePersist
    public void prePersist() {
        createdDate = LocalDateTime.now();
        modifiedDate = LocalDateTime.now();
    }

    @PreUpdate
    public void preUpdate() {
        modifiedDate = LocalDateTime.now();
    }

    public Classroom() {
    }

    public Classroom(Long id, String classroomName, String description, boolean active) {
        this.id = id;
        this.classroomName = classroomName;
        this.description = description;
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

    public BuildingType getBuilding() {
        return building;
    }

    public void setBuilding(BuildingType building) {
        this.building = building;
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

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Classroom classroom = (Classroom) o;
        return active == classroom.active && Objects.equals(id, classroom.id)
                && Objects.equals(classroomName, classroom.classroomName)
                && Objects.equals(description, classroom.description)
                && building == classroom.building
                && Objects.equals(createdDate, classroom.createdDate)
                && Objects.equals(modifiedDate, classroom.modifiedDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, classroomName, description, building, createdDate, modifiedDate, active);
    }

    @Override
    public String toString() {
        return "Classroom{" +
                "id=" + id +
                ", classroomName='" + classroomName + '\'' +
                ", description='" + description + '\'' +
                ", building=" + building +
                ", createdDate=" + createdDate +
                ", modifiedDate=" + modifiedDate +
                ", active=" + active +
                '}';
    }
}

