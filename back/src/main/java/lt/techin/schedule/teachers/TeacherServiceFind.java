package lt.techin.schedule.teachers;

import lt.techin.schedule.subject.*;
import lt.techin.schedule.teachers.contacts.ContactRepository;
import lt.techin.schedule.teachers.contacts.ContactService;
import lt.techin.schedule.teachers.helpers.TeacherSubjectMapper;
import lt.techin.schedule.teachers.helpers.TeacherSubjectsDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.*;
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

    public Set<TeacherDto> getAllTeachers() {
        List<Teacher> teachers = teacherRepository.findAll();
        return TeacherMapper.teacherToDto(teachers);
    }

    public TeacherDto getTeacherById(Long id) {
//        Optional<Teacher> result = null;

//        try{
//         result = teacherRepository.findById(id);}
//
//catch(Exception e){}
      var  result = teacherRepository.findById(id);
        return result.isPresent()
                ? TeacherMapper.teacherToDto(result)
                : new TeacherDto();
    }

    public Set<TeacherDto> getTeachersByName(String name) {
        name = "%" + name.toLowerCase() + "%";
        var result = teacherRepository.getTeachersByNameFragment(name);
        return TeacherMapper.teacherToDto(result);
    }

    public Set<TeacherDto> getTeachersBySubjects(Subject subject) {
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

    public Set<TeacherDto> getTeachersByActiveStatus(boolean active) {
        var result = teacherRepository.findByisActive(active);
        return !result.isEmpty()
                ? result.stream()
                    .sorted(Comparator.comparing(Teacher::getModifiedDateAndTime))
                    .filter(t -> t.getActive() == active) //just to be sure :)
                    .map(TeacherMapper::teacherToDto)
                    .collect(Collectors.toSet())
                : new HashSet<TeacherDto>();
//            return new HashSet<TeacherDto>();
    }

    public Set<TeacherSubjectsDto> getMiniSubjects(){
        var result = subjectRepository.findAll().stream()
                .filter(subject -> !subject.getDeleted()).collect(Collectors.toSet());

        return TeacherSubjectMapper.subjectsToDtos(result);
    }
}
