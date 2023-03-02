package lt.techin.schedule.programs.subjectsHours;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lt.techin.schedule.subject.Subject;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDateTime;

@Entity
public class SubjectHours {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
//    @ManyToOne
//    @JoinColumn(name = "subject_id")
//    private Subject subjectn;
    private String subjectName;
    private Long subject;
    private int hours;
    @CreatedDate
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    private LocalDateTime createdDate;
    @LastModifiedDate
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    private LocalDateTime modifiedDate;

    @PrePersist
    public void prePersist() {
        createdDate = LocalDateTime.now();
        modifiedDate = LocalDateTime.now();
    }
    @PreUpdate
    public void preUpdate() {
        modifiedDate = LocalDateTime.now();
    }





    public String getSubjectName() {
        return subjectName;
    }

    public void setSubjectName(String subjectName) {
        this.subjectName = subjectName;
    }

    public SubjectHours() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public Long getSubject() {
        return subject;
    }

    public void setSubject(Long subject) {
        this.subject = subject;
    }
    //    @Override
//    public boolean equals(Object o) {
//        if (this == o) return true;
//        if (o == null || getClass() != o.getClass()) return false;
//        SubjectHours that = (SubjectHours) o;
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
        return "SubjectHours{" +
                "id=" + id +
//                ", subject=" + subject +
                ", hours=" + hours +
                ", createdDate=" + createdDate +
                ", modifiedDate=" + modifiedDate +
                '}';
    }
}
