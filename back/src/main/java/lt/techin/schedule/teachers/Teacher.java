package lt.techin.schedule.teachers;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lt.techin.schedule.shift.Shift;
import lt.techin.schedule.subject.Subject;
import lt.techin.schedule.teachers.contacts.Contact;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "teacher")
public class Teacher implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "teacher_id", nullable = false)
    private Long id;
    @JsonIgnore
    @OneToMany(mappedBy = "teacher", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Contact> contacts;
    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    @JoinTable(
            name = "teacher_subject",
            joinColumns = @JoinColumn(name = "teacher_id", referencedColumnName = "teacher_id"),
            inverseJoinColumns = @JoinColumn(name = "subject_id", referencedColumnName = "id"))
    @JsonManagedReference
    private Set<Subject> subjects;
    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "shift_id")
    @JsonManagedReference
    private Shift shift;
    @NotBlank
    @Size(min = 1, max = 30)
    private String fName = "";
    @NotBlank
    private String lName = "";
    private Boolean isActive;
    @CreatedDate
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm")
    private LocalDateTime createdDateTime;
    @LastModifiedDate
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm")
    private LocalDateTime modifiedDateAndTime;
    private Integer workHoursPerWeek;
    private Integer hashCode;

    @PrePersist
    public void prePersist() {
        createdDateTime = LocalDateTime.now();
        modifiedDateAndTime = LocalDateTime.now();
        hashCode = hashCode();
    }

    @PreUpdate
    public void preUpdate() {
        modifiedDateAndTime = LocalDateTime.now();
        hashCode = hashCode();
    }

    public Teacher() {
    }

    public Teacher(Long id, String fName, String lName, Boolean isActive) {
        this.id = id;
        this.fName = fName;
        this.lName = lName;
        this.isActive = isActive;
    }

    public Teacher(Long id, List<Contact> contacts,
                   Set<Subject> subjects, Shift shift, String fName,
                   String lName, Boolean isActive, LocalDateTime createdDateTime,
                   LocalDateTime modifiedDateAndTime, Integer workHoursPerWeek) {
        this.id = id;
        this.contacts = contacts;
        this.subjects = subjects;
        this.shift = shift;
        this.fName = fName;
        this.lName = lName;
        this.isActive = isActive;
        this.createdDateTime = createdDateTime;
        this.modifiedDateAndTime = modifiedDateAndTime;
        this.workHoursPerWeek = workHoursPerWeek;
    }

    public Integer getHashCode() {
        return hashCode;
    }

    public void setHashCode(Integer hashCode) {
        this.hashCode = hashCode;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<Contact> getContacts() {
        return contacts;
    }

    public void setContacts(List<Contact> contacts) {
        this.contacts = contacts;
    }

    public Set<Subject> getSubjects() {
        return subjects;
    }

    public void setSubjects(Set<Subject> subjects) {
        this.subjects = subjects;
    }

    public Shift getShift() {
        return shift;
    }

    public void setShift(Shift shift) {
        this.shift = shift;
    }

    public String getfName() {
        return fName;
    }

    public void setfName(String fName) {
        this.fName = fName;
    }

    public String getlName() {
        return lName;
    }

    public void setlName(String lName) {
        this.lName = lName;
    }

    public Boolean getActive() {
        return isActive;
    }

    public void setActive(Boolean active) {
        isActive = active;
    }

    public LocalDateTime getCreatedDateTime() {
        return createdDateTime;
    }

    public void setCreatedDateTime(LocalDateTime createdDateTime) {
        this.createdDateTime = createdDateTime;
    }

    public LocalDateTime getModifiedDateAndTime() {
        return modifiedDateAndTime;
    }

    public void setModifiedDateAndTime(LocalDateTime modifiedDateAndTime) {
        this.modifiedDateAndTime = modifiedDateAndTime;
    }

    public Integer getWorkHoursPerWeek() {
        return workHoursPerWeek;
    }

    public void setWorkHoursPerWeek(Integer workHoursPerWeek) {
        this.workHoursPerWeek = workHoursPerWeek;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Teacher teacher = (Teacher) o;
        return id.equals(teacher.id)
                && Objects.equals(contacts, teacher.contacts)
                && Objects.equals(subjects, teacher.subjects)
                && Objects.equals(shift, teacher.shift)
                && Objects.equals(fName, teacher.fName)
                && Objects.equals(lName, teacher.lName)
                && Objects.equals(isActive, teacher.isActive)
                && Objects.equals(workHoursPerWeek, teacher.workHoursPerWeek);
    }

    @Override
    public int hashCode() {
        return Objects.hash(fName.toLowerCase(), lName.toLowerCase());
    }

    @Override
    public String toString() {
        return "Teacher{" +
                "id=" + id +
                ", contacts=" + contacts +
                ", subjects=" + subjects +
                ", shift=" + shift +
                ", fName='" + fName + '\'' +
                ", lName='" + lName + '\'' +
                ", isActive=" + isActive +
                ", createdDateTime=" + createdDateTime +
                ", modifiedDateAndTime=" + modifiedDateAndTime +
                ", workHoursPerWeek=" + workHoursPerWeek +
                ", hashCode=" + hashCode +
                '}';
    }
}
