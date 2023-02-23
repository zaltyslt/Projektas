package lt.techin.schedule.teachers.contacts;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lt.techin.schedule.teachers.Teacher;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "contact")
public class Contact implements Serializable {
//contact table
// PK = {teacher_id {FK} | type}
// id | teacher_id | type | contact_body

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "contact_id", nullable = false) //nurodau stulpo name, kad zinot
    private Long id;

    @Column(name = "type", nullable = false)
    private ContactType contactType;
    @Column(name = "contact_body")
    private String contactValue;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "teacher_id", nullable = true) //nurodau, kad sitas Teacher DB laukas bus foreign key
    @OnDelete(action = OnDeleteAction.CASCADE) //will delete all contacts on teacher deletion
    @JsonBackReference
    private Teacher teacher;


    public static final Logger logger = LoggerFactory.getLogger(Contact.class);
    @PrePersist
    public void prePersist() {
//     logger.error("Just message: contact PrePersist ");
    }


    public Contact() {
//        logger.error("contact 0 constructor ");
    }
    public Contact(Teacher teacher) {
        this.teacher = teacher;
    }

    public Contact(Teacher teacher, ContactType contactType, String contactValue) {
        this.teacher = teacher;
        this.contactType = contactType;
        this.contactValue = contactValue;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ContactType getContactType() {
        return contactType;
    }

    public void setContactType(ContactType contactType) {
        this.contactType = contactType;
    }

    public String getContactValue() {
        return contactValue;
    }

    public void setContactValue(String contactValue) {
        this.contactValue = contactValue;
    }

    public Teacher getTeacher() {
        return teacher;
    }

    public void setTeacher(Teacher teacher) {
        this.teacher = teacher;
    }

    @Override
    public String toString() {
        return "Contact{" +
                "id=" + "id" +
                ", contactType=" + "contactType" +
                ", contactValue='" + "contactValue" + '\'' +
                ", teacher=" + "teacher" +
                '}';
    }
}
