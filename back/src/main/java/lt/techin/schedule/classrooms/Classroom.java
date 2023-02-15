package lt.techin.schedule.classrooms;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lt.techin.schedule.classrooms.buildings.Building;
import lt.techin.schedule.subject.Subject;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
public class Classroom {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @Column(name = "classroomName")
    private String classroomName;
    private String description;
    private BuildingType building;
//     @ManyToOne(fetch = FetchType.EAGER)
//     @JoinColumn(name = "building_id")
//     private Building building;

    @CreatedDate
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createdDate;
    @LastModifiedDate
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private LocalDateTime modifiedDate;

    private boolean active;

    @ManyToMany(mappedBy = "classRooms")
    @JsonIgnore
    private Set<Subject> subjects;

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
        subjects = new HashSet<Subject>();
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

    public Set<Subject> getSubjects() {
        return subjects;
    }

    public void setSubjects(Set<Subject> subjects) {
        this.subjects = subjects;
    }

//    @Override
//    public boolean equals(Object o) {
//        if (this == o) return true;
//        if (o == null || getClass() != o.getClass()) return false;
//        Classroom classroom = (Classroom) o;
//        return active == classroom.active && Objects.equals(id, classroom.id) && Objects.equals(classroomName, classroom.classroomName) && Objects.equals(description, classroom.description) && building == classroom.building && Objects.equals(createdDate, classroom.createdDate) && Objects.equals(modifiedDate, classroom.modifiedDate) && Objects.equals(subjects, classroom.subjects);
//    }
//
//    @Override
//    public int hashCode() {
//        return Objects.hash(id, classroomName, description, building, createdDate, modifiedDate, active);
//    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Classroom classroom = (Classroom) o;
        return active == classroom.active && Objects.equals(id, classroom.id) && Objects.equals(classroomName, classroom.classroomName) && Objects.equals(description, classroom.description) && building == classroom.building && Objects.equals(createdDate, classroom.createdDate) && Objects.equals(modifiedDate, classroom.modifiedDate) && Objects.equals(subjects, classroom.subjects);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, classroomName, description, building, createdDate, modifiedDate, active, subjects);
    }


//    @Override
//    public String toString() {
//        return "Classroom{" +
//                "id=" + id +
//                ", classroomName='" + classroomName + '\'' +
//                ", description='" + description + '\'' +
//                ", building=" + building +
//                ", createdDate=" + createdDate +
//                ", modifiedDate=" + modifiedDate +
//                ", active=" + active +
//                ", subjects=" + subjects +
//                '}';
//    }
}

