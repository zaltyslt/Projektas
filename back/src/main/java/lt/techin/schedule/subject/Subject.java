package lt.techin.schedule.subject;

import jakarta.persistence.*;
import lt.techin.schedule.module.Module;
import lt.techin.schedule.room.Room;
import org.hibernate.annotations.*;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.Set;

@Entity
@SQLDelete(sql = "UPDATE subject SET deleted = true WHERE id=?")
@FilterDef(name = "deletedSubjectFilter", parameters = @ParamDef(name = "isDeleted", type = org.hibernate.type.descriptor.java.BooleanJavaType.class))
@Filter(name = "deletedSubjectFilter", condition = "deleted = :isDeleted")
public class Subject {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String description;

    @ManyToOne
    @JoinColumn(name = "module_id")
    private Module module;

//    @ManyToMany
//    private Set<Room> rooms;

    @CreatedDate
    private LocalDateTime createdDate;

    @CreatedBy
    private String createdBy;

    @LastModifiedDate
    private LocalDateTime modifiedDate;

    @LastModifiedBy
    private String modifiedBy;

    private Boolean deleted = false;

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

//    public Set<Room> getRooms() {
//        return rooms;
//    }
//
//    public void setRooms(Set<Room> rooms) {
//        this.rooms = rooms;
//    }

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Subject subject = (Subject) o;
        return Objects.equals(id, subject.id) && Objects.equals(name, subject.name) && Objects.equals(description, subject.description) && Objects.equals(module, subject.module) && Objects.equals(createdDate, subject.createdDate) && Objects.equals(createdBy, subject.createdBy) && Objects.equals(modifiedDate, subject.modifiedDate) && Objects.equals(modifiedBy, subject.modifiedBy) && Objects.equals(deleted, subject.deleted);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, description, module, createdDate, createdBy, modifiedDate, modifiedBy, deleted);
    }
}
