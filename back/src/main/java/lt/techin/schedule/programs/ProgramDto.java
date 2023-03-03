package lt.techin.schedule.programs;

import com.fasterxml.jackson.annotation.JsonFormat;
import lt.techin.schedule.programs.subjectsHours.SubjectHours;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

public class ProgramDto {

    private Long id;

    private String programName;

    private String description;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDateTime createdDate;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    private LocalDateTime modifiedDate;

    private boolean active = true;

    private List<SubjectHours> subjectHoursList;

    public ProgramDto() {
    }

    public ProgramDto(Long id, String programName, String description, LocalDateTime createdDate,
                      LocalDateTime modifiedDate, boolean active) {
        this.id = id;
        this.programName = programName;
        this.description = description;
        this.createdDate = createdDate;
        this.modifiedDate = modifiedDate;
        this.active = active;
    }

    public ProgramDto(Long id, String programName, String description, LocalDateTime createdDate,
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
        ProgramDto that = (ProgramDto) o;
        return active == that.active
                && Objects.equals(id, that.id)
                && Objects.equals(programName, that.programName)
                && Objects.equals(description, that.description)
                && Objects.equals(createdDate, that.createdDate)
                && Objects.equals(modifiedDate, that.modifiedDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, programName, description, createdDate, modifiedDate, active);
    }

    @Override
    public String toString() {
        return "ProgramDto{" +
                "id=" + id +
                ", programName='" + programName + '\'' +
                ", description='" + description + '\'' +
                ", createdDate=" + createdDate +
                ", modifiedDate=" + modifiedDate +
                ", active=" + active +
                '}';
    }
}
