package lt.techin.schedule.teachers;

import lt.techin.schedule.shift.ShiftMapper;
import lt.techin.schedule.teachers.contacts.Contact;
import lt.techin.schedule.teachers.contacts.ContactDto;
import lt.techin.schedule.teachers.contacts.ContactDto2;
import lt.techin.schedule.teachers.contacts.ContactMapper;
import lt.techin.schedule.teachers.helpers.TeacherShiftMapper;
import lt.techin.schedule.teachers.helpers.TeacherSubjectMapper;
import lt.techin.schedule.teachers.helpers.TeacherSubjectsDto;

import java.util.*;
import java.util.stream.Collectors;

public class TeacherMapper {
    public static TeacherDto teacherToDto(Teacher teacher) {
        TeacherDto dto = new TeacherDto();

        dto.setId(teacher.getId());
        dto.setfName(teacher.getfName() != null ? teacher.getfName() : "");
        dto.setlName(teacher.getlName() != null ? teacher.getlName() : "");
        dto.setNickName(teacher.getNickName() != null ? teacher.getNickName() : "");

        dto.setSubjectsDtoList(teacher.getSubjects() != null
                ? TeacherSubjectMapper.subjectsToDtos(teacher.getSubjects())
                : new HashSet<TeacherSubjectsDto>());
        dto.setWorkHoursPerWeek(teacher.getWorkHoursPerWeek() != null ? teacher.getWorkHoursPerWeek() : 0);
        dto.setTeacherShiftDto(teacher.getShift() != null ? TeacherShiftMapper.shiftToDto(teacher.getShift()) : null);
//        dto.setSelectedShift(ShiftMapper.shiftToDto(teacher.getShift()));

        dto.setActive(teacher.getActive() != null ? teacher.getActive() : true);
//        dto.setContacts(teacher.getContacts() != null ? contactsForDto(teacher.getContacts()) : new ArrayList<ContactDto>());
        dto.setContacts(teacher.getContacts() != null
                ? ContactMapper.contactToDto2(teacher.getContacts()) : new ContactDto2());

        dto.setDateCreated(teacher.getCreatedDateTime());
        dto.setDateMotified(teacher.getModifiedDateAndTime());

        return dto;

    }

    public static TeacherDto teacherToDto(Optional<Teacher> teacher) {
//
        return teacher.isPresent()
                ? teacherToDto(teacher.get())
                : new TeacherDto();
    }

    public static Set<TeacherDto> teacherToDto(List<Teacher> teacher) {

        return !teacher.isEmpty()
                ? teacher.stream().map(t -> teacherToDto(t)).collect(Collectors.toSet())
                : new HashSet<TeacherDto>();
    }

    //to DTO Helpers
//    private static List<ContactDto> contactsForDto(List<Contact> contacts) {
//
//        return contacts.stream()
//                .map(c -> ContactMapper.contactToDto(c))
//                .toList();
//    }

//    private static List<Contact> contactsForTeacher(List<ContactDto> contacts) {
//        return contacts.stream()
//                .map(c -> ContactMapper.contactFromDto(c))
//                .toList();
//    }
    // DTO to teacher

    public static Teacher teacherFromDto(TeacherDto dto) {
        Teacher teacher = new Teacher();
        teacher.setId(dto.getId() != null ? dto.getId() : null);
        teacher.setfName(dto.getfName() != null ? dto.getfName().trim() : "_empty");
        teacher.setlName(dto.getlName() != null ? dto.getlName().trim() : "_empty");
        teacher.setNickName(dto.getNickName() != null ? dto.getNickName().trim() : "_empty");
        teacher.setSubjects(dto.getSubjectsDtoList() != null
                ? TeacherSubjectMapper.subjectsFromDtos(dto.getSubjectsDtoList())
                : null);
        teacher.setWorkHoursPerWeek(dto.getWorkHoursPerWeek());
//        teacher.setShift(ShiftMapper.shiftFromDto(dto.getShiftDtoNew()));
//        teacher.setShift(ShiftMapper.shiftFromDto(dto.getSelectedShift()));
//        teacher.setShift(TeacherShiftMapper.shiftFromDto(dto.get .getSelectedShift()));
        teacher.setActive(dto.getActive());
        teacher.setContacts(dto.getContacts() != null ? ContactMapper.contactFromDto2(dto.getContacts()) : new ArrayList<Contact>());
        //implement message

        return teacher;
    }

    public static Set<Teacher> teacherFromDto(Set<TeacherDto> dtoList) {
        return !dtoList.isEmpty()
                ? dtoList.stream().map(d -> teacherFromDto(d)).collect(Collectors.toSet())
                : new HashSet<Teacher>();
    }

//    public static List<Contact> insertTeacherInContacts(Teacher teacher, List<ContactDto> contactsDto) {
//        return contactsDto.stream()
//                .map(ContactMapper::contactFromDto)
//                .peek(c -> c.setTeacher(teacher))
//                .toList();
//    }

    //    public static List<Subject> subjectsFromDto(List<SubjectEntityDto> subjectList){
//        var result = subjectList.stream()
//                .map(SubjectMapper::toSubjectFromEntityDto)
//                .toList();
//        return result;
//    }

}
