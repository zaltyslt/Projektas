package lt.techin.schedule.subject;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lt.techin.schedule.classrooms.Classroom;
import lt.techin.schedule.module.Module;
import lt.techin.schedule.teachers.Teacher;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
public class Subject {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    private String name;

    @NotBlank
    private String description;

    @ManyToOne
    @JoinColumn(name = "module_id")

    @NotNull
    private Module module;

    @ManyToMany
//    @JsonIgnore
    @JoinTable(name = "subject_classrooms", joinColumns = {@JoinColumn(name = "subject_id")}, inverseJoinColumns = {@JoinColumn(name = "classroom_id")})
    @NotEmpty
    private Set<Classroom> classRooms;

    @ManyToMany(mappedBy = "subjects")
//@JsonIgnore
    @JsonBackReference
    private Set<Teacher> teachers;

    @CreatedDate
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    private LocalDateTime createdDate;

    @CreatedBy
    private String createdBy;

    @LastModifiedDate
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    private LocalDateTime modifiedDate;

    @LastModifiedBy
    private String modifiedBy;

    private Boolean deleted;

    @PrePersist
    public void prePersist() {
        createdDate = LocalDateTime.now();
        createdBy = "API app";
        modifiedDate = LocalDateTime.now();
        modifiedBy = "API app";
    }

    @PreUpdate
    public void preUpdate() {
        modifiedDate = LocalDateTime.now();
        modifiedBy = "API app";
    }

    public Subject() {
        classRooms = new HashSet<>();
        deleted = false;
    }

    public Subject(Long id, String name, String description, Module module, Set<Classroom> classRooms, LocalDateTime createdDate, String createdBy, LocalDateTime modifiedDate, String modifiedBy, Boolean deleted) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.module = module;
        this.classRooms = classRooms;
        this.createdDate = createdDate;
        this.createdBy = createdBy;
        this.modifiedDate = modifiedDate;
        this.modifiedBy = modifiedBy;
        this.deleted = deleted;
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

    public Set<Teacher> getTeachers() {
        return teachers;
    }

    public void setTeachers(Set<Teacher> teachers) {
        this.teachers = teachers;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Module getModule() {
        return module;
    }

    public void setModule(Module module) {
        this.module = module;
    }

    public Set<Classroom> getClassRooms() {
        return classRooms;
    }

    public void setClassRooms(Set<Classroom> classRooms) {
        this.classRooms = classRooms;
    }

    public LocalDateTime getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(LocalDateTime createdDate) {
        this.createdDate = createdDate;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public LocalDateTime getModifiedDate() {
        return modifiedDate;
    }

    public void setModifiedDate(LocalDateTime modifiedDate) {
        this.modifiedDate = modifiedDate;
    }

    public String getModifiedBy() {
        return modifiedBy;
    }

    public void setModifiedBy(String modifiedBy) {
        this.modifiedBy = modifiedBy;
    }

    public Boolean getDeleted() {
        return deleted;
    }

    public void setDeleted(Boolean deleted) {
        this.deleted = deleted;
    }

    public void addClassRoom(Classroom classroom) {
        classRooms.add(classroom);
    }

    public void removeClassRoom(Long classroomId) {
        var classRoomToRemove = classRooms.stream().filter(c -> c.getId().equals(classroomId)).findFirst().orElseThrow();
        if (classRoomToRemove != null) {
            classRooms.remove(classRoomToRemove);
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Subject subject = (Subject) o;
        return Objects.equals(id, subject.id) && Objects.equals(name, subject.name) && Objects.equals(description, subject.description) && Objects.equals(module, subject.module) && Objects.equals(classRooms, subject.classRooms) && Objects.equals(teachers, subject.teachers) && Objects.equals(createdDate, subject.createdDate) && Objects.equals(createdBy, subject.createdBy) && Objects.equals(modifiedDate, subject.modifiedDate) && Objects.equals(modifiedBy, subject.modifiedBy) && Objects.equals(deleted, subject.deleted);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, description, module, classRooms, teachers, createdDate, createdBy, modifiedDate, modifiedBy, deleted);
    }

    @Override
    public String toString() {
        return "Subject{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", module=" + module +
                ", classRooms=" + classRooms +
                ", teachers=" + teachers +
                ", createdDate=" + createdDate +
                ", createdBy='" + createdBy + '\'' +
                ", modifiedDate=" + modifiedDate +
                ", modifiedBy='" + modifiedBy + '\'' +
                ", deleted=" + deleted +
                '}';
    }
}
