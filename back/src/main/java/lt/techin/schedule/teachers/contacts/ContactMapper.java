package lt.techin.schedule.teachers.contacts;


import lt.techin.schedule.teachers.Teacher;

import java.util.List;
import java.util.stream.Collectors;

public class ContactMapper {

    public static ContactDto contactToDto(Contact contact){
        var dto = new ContactDto();
        dto.setContactType(contact.getContactType());
        dto.setContactValue(contact.getContactValue());
        return dto;
    }
    public static List<ContactDto> contactsToDto(List<Contact> contacts){
        var dtos = contacts.stream()
                .map(c->contactToDto(c))
                .toList();
                return dtos;
    }

    public static Contact contactFromDto(ContactDto contactDto){

        Contact contact = new Contact();
        Teacher teacher = new Teacher();

        contact.setContactType(contactDto.getContactType());
        contact.setContactValue(contactDto.getContactValue());
        contact.setTeacher(teacher);

        return contact;
    }

    public static List<Contact> contactFromDto(List<ContactDto> contactDto){
        return contactDto.stream()
                .map(c->contactFromDto(c))
                .toList();
    }
}
