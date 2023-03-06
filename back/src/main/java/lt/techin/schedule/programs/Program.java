package lt.techin.schedule.programs;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lt.techin.schedule.programs.subjectsHours.SubjectHours;
import lt.techin.schedule.group.Group;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Set;

@Entity
public
class Program {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @Column(name = "programName")
    @Size(min = 1, max = 200)
    private String programName;

    @Size(min = 1, max = 2000)
    private String description;

    @CreatedDate
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    private LocalDateTime createdDate;

    @LastModifiedDate
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    private LocalDateTime modifiedDate;

    @OneToMany
    @JoinColumn(name = "program_group")
    private Set<Group> groups;

    private boolean active = true;

    @OneToMany
    private List<SubjectHours> subjectHoursList;

    @PrePersist
    public void prePersist() {
        createdDate = LocalDateTime.now();
        modifiedDate = LocalDateTime.now();
    }

    @PreUpdate
    public void preUpdate() {
        modifiedDate = LocalDateTime.now();
    }

    public Program() {
    }

    public Program(Long id,
                   String programName,
                   String description,
                   Set<Group> groups,
                   boolean active,
                   List<SubjectHours> subjectHoursList) {
        this.id = id;
        this.programName = programName;
        this.description = description;
        this.groups = groups;
        this.active = active;
        this.subjectHoursList = subjectHoursList;
    }

    public Program(Long id, String programName, String description, LocalDateTime createdDate,
                   LocalDateTime modifiedDate, boolean active) {
        this.id = id;
        this.programName = programName;
        this.description = description;
        this.createdDate = createdDate;
        this.modifiedDate = modifiedDate;
        this.active = active;
    }

    public Program(Long id, String programName, String description, LocalDateTime createdDate,
                   LocalDateTime modifiedDate, boolean active, List<SubjectHours> subjectHoursList) {
        this.id = id;
        this.programName = programName;
        this.description = description;
        this.createdDate = createdDate;
        this.modifiedDate = modifiedDate;
        this.active = active;
        this.subjectHoursList = subjectHoursList;
    }

    public List<SubjectHours> getSubjectHoursList() {
        return subjectHoursList;
    }

    public void setSubjectHoursList(List<SubjectHours> subjectHoursList) {
        this.subjectHoursList = subjectHoursList;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getProgramName() {
        return programName;
    }

    public void setProgramName(String programName) {
        this.programName = programName;
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
        Program program = (Program) o;
        return active == program.active
                && Objects.equals(id, program.id)
                && Objects.equals(programName, program.programName)
                && Objects.equals(description, program.description)
                && Objects.equals(createdDate, program.createdDate)
                && Objects.equals(modifiedDate, program.modifiedDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, programName, description, createdDate, modifiedDate, active);
    }

    @Override
    public String toString() {
        return "Program{" +
                "id=" + id +
                ", programName='" + programName + '\'' +
                ", description='" + description + '\'' +
                ", createdDate=" + createdDate +
                ", modifiedDate=" + modifiedDate +
                ", active=" + active +
                '}';
    }
}
