package lt.techin.schedule.classrooms;

import jakarta.persistence.*;
import lt.techin.schedule.classrooms.buildings.Building;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDateTime;
import java.util.Objects;

@Entity
public class Classroom {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
//    @NotNull
//    @Size(min = 1, max = 5)
    @Column(name = "classroomName")
    private String classroomName;
    private String description;
     @ManyToOne(fetch = FetchType.EAGER)
     @JoinColumn(name = "building_id")
     private Building building;

    @CreatedDate
    private LocalDateTime createdDate;
    @LastModifiedDate
    private LocalDateTime modifiedDate;

    private boolean active;
    public Building getBuilding() {
        return building;
    }

    public void setBuilding(Building building) {
        this.building = building;
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

    public Classroom() {
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
        return active == classroom.active && Objects.equals(id, classroom.id) && Objects.equals(classroomName, classroom.classroomName) && Objects.equals(description, classroom.description) && Objects.equals(building, classroom.building) && Objects.equals(createdDate, classroom.createdDate) && Objects.equals(modifiedDate, classroom.modifiedDate);
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

