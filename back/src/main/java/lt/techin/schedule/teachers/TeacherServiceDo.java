package lt.techin.schedule.teachers;

import lt.techin.schedule.shift.Shift;
import lt.techin.schedule.shift.ShiftRepository;
import lt.techin.schedule.subject.Subject;
import lt.techin.schedule.subject.SubjectRepository;
import lt.techin.schedule.teachers.contacts.Contact;
import lt.techin.schedule.teachers.contacts.ContactMapper;
import lt.techin.schedule.teachers.contacts.ContactService;
import lt.techin.schedule.exceptions.TeacherException;
import lt.techin.schedule.teachers.helpers.TeacherSubjectMapper;
import org.springframework.dao.EmptyResultDataAccessException;
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
        int newTeacherHash = Objects.hash(newTeacher.getfName().toLowerCase(), newTeacher.getlName().toLowerCase());
        newTeacher.setId(null);

        isNotDublicate(newTeacher);


        newTeacher.setContacts(null);
        newTeacher.setShift(null);
        newTeacher.setSubjects(null);
        newTeacher.setId(null);
        newTeacher = teacherRepository.save(newTeacher);

        if (!teacherDto.getContacts().isEmpty()) {
            List<Contact> contactsList = ContactMapper.contactFromDto(teacherDto.getContacts());
            if (!contactsList.isEmpty()) {
                var contacts = contactService.createContacts(newTeacher, contactsList);
                newTeacher.setContacts(contacts);
            }
        }
        if (!teacherDto.getSubjectsDtoList().isEmpty()) {
            Set<Subject> subjectsFromDto = TeacherSubjectMapper.subjectsFromDtos(teacherDto.getSubjectsDtoList());

            Set<Subject> foundSubjects = subjectsFromDto.stream()
                    .filter(s -> teacherRepository.existsById(s.getId()))
                    .collect(Collectors.toSet());

            if (!foundSubjects.isEmpty()) {
                newTeacher.setSubjects(foundSubjects);
            }
        }

        if (teacherDto.getTeacherShiftDto() != null) {
            Optional<Shift> shift = shiftRepository.findById(teacherDto.getTeacherShiftDto().getId());
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

        //jei tik norim isjungt, tai kitu lauku nekeiciam
        if (presentTeacher.getActive() != newTeacher.getActive()) {
            presentTeacher.setActive(!presentTeacher.getActive());
            teacherRepository.save(presentTeacher);
            return ResponseEntity.accepted().body(TeacherMapper.teacherToDto(presentTeacher));
        }

        int newTeacherHash = Objects.hash(newTeacher.getfName().toLowerCase(), newTeacher.getlName().toLowerCase());

        isNotDublicate(newTeacher);

        contactService.createContacts(newTeacher, newTeacher.getContacts());
        newTeacher = teacherRepository.save(newTeacher);

        return ResponseEntity.ok(TeacherMapper.teacherToDto(newTeacher));
    }

    public ResponseEntity<Void> deleteTeacherById(Long teacherId) {
        try {
            teacherRepository.deleteById(teacherId);
            return ResponseEntity.noContent().build();
        } catch (EmptyResultDataAccessException exception) {
            return ResponseEntity.notFound().build();
        }
    }


    public boolean isNotDublicate(Teacher newTeacher) {
        int teacherHash = Objects.hash(newTeacher.getfName().toLowerCase(), newTeacher.getlName().toLowerCase());
        Map<Long, Teacher> teachers = teacherRepository.findTeachersByHashCode(teacherHash)
                .stream()
                .collect(Collectors.toMap(t -> t.getId(), Function.identity()));
        if (teachers.isEmpty()) {
            return true;
        }

        if (newTeacher.getId() != null) {
            teachers.remove(newTeacher.getId());
        }

        if (!teachers.isEmpty() && newTeacher.getNickName().isBlank()) {
            throw new TeacherException(HttpStatus.CONFLICT, "Bandoma įvesti esamą mokytoją, jei tai bendravardis naudokite žymą !!!");
        }

        for (Teacher teacher : teachers.values()) {
            if (!teacher.getNickName().isBlank() && teacher.getNickName().equalsIgnoreCase(newTeacher.getNickName())) {
                throw new TeacherException(HttpStatus.CONFLICT, "Bandoma įvesti esamą mokytoją !!!");
            }
        }

        return true;
    }
}
