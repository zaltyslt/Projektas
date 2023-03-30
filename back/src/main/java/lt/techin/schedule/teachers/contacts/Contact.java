package lt.techin.schedule.teachers.contacts;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lt.techin.schedule.teachers.Teacher;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Serializable;
import java.util.Objects;

@Entity
@Table(name = "contact")
public class Contact implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "contact_id", nullable = false)
    private Long id;
    @Column(name = "type", nullable = false)
    private ContactType contactType;
    @Column(name = "contact_body")
    private String contactValue;
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "teacher_id", nullable = true)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonBackReference
    private Teacher teacher;

    public static final Logger logger = LoggerFactory.getLogger(Contact.class);

    @PrePersist
    public void prePersist() {
    }

    public Contact() {
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
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Contact contact = (Contact) o;
        return Objects.equals(id, contact.id)
                && contactType == contact.contactType
                && Objects.equals(contactValue, contact.contactValue)
                && Objects.equals(teacher, contact.teacher);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, contactType, contactValue, teacher);
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
