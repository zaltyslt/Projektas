package lt.techin.schedule.subject;

import com.fasterxml.jackson.annotation.JsonFormat;
import lt.techin.schedule.classrooms.Classroom;
import lt.techin.schedule.module.Module;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class SubjectDto {

    private String name;

    private String description;

    private Module module;

    private Boolean deleted;

    private Set<Classroom> classRooms;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    private LocalDateTime createdDate;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    private LocalDateTime modifiedDate;

    public SubjectDto() {
        classRooms = new HashSet<>();
        deleted = false;
    }

    public SubjectDto(String name, String description, Module module, Set<Classroom> classRooms, LocalDateTime createdDate, LocalDateTime modifiedDate, Boolean deleted) {
        this.name = name;
        this.description = description;
        this.module = module;
        this.classRooms = classRooms;
        this.createdDate = createdDate;
        this.modifiedDate = modifiedDate;
        this.deleted = deleted;
    }

    public String getName() {
        return name;
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

    public LocalDateTime getModifiedDate() {
        return modifiedDate;
    }

    public void setModifiedDate(LocalDateTime modifiedDate) {
        this.modifiedDate = modifiedDate;
    }

    public Boolean getDeleted() {
        return deleted;
    }

    public void setDeleted(Boolean deleted) {
        this.deleted = deleted;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SubjectDto that = (SubjectDto) o;
        return Objects.equals(name, that.name) && Objects.equals(description, that.description) && Objects.equals(module, that.module) && Objects.equals(deleted, that.deleted) && Objects.equals(classRooms, that.classRooms) && Objects.equals(createdDate, that.createdDate) && Objects.equals(modifiedDate, that.modifiedDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, description, module, deleted, classRooms, createdDate, modifiedDate);
    }

    @Override
    public String toString() {
        return "SubjectDto{" +
                "name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", module=" + module +
                ", deleted=" + deleted +
                ", classRooms=" + classRooms +
                ", createdDate=" + createdDate +
                ", modifiedDate=" + modifiedDate +
                '}';
    }
}
