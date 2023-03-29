package lt.techin.schedule.teachers.contacts;


import lt.techin.schedule.teachers.Teacher;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ContactMapper {
    @Deprecated
    public static ContactDto contactToDto(Contact contact) {
        var dto = new ContactDto();
        dto.setContactType(contact.getContactType());
        dto.setContactValue(contact.getContactValue());
        return dto;
    }

    @Deprecated
    public static List<ContactDto> contactsToDto(List<Contact> contacts) {
        var dtos = contacts.stream()
                .map(c -> contactToDto(c))
                .toList();
        return dtos;
    }

    @Deprecated
    public static Contact contactFromDto(ContactDto contactDto) {
        Contact contact = new Contact();
        Teacher teacher = new Teacher();
        contact.setContactType(contactDto.getContactType());
        contact.setContactValue(contactDto.getContactValue());
        contact.setTeacher(teacher);
        return contact;
    }

    @Deprecated
    public static List<Contact> contactFromDto(List<ContactDto> contactDto) {
        return contactDto.stream()
                .map(c -> contactFromDto(c))
                .toList();
    }

    public static ContactDto2 contactToDto2(List<Contact> contacts) {
        ContactDto2 contactDto2 = new ContactDto2();
        for (Contact contact : contacts) {
            switch (contact.getContactType()) {
                case PHONE_NUMBER -> contactDto2.setPhoneNumber(contact.getContactValue());
                case DIRECT_EMAIL -> contactDto2.setDirectEmail(contact.getContactValue());
                case TEAMS_EMAIL -> contactDto2.setTeamsEmail(contact.getContactValue());
                case TEAMS_NAME -> contactDto2.setTeamsName(contact.getContactValue());
            }
        }
        return contactDto2;
    }

    public static List<Contact> contactFromDto2(ContactDto2 contactDto2) {
        Teacher dummyTeacher = new Teacher();
        dummyTeacher.setId(contactDto2.getTeacherId() != null ? contactDto2.getTeacherId() : null);
        Contact phoneNumber = new Contact(dummyTeacher, ContactType.PHONE_NUMBER,
                contactDto2.getPhoneNumber() != null ? contactDto2.getPhoneNumber() : null);
        Contact directEmail = new Contact(dummyTeacher, ContactType.DIRECT_EMAIL,
                contactDto2.getDirectEmail() != null ? contactDto2.getDirectEmail() : null);
        Contact teamsEmail = new Contact(dummyTeacher, ContactType.TEAMS_EMAIL,
                contactDto2.getTeamsEmail() != null ? contactDto2.getTeamsEmail() : null);
        Contact teamsName = new Contact(dummyTeacher, ContactType.TEAMS_NAME,
                contactDto2.getTeamsName() != null ? contactDto2.getTeamsName() : null);
        return new ArrayList<>(Arrays.asList(phoneNumber, directEmail, teamsEmail, teamsName));
    }
}
