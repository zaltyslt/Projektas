package lt.techin.schedule.teachers;

import lt.techin.schedule.shift.ShiftDto;
import lt.techin.schedule.shift.ShiftMapper;
import lt.techin.schedule.teachers.contacts.Contact;
import lt.techin.schedule.teachers.contacts.ContactDto2;
import lt.techin.schedule.teachers.contacts.ContactMapper;
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
        dto.setSubjectsList(teacher.getSubjects() != null
                ? TeacherSubjectMapper.subjectsToDtos(teacher.getSubjects())
                : new HashSet<TeacherSubjectsDto>());
        dto.setWorkHoursPerWeek(teacher.getWorkHoursPerWeek() != null ? teacher.getWorkHoursPerWeek().toString() : "0");
        dto.setSelectedShift(teacher.getShift() != null ? ShiftMapper.shiftToDto(teacher.getShift()) : new ShiftDto());
        dto.setActive(teacher.getActive() != null ? teacher.getActive() : true);
        dto.setContacts(teacher.getContacts() != null
                ? ContactMapper.contactToDto2(teacher.getContacts()) : new ContactDto2());
        dto.setDateCreated(teacher.getCreatedDateTime());
        dto.setDateModified(teacher.getModifiedDateAndTime());
        return dto;
    }

    public static TeacherDto teacherToDto(Optional<Teacher> teacher) {
        return teacher.isPresent()
                ? teacherToDto(teacher.get())
                : new TeacherDto();
    }

    public static List<TeacherDto> teachersToDtos(List<Teacher> teacher) {
        return !teacher.isEmpty()
                ? teacher.stream().map(t -> teacherToDto(t)).toList()
                : new ArrayList<TeacherDto>();
    }

    public static Teacher teacherFromDto(TeacherDto dto) {
        Teacher teacher = new Teacher();
        teacher.setId(dto.getId() != null ? dto.getId() : null);
        teacher.setfName(dto.getfName() != null ? dto.getfName().trim() : "_empty");
        teacher.setlName(dto.getlName() != null ? dto.getlName().trim() : "_empty");
        teacher.setSubjects(dto.getSubjectsList() != null
                ? TeacherSubjectMapper.subjectsFromDtos(dto.getSubjectsList())
                : null);
        teacher.setWorkHoursPerWeek(dto.getWorkHoursPerWeek() != null && !dto.getWorkHoursPerWeek()
                .equals("") ? Integer.parseInt(dto.getWorkHoursPerWeek()) : 0);
        teacher.setShift(ShiftMapper.dtoToShift(dto.getSelectedShift()));
        teacher.setActive(dto.getActive());
        teacher.setContacts(dto.getContacts() != null ? ContactMapper
                .contactFromDto2(dto.getContacts()) : new ArrayList<Contact>());
        return teacher;
    }

    public static Set<Teacher> teacherFromDto(Set<TeacherDto> dtoList) {
        return !dtoList.isEmpty()
                ? dtoList.stream().map(d -> teacherFromDto(d)).collect(Collectors.toSet())
                : new HashSet<Teacher>();
    }

    public static TeacherEntityDto toTeacherEntityDto(Teacher teacher) {
        var teacherEntityDto = new TeacherEntityDto();
        if (teacher != null) {
            teacherEntityDto.setId(teacher.getId());
            teacherEntityDto.setfName(teacher.getfName());
            teacherEntityDto.setlName(teacher.getlName());
            return teacherEntityDto;
        } else {
            return null;
        }
    }

    public static Teacher toTeacherFromEntityDto(TeacherEntityDto teacherEntityDto) {
        var teacher = new Teacher();
        if (teacherEntityDto != null) {
            teacher.setId(teacherEntityDto.getId());
            teacher.setfName(teacherEntityDto.getfName());
            teacher.setlName(teacherEntityDto.getlName());
            return teacher;
        } else {
            return null;
        }
    }
}
