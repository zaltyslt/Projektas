package lt.techin.schedule.teachers;

import lt.techin.schedule.shift.Shift;
import lt.techin.schedule.shift.ShiftRepository;
import lt.techin.schedule.subject.Subject;
import lt.techin.schedule.subject.SubjectRepository;
import lt.techin.schedule.teachers.contacts.*;
import lt.techin.schedule.exceptions.TeacherException;
import lt.techin.schedule.teachers.helpers.TeacherSubjectMapper;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class TeacherServiceDo {
    TeacherRepository teacherRepository;
    ContactService contactService;
    private final ShiftRepository shiftRepository;
    private final SubjectRepository subjectRepository;

    public TeacherServiceDo(TeacherRepository teacherRepository, ContactService contactService,
                            ShiftRepository shiftRepository,
                            SubjectRepository subjectRepository) {
        this.teacherRepository = teacherRepository;
        this.contactService = contactService;
        this.shiftRepository = shiftRepository;
        this.subjectRepository = subjectRepository;
    }

    public ResponseEntity<TeacherDto> createTeacher(TeacherDto teacherDto) {
       Teacher newTeacher = TeacherMapper.teacherFromDto(teacherDto);

        newTeacher.setId(null);

        if (isNotDuplicate(newTeacher)) {


            newTeacher.setContacts(null);
            newTeacher.setShift(null);
            newTeacher.setSubjects(null);
            newTeacher.setId(null);
            newTeacher = teacherRepository.save(newTeacher);
        } else {
            throw new RuntimeException("Teacher creation process failed.");
        }

        ContactDto2 contactsDto = teacherDto.getContacts();
        contactsDto.setTeacherId(newTeacher.getId());
        List<Contact> contactsToSave = ContactMapper.contactFromDto2(contactsDto);
        newTeacher.setContacts(contactsToSave);

        if (teacherDto.getSubjectsList()!=null && !teacherDto.getSubjectsList().isEmpty()) {
            Set<Subject> subjectsFromDto = TeacherSubjectMapper.subjectsFromDtos(teacherDto.getSubjectsList());

            Set<Subject> foundSubjects = subjectsFromDto.stream()
                    .filter(s -> subjectRepository.existsById(s.getId()))
                    .collect(Collectors.toSet());

            if (!foundSubjects.isEmpty()) {
                newTeacher.setSubjects(foundSubjects);
            }
        } else{newTeacher.setSubjects(new HashSet<>());

        }

        if (teacherDto.getSelectedShift() != null) {
            Optional<Shift> shift = shiftRepository.findById(teacherDto.getSelectedShift().getId());
            if (shift.isPresent()) {
                newTeacher.setShift(shift.get());
            }
        }
        newTeacher = teacherRepository.save(newTeacher);
        return ResponseEntity.ok(TeacherMapper.teacherToDto(newTeacher));


    }

    public ResponseEntity<TeacherDto> updateTeacher(Long teacherId, TeacherDto teacherDto) {
        Teacher presentTeacher = teacherRepository.findById(teacherId)
                .orElseThrow(() -> new TeacherException(HttpStatus.NOT_FOUND, "Mokytojas nerastas"));

        Teacher newTeacher = TeacherMapper.teacherFromDto(teacherDto);

        int newTeacherHash = Objects.hash(newTeacher.getfName().toLowerCase(), newTeacher.getlName().toLowerCase());

        isNotDuplicate(newTeacher);

        contactService.updateContacts (newTeacher);
        newTeacher = teacherRepository.save(newTeacher);

        return ResponseEntity.ok(TeacherMapper.teacherToDto(newTeacher));
    }

    public ResponseEntity<Void> switchOff(Long id) {
        var result = teacherRepository.findById(id);
        if (result.isPresent()) {
            Teacher teacher = result.get();
            try {
                teacher.setActive(false);
                teacher = teacherRepository.save(teacher);
                return ResponseEntity.ok().header(HttpHeaders.ACCEPT, "Deleted").build();
            } catch (Exception e) {
                throw new TeacherException(HttpStatus.BAD_REQUEST, "Ištrinti nepavyko !");
            }
        } else {
            throw new TeacherException(HttpStatus.NOT_FOUND, "Mokytojas nerastas !");
        }
    }

    public ResponseEntity<Void> switchOn(Long id) {
        var result = teacherRepository.findById(id);
        if (result.isPresent()) {
            Teacher teacher = result.get();
            try {
                teacher.setActive(true);
                teacher = teacherRepository.save(teacher);
                return ResponseEntity.ok().header(HttpHeaders.ACCEPT, "Restored").build();
            } catch (Exception e) {
                throw new TeacherException(HttpStatus.BAD_REQUEST, "Atstatyti nepavyko !");
            }
        } else {
            throw new TeacherException(HttpStatus.NOT_FOUND, "Mokytojas nerastas !");
        }
    }


    public ResponseEntity<Void> deleteTeacherById(Long teacherId) {
        try {
            teacherRepository.deleteById(teacherId);
            return ResponseEntity.noContent().build();
        } catch (EmptyResultDataAccessException exception) {
            return ResponseEntity.notFound().build();
        }
    }


    public boolean isNotDuplicate(Teacher newTeacher) {
        int teacherHash = Objects.hash(newTeacher.getfName().toLowerCase(), newTeacher.getlName().toLowerCase());
        Map<Long, Teacher> teachers = teacherRepository.findTeachersByHashCode(teacherHash)
                .stream()
                .collect(Collectors.toMap(t -> t.getId(), Function.identity()));


        //If no maches
        if (teachers.isEmpty()) {
            return true;
        }
        //If teacher looked and found is the same
        if (newTeacher.getId() != null) {
            teachers.remove(newTeacher.getId());
            if (teachers.isEmpty()) {
                return true;
            }
        }
        Map<ContactType, String> newTeacherContacts = newTeacher.getContacts().stream()
                .collect(Collectors.toMap(c -> c.getContactType(), c -> c.getContactValue()));
        for (Teacher teacher : teachers.values()) {

            Map<ContactType, String> teacherContacts = teacher.getContacts().stream()
                    .collect(Collectors.toMap(c -> c.getContactType(), c -> c.getContactValue()));

            if (newTeacherContacts.get(ContactType.PHONE_NUMBER).equals(teacherContacts.get(ContactType.PHONE_NUMBER)) ||
                    newTeacherContacts.get(ContactType.DIRECT_EMAIL).equals(teacherContacts.get(ContactType.DIRECT_EMAIL))
            ) {
                throw new TeacherException(HttpStatus.CONFLICT, "Bandoma įvesti esamą mokytoją !!!");
            }
        }
        return true;
    }
}
