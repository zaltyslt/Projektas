package lt.techin.schedule.module;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import org.hibernate.annotations.Filter;
import org.hibernate.annotations.FilterDef;
import org.hibernate.annotations.ParamDef;
import org.hibernate.annotations.SQLDelete;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDateTime;
import java.util.Objects;

@Entity
public class Module {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private Long id;
    private String number;
    private String name;


    @CreatedDate
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    private LocalDateTime createdDate;

    @LastModifiedDate
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    private LocalDateTime modifiedDate;

    private boolean deleted = false;

    @PrePersist
    public void prePersist() {
        createdDate = LocalDateTime.now();
        modifiedDate = LocalDateTime.now();
    }

    @PreUpdate
    public void preUpdate() {
        modifiedDate = LocalDateTime.now();
    }

    public Module() {
    }

    public Module(Long id, String number, String name, LocalDateTime createdDate, LocalDateTime modifiedDate, boolean deleted) {
        this.id = id;
        this.number = number;
        this.name = name;
        this.createdDate = createdDate;
        this.modifiedDate = modifiedDate;
        this.deleted = deleted;
    }

    public Module(Long id, String number, String name) {
        this.id = id;
        this.number = number;
        this.name = name;
    }

    public Long getId() { return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Module module = (Module) o;
        return deleted == module.deleted && Objects.equals(id, module.id) && Objects.equals(number, module.number) && Objects.equals(name, module.name) && Objects.equals(createdDate, module.createdDate) && Objects.equals(modifiedDate, module.modifiedDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, number, name, createdDate, modifiedDate, deleted);
    }
}
