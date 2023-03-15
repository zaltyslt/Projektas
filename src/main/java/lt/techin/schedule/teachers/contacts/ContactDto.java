package lt.techin.schedule.teachers.contacts;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;

/**
 * A DTO for the {@link lt.techin.schedule.teachers.contacts.Contact} entity
 */
public class ContactDto implements Serializable {
    private Long teacherId;
    private ContactType contactType;
    private String contactValue;

    public ContactDto() {
    }

    public ContactDto(Long teacherId, ContactType contactType, String contactValue) {
        this.teacherId = teacherId;
        this.contactType = contactType;
        this.contactValue = contactValue;
    }

    public Long getTeacherId() {
        return teacherId;
    }

    public void setTeacherId(Long teacherId) {
        this.teacherId = teacherId;
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

}