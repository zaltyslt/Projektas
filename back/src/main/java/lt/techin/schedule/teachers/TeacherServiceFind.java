package lt.techin.schedule.teachers;

import lt.techin.schedule.subject.*;
import lt.techin.schedule.teachers.contacts.ContactRepository;
import lt.techin.schedule.teachers.contacts.ContactService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class TeacherServiceFind {
    private final static Logger logger = LoggerFactory.getLogger(TeacherMapper.class);
    private TeacherRepository teacherRepository;
    private final ContactService contactService;
    private ContactRepository contactRepository;
    //    private SubjectService subjectService;
    private SubjectRepository subjectRepository;

    public TeacherServiceFind(TeacherRepository teacherRepository, ContactService contactService, ContactRepository contactRepository, SubjectService subjectService, SubjectRepository subjectRepository) {
        this.teacherRepository = teacherRepository;
        this.contactService = contactService;
        this.contactRepository = contactRepository;
//        this.subjectService = subjectService;
        this.subjectRepository = subjectRepository;
    }

    protected Set<TeacherDto> getAllTeachers() {
        List<Teacher> teachers = teacherRepository.findAll();
        return TeacherMapper.teacherToDto(teachers);
    }

    protected TeacherDto getTeacherById(Long id) {

        var result = teacherRepository.findById(id);
        return result.isPresent()
                ? TeacherMapper.teacherToDto(result)
                : new TeacherDto();
    }

    protected Set<TeacherDto> getTeachersByName(String name) {
        name = "%" + name.toLowerCase() + "%";
        var result = teacherRepository.getTeachersByNameFragment(name);
        return TeacherMapper.teacherToDto(result);
    }

    protected Set<TeacherDto> getTeachersBySubjects(Subject subject) {
        var result = teacherRepository.getTeacherBySubject(subject);
        //Comparator<Teacher> byLastName = Comparator.comparing(Teacher::getlName);
        return !result.isEmpty()
                ? result.stream()
                .sorted(Comparator.comparing(Teacher::getlName))
                .filter(t -> t.getSubjects().contains(subject)) //just to be sure :)
                .map(TeacherMapper::teacherToDto)
                .collect(Collectors.toSet())
                : new HashSet<TeacherDto>();
    }

    protected Set<TeacherDto> getTeachersByActiveStatus(boolean active) {
        var result = teacherRepository.findByisActive(active);
        return !result.isEmpty()
                ? result.stream()
                    .sorted(Comparator.comparing(Teacher::getlName))
                    .filter(t -> t.getActive() == active) //just to be sure :)
                    .map(TeacherMapper::teacherToDto)
                    .collect(Collectors.toSet())
                : new HashSet<TeacherDto>();
//            return new HashSet<TeacherDto>();
    }
}
