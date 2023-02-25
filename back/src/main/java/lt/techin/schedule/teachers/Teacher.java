package lt.techin.schedule.teachers;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lt.techin.schedule.shift.Shift;
import lt.techin.schedule.subject.Subject;
import lt.techin.schedule.teachers.contacts.Contact;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
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
    @Column(name = "teacher_id", nullable = false) //nurodau stulpo name, kad galeciau panaudoti kaip ID kontaktui
    private Long id;

    @JsonIgnore
    @OneToMany(mappedBy = "teacher", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Contact> contacts;
    //@JsonIgnore is used to ignore the logical property used in serialization and deserialization.
//    @Transient @JsonIgnore
//    private Set<Subject> subjectsList;

    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    @JoinTable(
            name = "teacher_subject",
            joinColumns = @JoinColumn(name = "teacher_id", referencedColumnName = "teacher_id"),
            inverseJoinColumns = @JoinColumn(name = "subject_id", referencedColumnName = "id"))
    @JsonManagedReference
    private Set<Subject> subjects;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    @JoinTable(
            name = "teacher_shifts",
            joinColumns = @JoinColumn(name = "teacher_id", referencedColumnName = "teacher_id"),
            inverseJoinColumns = @JoinColumn(name = "shift_id", referencedColumnName = "id"))
    @JsonManagedReference
    private Shift shift;

    @NotBlank
    @Size(min = 3, max = 30)
    private String fName="";
    @NotNull
    private String lName="";
    @NotNull
    private String nickName;
    private Boolean isActive;
    @CreatedDate
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm")
    private LocalDateTime createdDateTime;
    @LastModifiedDate
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm")
    private LocalDateTime modifiedDateAndTime;
    private Integer workHoursPerWeek;
    private Integer hashCode;

    //FIXME kai bus kaip paimti Shiftus


    @PrePersist
    public void prePersist() {
        createdDateTime = LocalDateTime.now();
        modifiedDateAndTime = LocalDateTime.now();
        this.hashCode = hashCode();
    }

    @PostLoad
    public void postLoad() {
//        if (savedSubjectsList != null && !savedSubjectsList.isEmpty()) {
//            subjects = TeacherService.loadSubjectsList(savedSubjectsList);
//        }
    }

    @PreUpdate
    public void preUpdate() {
        this.modifiedDateAndTime = LocalDateTime.now();
        this.hashCode = hashCode();
    }

    //*************************


    public Teacher() {
    }


    public Teacher(Long id, String fName, String lName) {
        this.id = id;
        this.fName = fName;
        this.lName = lName;
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

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
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
        return id.equals(teacher.id) && Objects.equals(contacts, teacher.contacts) && Objects.equals(subjects, teacher.subjects) && Objects.equals(shift, teacher.shift) && Objects.equals(fName, teacher.fName) && Objects.equals(lName, teacher.lName) && Objects.equals(nickName, teacher.nickName) && Objects.equals(isActive, teacher.isActive) && Objects.equals(workHoursPerWeek, teacher.workHoursPerWeek);
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
                ", nickName='" + nickName + '\'' +
                ", isActive=" + isActive +
                ", createdDateTime=" + createdDateTime +
                ", modifiedDateAndTime=" + modifiedDateAndTime +
                ", workHoursPerWeek=" + workHoursPerWeek +
                '}';
    }
}
