package lt.techin.schedule.programs.subjectsHours;

import com.fasterxml.jackson.annotation.JsonFormat;
import lt.techin.schedule.subject.Subject;

import java.time.LocalDateTime;

public class SubjectHoursDto {
    private Long id;
    private Subject subjectn;
    private String subjectName;

    private Boolean deleted;

    private Long subject;
    private int hours;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    private LocalDateTime createdDate;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    private LocalDateTime modifiedDate;

    public Boolean getDeleted() {
        return deleted;
    }

    public void setDeleted(Boolean deleted) {
        this.deleted = deleted;
    }

    public Subject getSubjectn() {
        return subjectn;
    }

    public void setSubjectn(Subject subjectn) {
        this.subjectn = subjectn;
    }

    public String getSubjectName() {
        return subjectName;
    }

    public void setSubjectName(String subjectName) {
        this.subjectName = subjectName;
    }

    public SubjectHoursDto() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }


    public Long getSubject() {
        return subject;
    }

    public void setSubject(Long subject) {
        this.subject = subject;
    }

    public int getHours() {
        return hours;
    }

    public void setHours(int hours) {
        this.hours = hours;
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

//    @Override
//    public boolean equals(Object o) {
//        if (this == o) return true;
//        if (o == null || getClass() != o.getClass()) return false;
//        SubjectHoursDto that = (SubjectHoursDto) o;
//        return hours == that.hours
//                && Objects.equals(id, that.id)
//                && Objects.equals(subject, that.subject)
//                && Objects.equals(createdDate, that.createdDate)
//                && Objects.equals(modifiedDate, that.modifiedDate);
//    }

//    @Override
//    public int hashCode() {
//        return Objects.hash(id, subject, hours, createdDate, modifiedDate);
//    }

    @Override
    public String toString() {
        return "SubjectHoursDto{" +
                "id=" + id +
//                ", subject=" + subject +
                ", hours=" + hours +
                ", createdDate=" + createdDate +
                ", modifiedDate=" + modifiedDate +
                '}';
    }
}
